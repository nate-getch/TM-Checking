package edu.mum.repository;

import edu.mum.domain.PersonRole;
import edu.mum.domain.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Erdenebayar on 11/21/2017
 */
public interface PersonRoleRepo extends CrudRepository<PersonRole, Integer> {

    @Query("FROM PersonRole p WHERE p.person.id =:personId AND role =:role")
    PersonRole findByPersonAndRole(@Param("personId") Integer personId, @Param("role") Role role);

}
