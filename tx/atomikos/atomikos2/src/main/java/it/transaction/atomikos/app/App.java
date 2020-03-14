package it.transaction.atomikos.app;

import javax.jms.JMSException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import it.transaction.atomikos.business.BusinessImpl;
import it.transaction.atomikos.conf.SpringContext;

public class App {

	public static void main(String[] args) throws Exception {
		
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringContext.class);
		BusinessImpl businessImpl = context.getBean(BusinessImpl.class);		
		businessImpl.setSpringContext(context);
		
		//businessImpl.createInDbAndSendToJms(39, "1039", 139);
		businessImpl.sendToJmsAndCreateInDb(39, "1039", 139);
		
		context.close();
	}

}
