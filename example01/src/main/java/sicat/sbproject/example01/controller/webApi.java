package sicat.sbproject.example01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class webApi {
	
	@GetMapping("/webApiGreeting")
	String hello() {
		return "Hello World!";
	}

}
