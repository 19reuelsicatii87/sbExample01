package sicat.sbproject.example01;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class dummy {

	public static void main(String[] args) {
		System.out.println("password-admin:" + new BCryptPasswordEncoder().encode("admin"));
		System.out.println("password-user:" + new BCryptPasswordEncoder().encode("user"));

	}

}
