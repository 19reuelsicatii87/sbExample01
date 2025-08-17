package sicat.sbproject.example01.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;
import sicat.sbproject.example01.model.Product;
import sicat.sbproject.example01.model.Proposal;
import sicat.sbproject.example01.service.ProductService;
import sicat.sbproject.example01.service.ProposalService;

@Controller
public class WebApp_Pub_Proposal {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProposalService proposalService;

	// Step 1: Show product selection page
	@GetMapping("/product/select")
	public String showProductSelection(Model model) {
		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);
		return "pages/generateProposal";
	}

	// Step 2: Handle selection form and create proposal
	@PostMapping("/proposal/generate")
	public String handleProductSelection(@RequestParam Integer productId, @RequestParam String customerName,
			Model model) {

		Optional<Product> productOpt = productService.getProductById(productId);
		if (productOpt.isEmpty()) {
			model.addAttribute("error", "Product not found");
			return "/pages/generateProposal";
		}

		Proposal proposal = proposalService.generateProposal(productOpt.get(), customerName);

		// Redirect after creation
		return "redirect:/proposal/" + proposal.getId();
	}

	// Step 3: New mapping to display proposal details
	@GetMapping("/proposal/{id}")
	public String viewProposal(@PathVariable Integer id, Model model) {
		Proposal proposal = proposalService.getProposalById(id)
				.orElseThrow(() -> new RuntimeException("Proposal not found"));

		model.addAttribute("proposal", proposal);
		return "pages/proposal"; // renders pages/proposal.html
	}

	@GetMapping("/proposal/download/{id}")
	public void downloadProposal(@PathVariable Integer id, HttpServletResponse response) throws IOException {
		Proposal proposal = proposalService.getProposalById(id)
				.orElseThrow(() -> new RuntimeException("Proposal not found"));

		// ✅ Use your service method to get PDF as InputStream
		try (ByteArrayInputStream pdfStream = proposalService.exportProposalAsPdf(proposal)) {
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=proposal_" + id + ".pdf");

			// ✅ Copy bytes from InputStream to response output
			pdfStream.transferTo(response.getOutputStream());
		}
	}

}
