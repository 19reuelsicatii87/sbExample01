package sicat.sbproject.example01.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import sicat.sbproject.example01.model.User;
import sicat.sbproject.example01.model.Address;
import sicat.sbproject.example01.repository.UserRepository;

@Component
public class UserSeeder implements ApplicationRunner {

//	private final ProductRepository productRepository;
//
//	// Constructor injection
//	public ProductSeeder(ProductRepository productRepository) {
//		this.productRepository = productRepository;
//	}

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub

		seedUsers();

	}

	// Data Seeder
	private void seedUsers() {

		// Create users first
		User alice = new User("Alice Smith", "alice@example.com");
		alice.setUsername("alice");
		alice.setPassword("$2a$10$UM0SscOIpwSgEfWNVB3ia.FkgFE4xu2pm6IeFDgjN9Ud4ObZpAMcG");
		alice.setRole("USER");

		User bob = new User("Bob Johnson", "bob@example.com");
		bob.setUsername("bob");
		bob.setPassword("$2a$10$UM0SscOIpwSgEfWNVB3ia.FkgFE4xu2pm6IeFDgjN9Ud4ObZpAMcG");
		bob.setRole("USER");

		User charlie = new User("Charlie Brown", "charlie@example.com");
		charlie.setUsername("charlie");
		charlie.setPassword("$2a$10$UM0SscOIpwSgEfWNVB3ia.FkgFE4xu2pm6IeFDgjN9Ud4ObZpAMcG");
		charlie.setRole("USER");

		User reuelsicatii = new User("Reuel Sicat II", "reuelsicatii@example.com");
		charlie.setUsername("reuelsicatii");
		charlie.setPassword("$2a$10$iWzqT8hb3GHao/4/tVcweO194vwMfdye1rb6O3pW8Ih7/n54/.qkO");
		charlie.setRole("ADMIN");

		// Create addresses referencing users
		alice.setAddresses(List.of(new Address("Home", "123 Main St", "New York", "NY", alice),
				new Address("Office", "456 Park Ave", "New York", "NY", alice)));

		bob.setAddresses(List.of(new Address("Home", "789 Market St", "San Francisco", "CA", bob)));

		charlie.setAddresses(List.of(new Address("Home", "321 Broadway", "Los Angeles", "CA", charlie)));

		reuelsicatii.setAddresses(List.of(new Address("Home", "1234 Prime St", "New York", "NY", reuelsicatii),
				new Address("Office", "4567 Second Ave", "New York", "NY", reuelsicatii)));

		// Final seeds list
		List<User> seeds = List.of(alice, bob, charlie);

		for (User u : seeds) {
			// check existence by unique field (name) before inserting
			if (userRepository.existsByName(u.getName())) {
				System.out.println("⏭️ Product exists, skipping: " + u.getName());
			} else {
				userRepository.save(u);
				System.out.println("✅ Seeded product: " + u.getName());
			}
		}
	}

}