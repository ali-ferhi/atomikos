package business.manager.factory;

import business.manager.contract.FirstBusinessManager;

public class BusinessManagerFactory {
	
	private FirstBusinessManager firstBusinessManager;
	
	public void setFirstBusinessManager(FirstBusinessManager pFirstBusinessManager) {
		this.firstBusinessManager = pFirstBusinessManager;
	}
	
	public FirstBusinessManager getFirstBusinessManager() {
		return this.firstBusinessManager;
	}

}
