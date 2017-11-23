package edu.mum.repository;

import edu.mum.domain.Session;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepo extends CrudRepository<Session, Integer> {
	
	@Modifying
	@Query("UPDATE Session s SET s.availablesits = (s.availablesits + 1) WHERE s.id = :id")
	public void increaseAvailableSits(@Param("id") Integer id);
	
	@Modifying
	@Query("UPDATE Session s SET s.availablesits = (s.availablesits - 1) WHERE s.id = :id")
	public void decreaseAvailableSits(@Param("id") Integer id);

}
