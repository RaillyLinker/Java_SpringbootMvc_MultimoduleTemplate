package com.raillylinker.module_security.util_components.impls;

import com.raillylinker.module_common.util_components.CryptoUtil;
import com.raillylinker.module_retrofit2.retrofit2_classes.RepositoryNetworkRetrofit2;
import com.raillylinker.module_retrofit2.retrofit2_classes.request_apis.AppleIdAppleComRequestApi;
import com.raillylinker.module_security.util_components.AppleOAuthHelperUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// [Apple OAuth2 검증 관련 유틸]
@Component
public class AppleOAuthHelperUtilImpl implements AppleOAuthHelperUtil {
    public AppleOAuthHelperUtilImpl(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            CryptoUtil cryptoUtil
    ) throws InterruptedException {
        this.cryptoUtil = cryptoUtil;
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final CryptoUtil cryptoUtil;
    // Retrofit2 요청 객체
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final RepositoryNetworkRetrofit2 networkRetrofit2 = RepositoryNetworkRetrofit2.getInstance();

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Logger classLogger = LoggerFactory.getLogger(this.getClass());

    // 애플 Id Token 검증 함수 - 검증이 완료되었다면 프로필 정보가 반환되고, 검증되지 않는다면 null 반환
    @Override
    @Nullable
    @org.jetbrains.annotations.Nullable
    public AppleOAuthHelperUtil.AppleProfileData getAppleMemberData(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String idToken
    ) {
        try {
            // 공개키 가져오기
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Response<AppleIdAppleComRequestApi.GetAuthKeysOutputVo> response = networkRetrofit2.appleIdAppleComRequestApi.getAuthKeys().execute();

            if (response.body() == null) {
                return null;
            }

            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<AppleIdAppleComRequestApi.GetAuthKeysOutputVo.Key> testEntityVoList = response.body().keys();

            // idToken 헤더의 암호화 알고리즘 정보 가져오기
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String header = cryptoUtil.base64Decode(idToken.split("\\.")[0]);
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Map<String, Object> headerMap = new BasicJsonParser().parseMap(header);

            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String idTokenKid = headerMap.get("kid").toString();
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String idTokenAlg = headerMap.get("alg").toString();

            // 공개키 리스트를 순회하며 암호화 알고리즘이 동일한 키 객체 가져오기
            @Nullable @org.jetbrains.annotations.Nullable
            AppleIdAppleComRequestApi.GetAuthKeysOutputVo.Key appleKeyObject = null;
            for (AppleIdAppleComRequestApi.GetAuthKeysOutputVo.Key jsonObject : testEntityVoList) {
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String kid = jsonObject.kid();
                @Valid @NotNull @org.jetbrains.annotations.NotNull
                String alg = jsonObject.alg();

                if (Objects.equals(idTokenKid, kid) && Objects.equals(idTokenAlg, alg)) {
                    appleKeyObject = jsonObject;
                    break;
                }
            }

            // 일치하는 공개키가 없음
            if (appleKeyObject == null) {
                return null;
            }

            byte[] nBytes = Base64.getUrlDecoder().decode(appleKeyObject.n());
            byte[] eBytes = Base64.getUrlDecoder().decode(appleKeyObject.e());

            @Valid @NotNull @org.jetbrains.annotations.NotNull
            BigInteger n = new BigInteger(1, nBytes);
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            BigInteger e = new BigInteger(1, eBytes);

            @Valid @NotNull @org.jetbrains.annotations.NotNull
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            KeyFactory keyFactory = KeyFactory.getInstance(appleKeyObject.kty());
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            @Valid @NotNull @org.jetbrains.annotations.NotNull
            JwtParserBuilder jwtParser = Jwts.parser();
            jwtParser.verifyWith(publicKey);

            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Claims memberInfo = jwtParser.build().parseSignedClaims(idToken).getPayload();

            return new AppleOAuthHelperUtil.AppleProfileData(
                    memberInfo.get("sub", String.class),
                    memberInfo.containsKey("is_private_email") && "true".equals(memberInfo.get("is_private_email"))
                            ? null : memberInfo.get("email", String.class)
            );
        } catch (Exception e) {
            classLogger.error("An error occurred: ", e);  // 예외와 함께 로그를 기록
            return null;
        }
    }
}
