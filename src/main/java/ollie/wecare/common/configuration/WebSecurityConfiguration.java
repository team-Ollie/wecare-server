package ollie.wecare.common.configuration;

import lombok.RequiredArgsConstructor;
import ollie.wecare.common.jwt.JwtAuthenticationFilter;
import ollie.wecare.common.jwt.JwtExceptionFilter;
import ollie.wecare.user.service.AuthService;
import ollie.wecare.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {
    private final UserService userService;
    private final AuthService authService;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final RedisTemplate<String, String> redisTemplate;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedOrigins(List.of("http://localhost:8080", "http://localhost:3000", "https://team-ollie.github.io/WeCare-FE", "https://team-ollie.github.io"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers(
                                new AntPathRequestMatcher("/users/login"),
                                new AntPathRequestMatcher("/users/signup"),
                                new AntPathRequestMatcher("/users/nickname"),
                                new AntPathRequestMatcher("/users/loginId"),
                                new AntPathRequestMatcher("/users/signupView"),
                                new AntPathRequestMatcher("/users/reissue-token"),
                                new AntPathRequestMatcher("/**", "GET"),
                                new AntPathRequestMatcher("/home", "GET")).permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(authService, userService, redisTemplate), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
                .build();
    }
}
