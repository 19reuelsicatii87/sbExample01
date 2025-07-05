package sicat.sbproject.example01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sicat.sbproject.example01.model.user;
import sicat.sbproject.example01.service.userService;

@RestController
public class webApi {
	
	  @Autowired
	    private userService userServ;
	
	@GetMapping("/webApiGreeting")
	String hello() {
		return "Hello World!";
	}
	
	
	@GetMapping("/api/users")
	 public Iterable<user> getAllUsers() {
        return userServ.getAllUsers();
    }
	 
	 @PutMapping("/api/user/{id}")
	    public user updateUser(@PathVariable Integer id, @RequestBody user updatedUser) {
	        return userServ.updateUser(id, updatedUser);
	    }

}
