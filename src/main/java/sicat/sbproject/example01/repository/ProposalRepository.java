package sicat.sbproject.example01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sicat.sbproject.example01.model.Proposal;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Integer> {

	// You can define custom query methods if needed, e.g.

}
