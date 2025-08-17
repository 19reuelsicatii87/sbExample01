package sicat.sbproject.example01.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sicat.sbproject.example01.model.User;
import sicat.sbproject.example01.service.UserService;

@Controller
public class WebApp_Pub_LoginRegistration {

	@Autowired
	private UserService userService;

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

	@GetMapping("/login")
	public String login() {

		return "pages/login";
	}

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

	@GetMapping("/profile")
	public String showProfileForm(Principal principal, Model model) {

		String username = principal.getName(); // JWT subject
		User user = userService.getUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

		model.addAttribute("user", user);
		return "pages/profile";
	}

	@PostMapping("/profile")
	public String updateUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {

		User updatedUser = userService.updateUser(user.getId(), user);

		if (updatedUser != null) {
			redirectAttributes.addFlashAttribute("message", "Update successful!");
			return "redirect:/profile";
		} else {
			redirectAttributes.addFlashAttribute("error", "Failed to update user.");
			return "redirect:/profile";
		}

	}

	// Secured pages
	// ============================
	@GetMapping("/dashboard")
	public String dashboard() {

		return "pages/dashboard";
	}

}
