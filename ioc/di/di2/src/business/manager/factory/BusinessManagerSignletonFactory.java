package business.manager.factory;

import business.manager.contract.FirstBusinessManager;
import business.manager.impl.FirstProjectManagerFirstImpl;

public class BusinessManagerSignletonFactory {
	
	private static final BusinessManagerSignletonFactory INSTANCE = new BusinessManagerSignletonFactory();
	
	private BusinessManagerSignletonFactory() {
	    super();
	}
	
	public static BusinessManagerSignletonFactory getInstance() {
	    return BusinessManagerSignletonFactory.INSTANCE;
	}
	
	public FirstBusinessManager getFirstBusinessManager() {
		return new FirstProjectManagerFirstImpl();
	}

}
