package sicat.sbproject.example01.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class user {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String email;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<address> addresses;

	// Constructors
	public user() {
	}

	public user(String name, String email) {
		this.name = name;
		this.email = email;
	}

	// Getters and setters
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<address> addresses) {
		// Set the reverse relationship
		for (address addr : addresses) {
			addr.setUser(this);
		}
		this.addresses = addresses;
	}
}
