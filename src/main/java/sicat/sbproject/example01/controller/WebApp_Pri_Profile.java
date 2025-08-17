package sicat.sbproject.example01.controller;

import java.security.Principal;

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
public class WebApp_Pri_Profile {

	@Autowired
	private UserService userService;

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

}
