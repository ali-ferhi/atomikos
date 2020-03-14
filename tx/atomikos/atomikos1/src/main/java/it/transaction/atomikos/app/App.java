package it.transaction.atomikos.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.transaction.atomikos.business.BusinessImpl;
import it.transaction.atomikos.conf.SpringContext;

public class App {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringContext.class);
		BusinessImpl businessImpl = context.getBean(BusinessImpl.class);
		businessImpl.createInAllSchemasWithAtomikos(37, "1037", 137);
		context.close();
	}

}
