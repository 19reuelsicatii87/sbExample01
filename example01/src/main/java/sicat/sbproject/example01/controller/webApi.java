package sicat.sbproject.example01.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sicat.sbproject.example01.model.user;
import sicat.sbproject.example01.service.userService;

import sicat.sbproject.example01.model.address;
import sicat.sbproject.example01.service.addressService;

@RestController
public class webApi {

	@Autowired
	private userService userServ;
	
	@Autowired
	private addressService addressServ;

	@GetMapping("/webApiGreeting")
	String hello() {
		return "Hello World!!!";
	}
	
	
	// USER ENDPOINTS
	// ===========================================
	@GetMapping("/api/user")
	public Iterable<user> getAllUsers() {
		return userServ.getAllUsers();
	}
	
	@GetMapping("/api/user/{id}")
	public Optional<user> getUserById(@PathVariable Integer id) {
		return userServ.getUserById(id);
	}
	
	@PostMapping("/api/user")
	public user updateUser(@RequestBody user createUser) {
		return userServ.saveUser(createUser);
	}


	@PutMapping("/api/user/{id}")
	public user updateUser(@PathVariable Integer id, @RequestBody user updatedUser) {
		return userServ.updateUser(id, updatedUser);
	}
	
	
	// ADDRESS ENDPOINTS
	// ===========================================
	@GetMapping("/api/address")
	public Iterable<address> getAllAddress() {
		return addressServ.getAllAddresses();
	}

}
