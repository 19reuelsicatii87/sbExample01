package sicat.sbproject.example01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Define public routes
        String[] publicPaths = { "/", "/home", "/login", "/register", "/css/**" };
        
        

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(publicPaths).permitAll()
                .anyRequest().authenticated()
            )
            
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(loginSuccessHandler()) // 👈 JWT after login
                .permitAll()
            )
            
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler()) // 👈 Clear JWT on logout
            )
            
            // Reads JWT from the request (e.g., cookie or header), 
            // validates it, and sets the Spring Security context.
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    // ✅ Custom success handler to generate JWT and store in cookie
    @Bean
    AuthenticationSuccessHandler loginSuccessHandler() {
    	
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
        	
            String username = authentication.getName();
            String token = jwtUtil.generateToken(username);
            
            System.out.println("✅ Login success for user: " + username);
            System.out.println("✅ Login success for user: " + token);

            ResponseCookie jwtCookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .path("/")
                .maxAge(86400)
                .build();

            response.setHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
            response.sendRedirect("/dashboard");
            
        };
    }

    // ✅ Optional: remove JWT on logout
    @Bean
    LogoutSuccessHandler logoutSuccessHandler() {
    	
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
        	
            ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();

            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            response.sendRedirect("/login");
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
