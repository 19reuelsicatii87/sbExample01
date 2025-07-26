package sicat.sbproject.example01;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class dummy {

	public static void main(String[] args) {
		System.out.println("password:" + new BCryptPasswordEncoder().encode("user"));

	}

}
