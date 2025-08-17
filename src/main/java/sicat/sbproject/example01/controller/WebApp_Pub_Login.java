package sicat.sbproject.example01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebApp_Pub_Login {

	@GetMapping("/login")
	public String login() {

		return "pages/login";
	}

}
