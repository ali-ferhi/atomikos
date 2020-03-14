package metier;

import dao.IDao;

public class MetierImpl implements IMetier {
	
	private IDao iDao;
	
	public MetierImpl(IDao injectionDAO) {
		iDao = injectionDAO;
	}	

	@Override
	public String getSalam() {
		return "Salam " + iDao.getNom() + "!";
	}
	
	public void setIDao(IDao injectionDAO) {
		iDao = injectionDAO;
	}

}
