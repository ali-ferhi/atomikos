package it.transaction.jbossts.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.transaction.jbossts.business.BusinessImpl;
import it.transaction.jbossts.conf.SpringContext;

public class App {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringContext.class);
		BusinessImpl businessImpl = context.getBean(BusinessImpl.class);
		businessImpl.createInSchema1(30, "1030", 130);
		/*if(true) {
			throw new RuntimeException("test");
		}*/
		businessImpl.createInSchema2(30, "1030", 130);
		context.close();
	}

}
