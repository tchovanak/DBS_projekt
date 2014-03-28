package sk.fiit.tomas.chovanak.dbs.DAO_MANAGERS;


public class DAOfactory {	
	
	private static DAOmanager manager = new DAOmanager();
	
	public DAOmanager createDAOmanager(){
		return manager;
	}
}
