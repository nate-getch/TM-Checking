package edu.mum.service;

import java.util.List;

import edu.mum.domain.Session;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

public interface SessionService {

	@Secured("hasAuthority('ROLE_COUNCELOR, ROLE_ADMIN')")
	Integer save(Session session);

	@Secured("hasAuthority('ROLE_COUNCELOR, ROLE_ADMIN')")
	void removeById(int id);
	
	@Secured("hasAuthority('ROLE_COUNCELOR, ROLE_ADMIN')")
	Session get(Integer id);

	@PreAuthorize("isAuthenticated()")
	public List<Session> findAll();

}
