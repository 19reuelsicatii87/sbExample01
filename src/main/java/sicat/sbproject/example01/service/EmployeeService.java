package sicat.sbproject.example01.service;

import sicat.sbproject.example01.model.Employee;
import sicat.sbproject.example01.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepo;

	public Iterable<Employee> getAllEmployee() {
		return employeeRepo.findAll();
	}

	public Optional<Employee> getEmployeeById(Integer id) {
		return employeeRepo.findById(id);
	}

	public Employee saveEmployee(Employee user) {
		return employeeRepo.save(user);
	}

	public void deleteEmployee(Integer id) {
		employeeRepo.deleteById(id);
	}

	public Employee updateEmployee(Integer id, Employee updatedEmployee) {
		return employeeRepo
			.findById(id)
			.map(existingEmployee -> {

			// set other fields as needed
			existingEmployee.setId(updatedEmployee.getId());
			existingEmployee.setName(updatedEmployee.getName());

			// triggers update
			return employeeRepo.save(existingEmployee);

		}).orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
	}

}
