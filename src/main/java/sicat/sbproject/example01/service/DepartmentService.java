package sicat.sbproject.example01.service;

import sicat.sbproject.example01.model.Department;
import sicat.sbproject.example01.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepo;

	public Iterable<Department> getAllDepartment() {
		return departmentRepo.findAll();
	}

	public Optional<Department> getDepartmentById(Integer id) {
		return departmentRepo.findById(id);
	}

	public Department saveDepartment(Department user) {
		return departmentRepo.save(user);
	}

	public void deleteDepartment(Integer id) {
		departmentRepo.deleteById(id);
	}

	public Department updateDepartment(Integer id, Department updatedDepartment) {
		return departmentRepo
			.findById(id)
			.map(existingDepartment -> {
				
				// set other fields as needed
				existingDepartment.setId(updatedDepartment.getId());
				existingDepartment.setName(updatedDepartment.getName());
				
				// triggers update
				return departmentRepo.save(existingDepartment);
				
		}).orElseThrow(() -> new RuntimeException("Department not found with id " + id));
	}

}
