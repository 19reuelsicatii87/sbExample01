package sicat.sbproject.example01.service;

import sicat.sbproject.example01.model.address;
import sicat.sbproject.example01.repository.addressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class addressService {

	@Autowired
	private addressRepository addressRepository;

	public Iterable<address> getAllAddresses() {
		return addressRepository.findAll();
	}

	public Optional<address> getAddressById(Integer id) {
		return addressRepository.findById(id);
	}

	public address saveAddress(address user) {
		return addressRepository.save(user);
	}

	public void deleteAddress(Integer id) {
		addressRepository.deleteById(id);
	}

	public address updateAddress(Integer id, address updatedAddress) {
		return addressRepository
			.findById(id)
			.map(existingAddress -> {
				
				// set other fields as needed
				existingAddress.setStreet(updatedAddress.getType());
				existingAddress.setStreet(updatedAddress.getStreet());
				existingAddress.setCity(updatedAddress.getCity());
				existingAddress.setState(updatedAddress.getState());
				
				// triggers update
				return addressRepository.save(existingAddress);
				
		}).orElseThrow(() -> new RuntimeException("Address not found with id " + id));
	}

}
