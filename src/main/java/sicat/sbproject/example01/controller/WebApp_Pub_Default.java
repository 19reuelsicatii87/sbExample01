package sicat.sbproject.example01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebApp_Pub_Default {

	
	// Unsecured pages
	// ============================

	@GetMapping("/")
	public String home() {

		return "pages/home";
	}

	@GetMapping("/about-us")
	public String aboutus() {

		return "pages/aboutus";
	}

}
