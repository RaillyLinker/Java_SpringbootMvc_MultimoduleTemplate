package com.raillylinker.module_security.util_components.impls;

import com.raillylinker.module_common.util_components.CryptoUtil;
import com.raillylinker.module_retrofit2.retrofit2_classes.RepositoryNetworkRetrofit2;
import com.raillylinker.module_retrofit2.retrofit2_classes.request_apis.AppleIdAppleComRequestApi;
import com.raillylinker.module_security.util_components.AppleOAuthHelperUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

// [Apple OAuth2 검증 관련 유틸]
@Component
public class AppleOAuthHelperUtilImpl implements AppleOAuthHelperUtil {
    public AppleOAuthHelperUtilImpl(CryptoUtil cryptoUtil) {
        this.cryptoUtil = cryptoUtil;
    }

    private final CryptoUtil cryptoUtil;
    private final RepositoryNetworkRetrofit2 networkRetrofit2 = RepositoryNetworkRetrofit2.getInstance();

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private final Logger classLogger = LoggerFactory.getLogger(this.getClass());

    // 애플 Id Token 검증 함수 - 검증이 완료되었다면 프로필 정보가 반환되고, 검증되지 않는다면 null 반환
    @Override
    public AppleOAuthHelperUtil.AppleProfileData getAppleMemberData(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String idToken
    ) {
        try {
            // 공개키 가져오기
            var response = networkRetrofit2.appleIdAppleComRequestApi.getAuthKeys().execute();

            if (response.body() == null) {
                return null;
            }

            List<AppleIdAppleComRequestApi.GetAuthKeysOutputVo.Key> testEntityVoList = response.body().keys();

            // idToken 헤더의 암호화 알고리즘 정보 가져오기
            String header = cryptoUtil.base64Decode(idToken.split("\\.")[0]);
            var headerMap = new BasicJsonParser().parseMap(header);

            String idTokenKid = headerMap.get("kid").toString();
            String idTokenAlg = headerMap.get("alg").toString();

            // 공개키 리스트를 순회하며 암호화 알고리즘이 동일한 키 객체 가져오기
            AppleIdAppleComRequestApi.GetAuthKeysOutputVo.Key appleKeyObject = null;
            for (AppleIdAppleComRequestApi.GetAuthKeysOutputVo.Key jsonObject : testEntityVoList) {
                String kid = jsonObject.kid();
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

            BigInteger n = new BigInteger(1, nBytes);
            BigInteger e = new BigInteger(1, eBytes);

            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance(appleKeyObject.kty());
            var publicKey = keyFactory.generatePublic(publicKeySpec);

            var jwtParser = Jwts.parser();
            jwtParser.verifyWith(publicKey);

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
