package edu.mum.service;

import edu.mum.domain.Address;
import edu.mum.domain.Person;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 985927 on 11/20/2017
 */
@Transactional(readOnly = true)
public interface AddressService {

    @Transactional
    void save(Address address);

    List<Address> find();

    Address get(Integer id);
}
