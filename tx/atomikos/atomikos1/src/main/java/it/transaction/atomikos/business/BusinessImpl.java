package it.transaction.atomikos.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.transaction.atomikos.dao.DaoImpl;

@Service
public class BusinessImpl {
	
	@Autowired
	DaoImpl daoImpl;
	
	
	@Transactional(transactionManager = "jtaTxManager")
	public void createInAllSchemasWithAtomikos(int id, String key, int value) {
		daoImpl.createInSchema1(id, key, value);
		/*if(true) {
			throw new RuntimeException("test");
		}*/
		daoImpl.createInSchema2(id, key, value);
	}

}
