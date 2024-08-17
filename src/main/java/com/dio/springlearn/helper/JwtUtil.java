package com.dio.springlearn.helper;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

public class JwtUtil {

    private static final String SECRET_STRING = "secret_key";
    private static final long EXPIRATION_TIME = 864_000_000;
    // private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1 * 24 * 60 * 60 * 1000L; // 1 Day
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1 * 60 * 1000L; // 1 minute in milliseconds

    private static String generateDeviceFingerprint(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String ip = request.getRemoteAddr();
        String data = userAgent + ip;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 algorithm not available", e);
        }
    }

    private static Key getSigningKey() {
        try {
            MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
            byte[] keyBytes = sha512.digest(SECRET_STRING.getBytes(StandardCharsets.UTF_8));
            return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-512 algorithm not available", e);
        }
    }

    public static String generateAccessToken(String username, String name, int userId,HttpServletRequest request) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", name);
        claims.put("user_id", userId);
        claims.put("email", username);
        claims.put("device_fingerprint", generateDeviceFingerprint(request));

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public static String generateRefreshToken(String username, String name, int userId,HttpServletRequest request) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", name);
        claims.put("user_id", userId);
        claims.put("email", username);
        claims.put("device_fingerprint", generateDeviceFingerprint(request));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean isTokenExpired(String token) {
        return parseToken(token).getExpiration().before(new Date());
    }

    public static boolean validateDeviceFingerprint(String token, HttpServletRequest request) {
        String tokenFingerprint = parseToken(token).get("device_fingerprint", String.class);
        String currentFingerprint = generateDeviceFingerprint(request);
        return tokenFingerprint != null && tokenFingerprint.equals(currentFingerprint);
    }
}
