package sicat.sbproject.example01.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String email;

	 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	 @JsonManagedReference
	 private List<Address> addresses;	

	// Constructors
	public User() {
	}

	public User(String name, String email) {
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

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		// Set the reverse relationship
		for (Address addr : addresses) {
			addr.setUser(this);
		}
		this.addresses = addresses;
	}
}
