package edu.mum.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mum.domain.Session;
import edu.mum.repository.AppointmentRepo;
import edu.mum.repository.LocationRepo;
import edu.mum.repository.SessionRepo;
import edu.mum.service.SessionService;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

	@Autowired
	private SessionRepo sessionRepo;
	
	@Autowired
	private AppointmentRepo appRepo;
	
	@Autowired
	private LocationRepo locationRepo;
	
	public Integer save(Session session) {
		session = sessionRepo.save(session);
		return session.getId();
	}

	public void removeById(int id) {
		Session removing = sessionRepo.findOne(id);
		locationRepo.delete(removing.getLocation());
		appRepo.delete(removing.getAppointments());
		sessionRepo.delete(removing);
	}

	public Session get(Integer id) {
		return sessionRepo.findOne(id);
	}

	public List<Session> findAll() {
		return (List<Session>) sessionRepo.findAll();
	}
	
}
