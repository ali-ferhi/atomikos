package metier;

import dao.Dao;

public class Metier {
	
	private Dao dao;
	
	public Metier() {
		dao = new Dao();
	}
	
	public String getSalam() {
		return "Salam " + dao.getNom() + "!";
	}

}
