package sicat.sbproject.example01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class webApp {
	
	
	@GetMapping("/")
	public String home() {		
		
		return "pages/home";
	}
	
	
	@GetMapping("/login")
	public String login() {		
		
		return "pages/login";
	}
	
	@GetMapping("/webAppGreeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		
		model.addAttribute("name", name);
		
		return "greeting";
	}

}
