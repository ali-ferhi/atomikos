package it.transaction.jbossts.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import it.transaction.jbossts.entities.schema1.Entity1;

@Repository
public class DaoImpl {
	
	@PersistenceContext(unitName="unitName1")	
	private EntityManager entityManager1;
	
	@PersistenceContext(unitName="unitName2")	
	private EntityManager entityManager2;
	
	public void createInSchema1(int id, String key, int value) {
		Entity1 entity1 = new Entity1(id, key, value);
		entityManager1.persist(entity1);
	}
	
	public void createInSchema2(int id, String key, int value) {
		it.transaction.jbossts.entities.schema2.Entity1 entity1 = new it.transaction.jbossts.entities.schema2.Entity1(id, key, value);
		entityManager2.persist(entity1);
	}

}
