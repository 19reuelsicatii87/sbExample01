package sicat.sbproject.example01.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sicat.sbproject.example01.model.User;
import sicat.sbproject.example01.service.UserService;

import sicat.sbproject.example01.model.Address;
import sicat.sbproject.example01.service.AddressService;

import sicat.sbproject.example01.model.Department;
import sicat.sbproject.example01.service.DepartmentService;

import sicat.sbproject.example01.model.Employee;
import sicat.sbproject.example01.service.EmployeeService;

@RestController
public class WebApi {

	@Autowired
	private UserService userService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private DepartmentService departmentService;	
	
	@Autowired
	private EmployeeService employeeService;

	@GetMapping("/webApiGreeting")
	String hello() {
		return "Hello World!!!";
	}

	// USER ENDPOINTS
	// ===========================================
	@GetMapping("/api/user")
	public Iterable<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/api/user/{id}")
	public Optional<User> getUserById(@PathVariable Integer id) {
		return userService.getUserById(id);
	}

	@PostMapping("/api/user")
	public User createUser(@RequestBody User createUser) {
		return userService.saveUser(createUser);
	}

	@PutMapping("/api/user/{id}")
	public User updateUser(@PathVariable Integer id, @RequestBody User updatedUser) {
		return userService.updateUser(id, updatedUser);
	}

	// ADDRESS ENDPOINTS
	// ===========================================
	@GetMapping("/api/address")
	public Iterable<Address> getAllAddress() {
		return addressService.getAllAddresses();
	}

	@PostMapping("/api/address")
	public User createAddress(@RequestBody User createAddress) {
		return userService.saveUser(createAddress);
	}

	// DEPARTMENT ENDPOINTS
	// ===========================================
	@GetMapping("/api/department")
	public Iterable<Department> getAllDepartment() {
		return departmentService.getAllDepartment();
	}

	@PostMapping("/api/department")
	public Department createDepartment(@RequestBody Department createDepartment) {
		return departmentService.saveDepartment(createDepartment);
	}
	
	
	// EMPLOYEE ENDPOINTS
	// ===========================================
	@GetMapping("/api/employee")
	public Iterable<Employee> getAllEmployee() {
		return employeeService.getAllEmployee();
	}
	
	

}
