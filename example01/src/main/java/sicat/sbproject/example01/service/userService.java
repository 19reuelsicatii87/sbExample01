package sicat.sbproject.example01.service;

import sicat.sbproject.example01.model.User;
import sicat.sbproject.example01.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(Integer id) {
		return userRepository.findById(id);
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public void deleteUser(Integer id) {
		userRepository.deleteById(id);
	}

	public User updateUser(Integer id, User updatedUser) {
		
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
