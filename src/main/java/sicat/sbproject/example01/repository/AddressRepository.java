package sicat.sbproject.example01.repository;

import sicat.sbproject.example01.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
    // You can define custom query methods if needed, e.g.
    // List<User> findByName(String name);
}
