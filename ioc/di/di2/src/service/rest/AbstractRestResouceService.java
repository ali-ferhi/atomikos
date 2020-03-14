package service.rest;

import business.manager.factory.BusinessManagerFactory;

public abstract class AbstractRestResouceService {
	
	private static BusinessManagerFactory BUSINESS_MANAGER_FACTORY;
	
	protected static BusinessManagerFactory getBusinessManagerFactory() {
        return BUSINESS_MANAGER_FACTORY;
    }
	
    public static void setBusinessManagerFactory(BusinessManagerFactory pBusinessManagerFactory) {
    	BUSINESS_MANAGER_FACTORY = pBusinessManagerFactory;
    }

}
