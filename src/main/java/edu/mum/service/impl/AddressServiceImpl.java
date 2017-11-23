package edu.mum.service.impl;

import edu.mum.domain.Address;
import edu.mum.repository.AddressRepo;
import edu.mum.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Erdenebayar on 11/21/2017
 */
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;

    @Autowired
    public AddressServiceImpl(AddressRepo addressRepo) {
        this.addressRepo = addressRepo;
    }

    @Override
    public void save(Address address) {
        addressRepo.save(address);
    }

    @Override
    public List<Address> find() {
        return (List<Address>) addressRepo.findAll();
    }

    @Override
    public Address get(Integer id) {
        return addressRepo.findOne(id);
    }
}
