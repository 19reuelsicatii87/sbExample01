package sicat.sbproject.example01.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

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

		// ✅ Use explicit, Ant-style patterns and include trailing /** where needed
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(auth -> auth
				// public
				.requestMatchers("/", "/home", "/login", "/register", "/about-us").permitAll()
				.requestMatchers("/product/select/**").permitAll() // ✅ public page
				.requestMatchers("/proposal/generate/**").permitAll() // ✅ public action
				.requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
				// everything else requires auth
				.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").successHandler(loginSuccessHandler()) // sets cookie +
																									// redirect
						.permitAll())
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler()))
				// Optional: return 401 for APIs instead of redirecting
				.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

		// ✅ Make sure our JWT filter runs early (before username/password filter)
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// ✅ Generate JWT and store in httpOnly cookie, then redirect to /dashboard
	@Bean
	AuthenticationSuccessHandler loginSuccessHandler() {
		return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
			String username = authentication.getName();
			String token = jwtUtil.generateToken(username);

			System.out.println("✅ Login success for user: " + username);
			System.out.println("✅ JWT: " + token);

			ResponseCookie jwtCookie = ResponseCookie.from("jwt", token).httpOnly(true).path("/").maxAge(86400).build();

			response.setHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
			response.sendRedirect("/dashboard");
		};
	}

	@Bean
	LogoutSuccessHandler logoutSuccessHandler() {
		return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
			ResponseCookie cookie = ResponseCookie.from("jwt", "").httpOnly(true).path("/").maxAge(0).build();

			response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
			response.sendRedirect("/login");
		};
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
