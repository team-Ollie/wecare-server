package ollie.wecare.user.service;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class RedisService {

    @Value("${jwt.refresh-token-validity-in-millis}")
    private int refreshTokenExpirationTime;

    @Value("${jwt.secret-key}")
    private String secretKey;
    private final RedisTemplate<String, String> redisTemplate;

    public void signup(Long userIdx, String refreshToken) {
        redisTemplate.opsForValue().set(String.valueOf(userIdx), refreshToken, Duration.ofMillis(refreshTokenExpirationTime));
    }

    public void deleteFromRedis(Long userIdx) {
        if(redisTemplate.opsForValue().get(String.valueOf(userIdx)) != null) redisTemplate.delete(String.valueOf(userIdx));
    }

    public void registerBlackList(String accessToken, String status) {
        // accessToken expirationTime 동안 전달받은 staus 상태로 redis에 저장
        Long expirationTime = getExpirationTime(accessToken);
        redisTemplate.opsForValue().set(accessToken, status, Duration.ofMillis(expirationTime));
    }

    private Long getExpirationTime(String token) {
        Date accessTokenExpirationTime = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
        return accessTokenExpirationTime.getTime() - (new Date()).getTime();
    }

    public String getToken(Long userIdx){
        return redisTemplate.opsForValue().get(String.valueOf(userIdx));
    }

}
