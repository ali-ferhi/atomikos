package it.transaction.jbossts.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.transaction.jbossts.dao.DaoImpl;

@Service
public class BusinessImpl {
	
	@Autowired
	DaoImpl daoImpl;
	
	
	@Transactional(transactionManager = "transactionManager1")
	public void createInSchema1(int id, String key, int value) {
		daoImpl.createInSchema1(id, key, value);
	}
	
	@Transactional(transactionManager = "transactionManager2")
	public void createInSchema2(int id, String key, int value) {
		daoImpl.createInSchema2(id, key, value);
	}

}
