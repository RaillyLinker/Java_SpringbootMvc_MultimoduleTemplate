package com.raillylinker.module_security.util_components.impls;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raillylinker.module_common.util_components.CryptoUtil;
import com.raillylinker.module_security.util_components.JwtTokenUtil;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// [JWT 토큰 유틸]
@Component
public class JwtTokenUtilImpl implements JwtTokenUtil {
    public JwtTokenUtilImpl(CryptoUtil cryptoUtil) {
        this.cryptoUtil = cryptoUtil;
    }

    private final CryptoUtil cryptoUtil;


    // <공개 메소드 공간>
    // (액세스 토큰 발행)
    // memberRoleList : 멤버 권한 리스트 (ex : ["ROLE_ADMIN", "ROLE_DEVELOPER"])
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public String generateAccessToken(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long memberUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long accessTokenExpirationTimeSec,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256InitializationVector,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256EncryptionKey,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String issuer,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtSecretKeyString,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            List<@Valid @NotNull String> roleList
    ) {
        return doGenerateToken(
                memberUid,
                "access",
                accessTokenExpirationTimeSec,
                jwtClaimsAes256InitializationVector,
                jwtClaimsAes256EncryptionKey,
                issuer,
                jwtSecretKeyString,
                roleList
        );
    }

    // (리프레시 토큰 발행)
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public String generateRefreshToken(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long memberUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long refreshTokenExpirationTimeSec,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256InitializationVector,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256EncryptionKey,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String issuer,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtSecretKeyString
    ) {
        return doGenerateToken(
                memberUid,
                "refresh",
                refreshTokenExpirationTimeSec,
                jwtClaimsAes256InitializationVector,
                jwtClaimsAes256EncryptionKey,
                issuer,
                jwtSecretKeyString,
                null
        );
    }

    // (JWT Secret 확인)
    // : 토큰 유효성 검증. 유효시 true, 위변조시 false
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public Boolean validateSignature(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String token,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtSecretKeyString
    ) {
        String[] tokenSplit = token.split("\\.");
        String header = tokenSplit[0];
        String payload = tokenSplit[1];
        String signature = tokenSplit[2];

        String newSig = cryptoUtil.hmacSha256(header + "." + payload, jwtSecretKeyString);

        return signature.equals(newSig);
    }

    // (JWT 정보 반환)
    // Member Uid
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public Long getMemberUid(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String token,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256InitializationVector,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256EncryptionKey
    ) {
        return Long.parseLong(cryptoUtil.decryptAES256(
                parseJwtForPayload(token).get("mu").toString(),
                "AES/CBC/PKCS5Padding",
                jwtClaimsAes256InitializationVector,
                jwtClaimsAes256EncryptionKey));
    }

    // (Token 용도 (access or refresh) 반환)
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public String getTokenUsage(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String token,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256InitializationVector,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256EncryptionKey
    ) {
        return cryptoUtil.decryptAES256(
                parseJwtForPayload(token).get("tu").toString(),
                "AES/CBC/PKCS5Padding",
                jwtClaimsAes256InitializationVector,
                jwtClaimsAes256EncryptionKey);
    }

    // (멤버 권한 리스트 반환)
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public List<String> getRoleList(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String token,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256InitializationVector,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256EncryptionKey
    ) {
        String rl = cryptoUtil.decryptAES256(
                parseJwtForPayload(token).get("rl").toString(),
                "AES/CBC/PKCS5Padding",
                jwtClaimsAes256InitializationVector,
                jwtClaimsAes256EncryptionKey);
        return new Gson().fromJson(rl, new TypeToken<List<String>>() {
        }.getType());
    }

    // (발행자 반환)
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public String getIssuer(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String token
    ) {
        return parseJwtForPayload(token).get("iss").toString();
    }

