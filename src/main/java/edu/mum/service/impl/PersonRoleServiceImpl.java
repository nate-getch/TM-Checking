package edu.mum.service.impl;

import edu.mum.domain.PersonRole;
import edu.mum.domain.Role;
import edu.mum.repository.PersonRoleRepo;
import edu.mum.service.PersonRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Erdenebayar on 11/21/2017
 */
@Service
public class PersonRoleServiceImpl implements PersonRoleService {

    private final PersonRoleRepo personRoleRepo;

    @Autowired
    public PersonRoleServiceImpl(PersonRoleRepo personRoleRepo) {
        this.personRoleRepo = personRoleRepo;
    }

    @Override
    public PersonRole findByPersonAndRole(Integer personId, Role role) {
        return personRoleRepo.findByPersonAndRole(personId, role);
    }
}
