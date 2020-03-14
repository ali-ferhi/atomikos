package web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import metier.IMetier;

public class Web {
	
	public static void main(String[] args) {
		
		ApplicationContext springContext = new ClassPathXmlApplicationContext("Config.xml");
		IMetier metierImpl = (IMetier) springContext.getBean("metierImpl");
		System.out.println(metierImpl.getSalam());
	}

}