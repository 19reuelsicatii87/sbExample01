package sicat.sbproject.example01.service;

import sicat.sbproject.example01.model.user;
import sicat.sbproject.example01.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class userService {

	@Autowired
	private userRepository userRepository;

	public Iterable<user> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<user> getUserById(Integer id) {
		return userRepository.findById(id);
	}

	public user saveUser(user user) {
		return userRepository.save(user);
	}

	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}

	public user updateUser(Integer id, user updatedUser) {
		
		return userRepository
				.findById(id)
				.map(existingUser -> {
			
				// set other fields as needed
				existingUser.setName(updatedUser.getName());
				existingUser.setEmail(updatedUser.getEmail());
				
				// triggers update
				return userRepository.save(existingUser); 
				
				})
				.orElseThrow(() -> new RuntimeException("User not found with id " + id));
	}

}
