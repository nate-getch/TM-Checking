package edu.mum.repository;

import edu.mum.domain.Location;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepo extends CrudRepository<Location,Integer> {
}