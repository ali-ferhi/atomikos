package web;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.Scanner;

import dao.DaoImpl;
import dao.DaoImpl2;
import dao.IDao;
import metier.IMetier;
import metier.Metier;
import metier.MetierImpl;

public class Web {
	
	public static void main(String args[]) {
		
		//Metier metier = new Metier();
		//System.out.println(metier.getSalam());
		
		
		// Déclaration par interface
		//IDao daoImpl;
		
		// Instantiation pas implémentation
		//daoImpl = new DaoImpl();
		
		
		//IMetier metierImpl = new MetierImpl(daoImpl);
		//System.out.println(metierImpl.getSalam());
		
		
		try {
			Scanner scanner = new Scanner(new File("config.txt"));
			
			String nomClasseDaoImpl = scanner.next();
			String nomClasseMetierImpl = scanner.next();
						
			Class classeDaoImpl = Class.forName(nomClasseDaoImpl);
			IDao daoImpl = (IDao) classeDaoImpl.newInstance();
			
			Class classeMetierImpl = Class.forName(nomClasseMetierImpl);
			IMetier metierImpl = (IMetier) classeMetierImpl.newInstance();
			
			Method meth = classeMetierImpl.getMethod("setDao",new Class[]{IDao.class});
			meth.invoke(metierImpl, new Object[]{"dao"});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
