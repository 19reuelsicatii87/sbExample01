package sicat.sbproject.example01.service;

import sicat.sbproject.example01.model.Address;
import sicat.sbproject.example01.model.User;
import sicat.sbproject.example01.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(Integer id) {
		return userRepository.findById(id);
	}

	public Optional<User> getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}

	public User updateUser(Integer id, User updatedUser) {

		return userRepository.findById(id).map(existingUser -> {

			// set other fields as needed
			existingUser.setId(updatedUser.getId());
		
			existingUser.setEmail(updatedUser.getEmail());
			existingUser.setName(updatedUser.getName());			
			existingUser.setUsername(updatedUser.getUsername());			
			existingUser.setPassword( passwordEncoder.encode(updatedUser.getPassword()) );
			
			  // Clear and update the address list instead of replacing it
		    existingUser.getAddresses().clear();
		    for (Address addr : updatedUser.getAddresses()) {
		        addr.setUser(existingUser); // Make sure the back-reference is set
		        existingUser.getAddresses().add(addr);
		    }

			// triggers update
			return userRepository.save(existingUser);

		}).orElseThrow(() -> new RuntimeException("User not found with id " + id));
	}

	public String registerUser(User user) {

		if (userRepository.findByUsername(user.getUsername()).isPresent()) {

			return "Username already taken";
			// throw new RuntimeException("Username already taken");

		}

		else {

			String hashedPassword = passwordEncoder.encode(user.getPassword());
			User userInsert = new User();
			userInsert.setUsername(user.getUsername());
			userInsert.setPassword(hashedPassword);

			userRepository.save(userInsert);
			return "Registration successful!";

		}

	}

}
