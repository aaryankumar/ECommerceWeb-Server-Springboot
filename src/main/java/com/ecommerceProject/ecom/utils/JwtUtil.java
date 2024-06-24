package com.ecommerceProject.ecom.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    public static final String SECRET="0659B33D512E834386FA2FBAB446E58BD1687DF334F1DD9AD90B6DE70DEFB495";

    public String generateToken(String userName){

        Map<String, Object> claims=new HashMap<>();
        return createToken(claims, userName);
    }

    private String createToken(Map<String, Object> claims, String userName) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))//date and time at which the token issued
                .setExpiration(new Date(System.currentTimeMillis()+10000*60*30))
                .signWith( SignatureAlgorithm.HS256,SECRET).compact();
    }
//SignatureAlgorithm
//    private Key getSignKey() {
//        byte[] keybytes= Decoder.BASE64.decode(SECRET);
//        return keys.hmacShaKeyFor(keybytes);
//
//    }
        public String extractUsername(String token){

        return extractClaim(token, Claims::getSubject);
        }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims= extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        return  Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token){

        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {

        return extractClaim(token,Claims::getExpiration);
    }
    public boolean validateToken(String token, UserDetails userDetails){

        final String username= extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
