package app;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import business.manager.factory.BusinessManagerFactory;
import business.manager.impl.FirstProjectManagerFirstImpl;
import service.rest.AbstractRestResouceService;

// elle est instanciée par le serveur au démarrage
public class DependencyInjectionListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent pServletContextEvent) {
		
	}

	// elle est appelée par le serveur au démarrage
	@Override
	public void contextInitialized(ServletContextEvent pServletContextEvent) {
		
		BusinessManagerFactory businessManagerFactory = new BusinessManagerFactory();
		businessManagerFactory.setFirstBusinessManager(new FirstProjectManagerFirstImpl());
		
		AbstractRestResouceService.setBusinessManagerFactory(businessManagerFactory);
		
	}

}
