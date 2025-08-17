package sicat.sbproject.example01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sicat.sbproject.example01.model.User;
import sicat.sbproject.example01.service.UserService;

@Controller
public class WebApp_Pub_Registration {

	@Autowired
	private UserService userService;

	// Unsecured pages
	// ============================

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {

		model.addAttribute("user", new User());

		return "pages/register";
	}

	@PostMapping("/register")
	public String registerUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {

		String result = userService.registerUser(user);

		if ("Registration successful!".equals(result)) {

			redirectAttributes.addFlashAttribute("message", "Registration successful!");
			return "redirect:/login";

		} else {

			redirectAttributes.addFlashAttribute("error", result);
			return "redirect:/register";

		}

	}

}
