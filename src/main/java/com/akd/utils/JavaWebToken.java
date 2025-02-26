package com.akd.utils;

import com.akd.common.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JavaWebToken {


    private static Key getKeyInstance() {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("LOGIN_APP");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }

    public static String createJavaWebToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance()).compact();
    }

    public static String createJavaWebTokenByMd5(Map<String, Object> claims){
        String javaWebToken = createJavaWebToken(claims);
        return MD5Utils.encoderByMd5(javaWebToken);
    }

    public static Map<String, Object> verifyJavaWebToken(String jwt) {
        try {
            Map<String, Object> jwtClaims =
                    Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
            return jwtClaims;
        } catch (Exception e) {
            log.error("json web token verify failed");
            return null;
        }
    }

    public static String createTokenByCinemaCode(String cinemaCode){
        Map<String, Object> tokenMap = new HashMap<String, Object>();
        tokenMap.put("cinemaCode", cinemaCode);
        tokenMap.put("date", System.currentTimeMillis());
        tokenMap.put("userRedisSalt", Constants.SALT);
        String token = cinemaCode+"_"+JavaWebToken.createJavaWebTokenByMd5(tokenMap);
        return token;
    }

}
