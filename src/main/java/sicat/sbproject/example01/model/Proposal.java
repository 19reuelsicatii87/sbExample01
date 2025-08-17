package sicat.sbproject.example01.model;

import jakarta.persistence.*;

@Entity
@Table(name = "proposals")
public class Proposal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String proposalNumber;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	private String customerName;

	public Proposal() {
	}

	public Proposal(String proposalNumber, Product product, String customerName) {
		this.proposalNumber = proposalNumber;
		this.product = product;
		this.customerName = customerName;
	}

	// Getters and setters...
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProposalNumber() {
		return proposalNumber;
	}

	public void setProposalNumber(String proposalNumber) {
		this.proposalNumber = proposalNumber;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}
