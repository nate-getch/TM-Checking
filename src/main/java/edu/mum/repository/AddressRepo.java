package edu.mum.repository;

import edu.mum.domain.Address;
import edu.mum.domain.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends CrudRepository<Address, Integer> {

}