    // (토큰 남은 유효 시간(초) 반환 (만료된 토큰이라면 0))
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public Long getRemainSeconds(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String token
    ) {
        long exp = (long) parseJwtForPayload(token).get("exp");
        long currentEpochSeconds = Instant.now().getEpochSecond();

        return currentEpochSeconds < exp ? exp - currentEpochSeconds : 0;
    }

    // (토큰 만료 일시 반환)
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public LocalDateTime getExpirationDateTime(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String token
    ) {
        long exp = (long) parseJwtForPayload(token).get("exp");
        Instant expirationInstant = Instant.ofEpochSecond(exp);

        return LocalDateTime.ofInstant(expirationInstant, ZoneId.systemDefault());
    }

    // (토큰 타입)
    @Override
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    public String getTokenType(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String token
    ) {
        return parseJwtForHeader(token).get("typ").toString();
    }


    // ---------------------------------------------------------------------------------------------
    // <비공개 메소드 공간>
    // (JWT 토큰 생성)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private String doGenerateToken(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long memberUid,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String tokenUsage,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            Long expireTimeSec,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256InitializationVector,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtClaimsAes256EncryptionKey,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String issuer,
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwtSecretKeyString,
            @Nullable @org.jetbrains.annotations.Nullable
            List<@Valid @NotNull String> roleList
    ) {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        JwtBuilder jwtBuilder = Jwts.builder();

        @Valid @NotNull @org.jetbrains.annotations.NotNull
        Map<String, Object> headersMap = new HashMap<>();
        headersMap.put("typ", "JWT");

        jwtBuilder.header().empty().add(headersMap);

        @Valid @NotNull @org.jetbrains.annotations.NotNull
        Map<String, Object> claimsMap = new HashMap<>();

        // member uid
        claimsMap.put("mu", cryptoUtil.encryptAES256(
                String.valueOf(memberUid),
                "AES/CBC/PKCS5Padding",
                jwtClaimsAes256InitializationVector,
                jwtClaimsAes256EncryptionKey
        ));

        // 멤버 권한 리스트
        if (roleList != null) {
            claimsMap.put("rl", cryptoUtil.encryptAES256(
                    new Gson().toJson(roleList),
                    "AES/CBC/PKCS5Padding",
                    jwtClaimsAes256InitializationVector,
                    jwtClaimsAes256EncryptionKey
            ));
        }

        // token usage
        claimsMap.put("tu", cryptoUtil.encryptAES256(
                tokenUsage,
                "AES/CBC/PKCS5Padding",
                jwtClaimsAes256InitializationVector,
                jwtClaimsAes256EncryptionKey
        ));

        // 발행자
        claimsMap.put("iss", issuer);

        claimsMap.put("iat", Instant.now().getEpochSecond());
        claimsMap.put("exp", Instant.now().getEpochSecond() + expireTimeSec);
        claimsMap.put("cd", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSSSSS")));

        jwtBuilder.claims().empty().add(claimsMap);

        jwtBuilder
                .signWith(
                        Keys.hmacShaKeyFor(jwtSecretKeyString.getBytes(StandardCharsets.UTF_8)),
                        Jwts.SIG.HS256
                );

        return jwtBuilder.compact();
    }

    // (base64 로 인코딩된 Header, Payload 를 base64 로 디코딩)
    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private Map<@Valid @NotNull String, @Valid @NotNull Object> parseJwtForHeader(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwt
    ) {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        String header = cryptoUtil.base64Decode(jwt.split("\\.")[0]);
        return new BasicJsonParser().parseMap(header);
    }

    @Valid
    @NotNull
    @org.jetbrains.annotations.NotNull
    private Map<@Valid @NotNull String, @Valid @NotNull Object> parseJwtForPayload(
            @Valid @NotNull @org.jetbrains.annotations.NotNull
            String jwt
    ) {
        @Valid @NotNull @org.jetbrains.annotations.NotNull
        String payload = cryptoUtil.base64Decode(jwt.split("\\.")[1]);
        return new BasicJsonParser().parseMap(payload);
    }

}
