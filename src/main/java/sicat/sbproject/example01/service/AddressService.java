package sicat.sbproject.example01.service;

import sicat.sbproject.example01.model.Address;
import sicat.sbproject.example01.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	public Iterable<Address> getAllAddresses() {
		return addressRepository.findAll();
	}

	public Optional<Address> getAddressById(Integer id) {
		return addressRepository.findById(id);
	}

	public Address saveAddress(Address user) {
		return addressRepository.save(user);
	}

	public void deleteAddress(Integer id) {
		addressRepository.deleteById(id);
	}

	public Address updateAddress(Integer id, Address updatedAddress) {
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
