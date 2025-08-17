package sicat.sbproject.example01.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import sicat.sbproject.example01.model.Product;
import sicat.sbproject.example01.model.Proposal;
import sicat.sbproject.example01.repository.ProposalRepository;

@Service
public class ProposalService {

	@Autowired
	private ProposalRepository proposalRepository;

	public ProposalService(ProposalRepository proposalRepository) {
		this.proposalRepository = proposalRepository;
	}

	public List<Proposal> getAllProposals() {
		return proposalRepository.findAll();
	}

	public Optional<Proposal> getProposalById(Integer id) {
		return proposalRepository.findById(id);
	}

	public Proposal saveProposal(Proposal Proposal) {
		return proposalRepository.save(Proposal);
	}

	public void deleteProposal(Integer id) {
		proposalRepository.deleteById(id);
	}

	public Proposal updateProposal(Integer id, Proposal updatedProposal) {

		return proposalRepository.findById(id).map(existingProposal -> {

			// set other fields as needed
			existingProposal.setId(updatedProposal.getId());
			existingProposal.setProposalNumber(updatedProposal.getProposalNumber());
			existingProposal.setProduct(updatedProposal.getProduct());
			existingProposal.setCustomerName(updatedProposal.getCustomerName());

			// triggers update and return updated
			return proposalRepository.save(existingProposal);

		}).orElseThrow(() -> new RuntimeException("Proposal not found with id " + id));
	}

	public Proposal generateProposal(Product product, String customerName) {
		Proposal proposal = new Proposal(UUID.randomUUID().toString(), product, customerName);
		return proposalRepository.save(proposal);
	}

	public ByteArrayInputStream exportProposalAsPdf(Proposal proposal) {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			PdfWriter.getInstance(document, out);
			document.open();

			document.add(new Paragraph("Proposal Number: " + proposal.getProposalNumber()));
			document.add(new Paragraph("Customer: " + proposal.getCustomerName()));
			document.add(new Paragraph("Product: " + proposal.getProduct().getName()));
			document.add(new Paragraph("Description: " + proposal.getProduct().getDescription()));
			document.add(new Paragraph("Price: $" + proposal.getProduct().getPrice()));

			document.close();
		} catch (DocumentException e) {
			throw new RuntimeException("Error while creating PDF", e);
		}

		return new ByteArrayInputStream(out.toByteArray());
	}

}
