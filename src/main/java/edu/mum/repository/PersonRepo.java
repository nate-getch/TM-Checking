package edu.mum.repository;

import edu.mum.domain.Person;
import edu.mum.domain.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 985927 on 11/20/2017
 */
@Repository
public interface PersonRepo extends CrudRepository<Person, Integer> {

    @Query("FROM Person p WHERE p.username =:username")
    public Person findByUsername(@Param("username") String username);

    @Query("FROM Person p WHERE p.username =:username AND p.id <>:id")
    public Person findByUsernameAndIdIsNot(@Param("username") String username, @Param("id") Integer id);

    @Query("SELECT pr.person FROM PersonRole pr WHERE pr.role =:role")
    List<Person> findByPersonRoles(@Param("role") Role role);

}
