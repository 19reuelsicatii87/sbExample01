package sicat.sbproject.example01.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher; // ✅
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sicat.sbproject.example01.service.CustomUserDetailsService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	// ✅ Use Ant-style patterns so /product/select, /product/select/ and params all
	// match
	private static final List<String> PUBLIC_PATTERNS = List.of("/", "/home", "/login", "/register", "/about-us",
			"/product/select/**", // ✅ public
			"/proposal/generate/**", // ✅ public
			"/css/**", "/js/**", "/img/**");

	private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher(); // ✅

	public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}

	// ✅ Let Spring decide to skip this filter entirely for public routes
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getRequestURI();
		boolean skip = PUBLIC_PATTERNS.stream().anyMatch(p -> PATH_MATCHER.match(p, path));
		if (skip) {
			System.out.println("👉 [JWT] Skipping filter for public path: " + path);
		}
		return skip; // true = do NOT run doFilterInternal
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String path = request.getRequestURI();
		System.out.println("👉 [JWT] Filtering secured path: " + path);

		// Read JWT from cookie
		String token = extractJwtFromCookie(request);
		System.out.println("👉 [JWT] Extracted token: " + (token != null ? token : "null"));

		if (token != null && jwtUtil.validateToken(token)) { // ✅ same secret for generate/validate inside JwtUtil
			String username = jwtUtil.extractUsername(token);
			System.out.println("👉 [JWT] Token valid for user: " + username);

			if (SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());

				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);

				System.out.println("👉 [JWT] Authentication set for: " + username);
			}
		} else {
			System.out.println("❌ [JWT] Missing or invalid token; proceeding unauthenticated.");
		}

		filterChain.doFilter(request, response);
	}

	private String extractJwtFromCookie(HttpServletRequest request) {
		if (request.getCookies() != null) {
			return Arrays.stream(request.getCookies()).filter(c -> "jwt".equals(c.getName())).map(Cookie::getValue)
					.findFirst().orElse(null);
		}
		return null;
	}
}
