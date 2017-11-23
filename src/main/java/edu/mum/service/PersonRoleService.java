package edu.mum.service;

import edu.mum.domain.PersonRole;
import edu.mum.domain.Role;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Erdenebayar on 11/21/2017
 */
@Transactional(readOnly = true)
public interface PersonRoleService {

    PersonRole findByPersonAndRole(Integer personId, Role role);

}
