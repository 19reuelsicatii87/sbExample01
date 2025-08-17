package sicat.sbproject.example01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebApp_Pri_Dashboard {

	// Secured pages
	// ============================
	@GetMapping("/dashboard")
	public String dashboard() {

		return "pages/dashboard";
	}

}
