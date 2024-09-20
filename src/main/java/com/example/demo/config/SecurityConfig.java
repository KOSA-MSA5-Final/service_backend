package com.example.demo.config;
//import com.example.demo.oAuth.CustomOAuth2LoginSuccessHandler;
//import com.example.demo.oAuth.CustomOAuth2UserService;
//import com.nimbusds.jose.jwk.source.ImmutableSecret;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.oauth2.jwt.JwtEncoder;
//import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@Log4j2
public class SecurityConfig  {
    private final WebConfig webConfig;
//    private final CustomOAuth2UserService customOAuth2UserService;
//    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint; // 커스텀 엔트리 포인트 주입

    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

//    @Bean
//    public JwtEncoder jwtEncoder() {
//        return new NimbusJwtEncoder(new ImmutableSecret<>(jwtSecretKey.getBytes()));
//    }
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true); // Allow encoded slashes
        firewall.setAllowUrlEncodedDoubleSlash(true); // Allow double slashes
        return firewall;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("Configuring Security Filter Chain.................................................................");

        http

                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()) // csrf 토큰 설정

                )

                .cors(cors -> cors.configurationSource(webConfig.getCorsConfiguration()))
                .addFilterAfter(new CsrfCookieGeneratorFilter(), org.springframework.security.web.csrf.CsrfFilter.class)
                .authorizeHttpRequests((request) ->
                        request.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                                .requestMatchers("/", "/api/**","/ws/**", "/auth/**", "/js/**", "/css/**", "/images/**", "http://localhost:80/**", "/**").permitAll()
                                .anyRequest().authenticated()
                );
//                .oauth2Login(oauth2->oauth2
//                        .successHandler(new CustomOAuth2LoginSuccessHandler())
//                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService)));


        return http.build();
    }
}
