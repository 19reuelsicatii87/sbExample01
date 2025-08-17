package sicat.sbproject.example01.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String type;
	@Column(columnDefinition = "TEXT")
	private String description;
	private double length;
	private double width;
	private double height;
	private double price;

	// ✅ JPA requires this
	protected Product() {
	}

	// Constructors
	public Product(String name, String type, String description, double length, double width, double height,
			double price) {
		super();
		this.name = name;
		this.type = type;
		this.description = description;
		this.length = length;
		this.width = width;
		this.height = height;
		this.price = price;
	}

	// Getters and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
