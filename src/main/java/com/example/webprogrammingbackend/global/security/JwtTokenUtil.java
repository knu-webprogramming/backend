package com.example.webprogrammingbackend.global.security;

import com.example.webprogrammingbackend.domain.members.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtTokenUtil {

    // JWT 비밀키 (임의로 설정)
    // JWT 키는 되도록이면 길고 쉽게 풀지 못하는 것으로!
    private String REQ_SECRET = "aschnhkghgAWFLIHslihwfaWFHawrrHRoiwoASqfo123kl1l23jlwfmnan19047ahnfgklalkwwikdrkACACjsjUIUKJBlhWAFWASFascWAfaollas";

    private long EXPIRE_TIME = 1000 * 60 * 60 * 24 * 365;

    /**
     * 회원 가입, 간편 인증 후 추가 정보 입력 란 진행 시에 발급 되는 토큰
     * @param authentication
     * @return
     */
    public String generateRegisterToken(Authentication authentication) {

        // google, kakao ...
        String clientRegistrationId = getRegistrationIdFromAuthentic(authentication);

        Map<String, String> claims = new HashMap<>();
        log.info("Token name : {}",authentication.getName());
        log.info("Token regi id : {}",clientRegistrationId);
        claims.put("sub",authentication.getName());
        claims.put("registration_id",clientRegistrationId);


        return generateJWT(claims, REQ_SECRET, EXPIRE_TIME);

    }

    public String generateRegisterToken(Member member) {

        Map<String, String> claims = new HashMap<>();
        log.info("Token name : {}",member.getId());
        log.info("Token regi id : {}",member.getAuth().getRegistrationId());
        claims.put("sub",member.getId().toString());
        claims.put("registration_id",member.getAuth().getRegistrationId());
        claims.put("role",member.getRole().toString());


        return generateJWT(claims, REQ_SECRET, EXPIRE_TIME);

    }

    /**
     * JWT 생성 Base Method
     * @param claims
     * @param secret
     * @param expireTime
     * @return
     */

    public String generateJWT(Map<String,?> claims, String secret, long expireTime) {
        // Key 세팅
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        // 현재시간, 만료시간 세팅
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expireTime);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256) //or signWith(Key, SignatureAlgorithm)
                .compact();
    }


    // JWT 토큰으로부터 사용자 ID 추출
    public String getUserIdFromToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(REQ_SECRET).parseClaimsJws(token);
        log.info("claims : {}",claims);
        return claims.getBody().getSubject();
    }

    // JWT 토큰으로부터 registrationId 추출 (google, kakao)
    public String getRegistrationIdFromToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(REQ_SECRET).parseClaimsJws(token);
        return (String) claims.getBody().get("registration_id");
    }

    // JWT 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(REQ_SECRET).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // RegistrationId 를 얻기 위한 메서드 (From. Authentic)
    private String getRegistrationIdFromAuthentic(Authentication authentication) {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        return oauthToken.getAuthorizedClientRegistrationId();
    }

}
