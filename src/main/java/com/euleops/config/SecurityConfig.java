package com.euleops.config;


import com.euleops.user.repository.UserRepository;
import com.euleops.user.service.UserDetailsServiceImpl;
import com.euleops.utilities.sendGrid.JwtAuthenticationFilter;
import com.euleops.utilities.sendGrid.JwtTokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${DOMAIN_ORIGIN_URL}")
    private String frontendUrl;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    public SecurityConfig(JwtTokenUtil jwtTokenUtil, UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Rutas públicas (permitAll)
                        .requestMatchers(
                                "/api/ws/**",
                                "/api/auth/**",
                                "/api/transactions/{transactionId}",
                                "/api/transactions/cancel/**",
                                "/api/auth/registerUser", "/api/auth/check-auth",
                                "/api/auth/registerUserByAdmin", "/api/auth/activate",
                                "/api/auth/validate-password", "/api/auth/login",
                                "/api/auth/google-login", "/api/auth/logout",
                                "/api/auth/reset-password", "/api/accounts/**",
                                "/api/accounts/apply-balance/", "/api/home",
                                "/api/gift-cards/**", "/api/kinguin-gift-cards/**",
                                "/api/users/**", "/api/public/**", "/api/cart",
                                "/api/cart/**", "/api/telegram/**", "/api/kinguin/**",
                                "/api/users/user-details", "/api/users/${userId}",
                                "/api/orders", "/api/orders/**", "/api/highlights/**",
                                "/api/highlights", "/api/users/update-user-details",
                                "/api/users/**", "/api/transactions", "/api/transactions/**",
                                "/api/transactions/cancel/**", "/api/wish-list",
                                "/api/wish-list/**", "/api/wish-list/${wishedItemId}",
                                "/api/feedbacks/**", "/api/recommendations/**",
                                "/api/recommendations/user/${userId}", "/api/sync/**",
                                "/api/freefire/**", "/api/freefire/products",
                                "/api/currency", "/api/recommendations/content-based/**",
                                "/api/manual-orders/**", "/api/main-screen-gift-cards/**",
                                "api/transactions/verifyPayment",
                                "/api/activation-details/**",
                                "api/transactions/verify-unmatched-payment",
                                "/api/paypal/**", "/api/auth/check-auth", "/api/visits/**", "/api/metrics/**",
                                "/api/admin/payments/**", "/api/promotion/**", "/api/payments/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // .oauth2Login(oauth2Login -> oauth2Login
                //     .defaultSuccessUrl("/api/home", true)
                //     .userInfoEndpoint(userInfoEndpoint ->
                //         userInfoEndpoint.oidcUserService(oidcUserService()))
                // )
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    authException.getMessage()
                            );
                        })
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenUtil, userDetailsService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("https://astralisbank.com", "http://localhost:4200"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.addAllowedOrigin(frontendUrl);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public OidcUserService oidcUserService() {
        return new OidcUserService();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }




}
