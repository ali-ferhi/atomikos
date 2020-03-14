package service.rest;

public class FirstRestResourceService extends AbstractRestResouceService {
	
	public void get() {
		getBusinessManagerFactory().getFirstBusinessManager();
	}

}
