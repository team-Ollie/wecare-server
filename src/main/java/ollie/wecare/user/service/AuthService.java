package ollie.wecare.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import ollie.wecare.user.dto.JwtDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${jwt.access-token-validity-in-millis}")
    private int accessTokenExpirationTime;

    @Value("${jwt.refresh-token-validity-in-millis}")
    private int refreshTokenExpirationTime;

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final RedisService redisService;


    // 토큰 발급
    public JwtDto generateToken(Long userIdx) {
        String accessToken = generateAccessToken(userIdx);
        String refreshToken = generateRefreshToken(userIdx);
        return new JwtDto(accessToken, refreshToken);
    }

    // accessToken 발급
    public String generateAccessToken(Long userIdx) {
        Claims claims = Jwts.claims();
        claims.put("userIdx", userIdx);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // refreshToken 발급
    public String generateRefreshToken(Long userIdx) {
        String refreshToken = Jwts.builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        redisService.signup(userIdx, refreshToken);
        return refreshToken;
    }
}
