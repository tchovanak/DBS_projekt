package sk.fiit.tomas.chovanak.dbs.DAO_MANAGERS;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import sk.fiit.tomas.chovanak.dbs.DAO_OBJECTS.PredajProduktuDAO;
import sk.fiit.tomas.chovanak.dbs.DAO_OBJECTS.ZamestnanecZmluvaDAO;


public class DAOmanager {
		  
		  protected Connection connection = null;
		  
		  protected PredajProduktuDAO  predajProduktuDAO  = null;
		  
		  protected ZamestnanecZmluvaDAO zamestnanecZmluvaDAO = null;
		  
		  private void openConnection(){	
			  	Properties props = new Properties();
				try {
					props.load(new FileInputStream("etc/db.properties"));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					this.connection = DriverManager.getConnection(props.getProperty("url"),props.getProperty("username"),props.getProperty("password"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
		  }
		  
		  private void closeConnection(){
				try {
					if(connection != null){
						connection.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
		  }
		  
		  /**
		   * Vytvori transakciu v ktorej vykona DaoCommand a následne transakciu commitne a uzatvori transakciu
		   * @param command
		   * @return
		   */
		  private Object transaction(DaoCommand command){
			    
			    try{ 
			    	this.connection.setAutoCommit(false);
			        
			    	Object returnValue = command.execute(this);
			        
			    	this.connection.commit();
			        
			    	return returnValue;
			    	
			    } catch(Exception e){
			    	try {
						this.connection.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
			    } finally {
			    	try {
			    		this.connection.setAutoCommit(true);
			    	} catch (SQLException e) {
			    		e.printStackTrace();
			    	}
			    }
		    
			    return null;
		  }
		  
		  /**
		   * Otvori spojenie, A v zabali command do transakcie, na konci uzatvori spojenie.
		   * @param command
		   * @return
		   */
		  public Object OpenTransactionAndClose(final DaoCommand command){
			  try{
				  openConnection();
				  return executeAndClose(new DaoCommand(){
	
					  @Override
					  public Object execute(DAOmanager manager) {
						  return manager.transaction(command);
					  }
				  });
			  }finally{
				  closeConnection();
			  }
		  }
		  
		  /**
		   * Vykona command bez transakcie a na konci uzatvori spojenie
		   * @param command
		   * @return
		   */
		  public Object executeAndClose(DaoCommand command){
			  try{
			      return command.execute(this);
			   } finally {
			      try {
					this.connection.close();
			      } catch (SQLException e) {
					e.printStackTrace();
			      }
			   }
			 
		  }
		  
		  
		  /************************ FACTORY METODY na DAO objekty ******************************************/
		  
		  public PredajProduktuDAO getPredajProduktuDAO(){
		    
			  if(this.predajProduktuDAO == null){
				  this.predajProduktuDAO = new PredajProduktuDAO(this.connection);
			  }
			  
			  return this.predajProduktuDAO;
		  }

		  public ZamestnanecZmluvaDAO getZamestnanecZmluvaDAO() {
			  
			  if(this.zamestnanecZmluvaDAO == null){
				  this.zamestnanecZmluvaDAO = new ZamestnanecZmluvaDAO(this.connection);
			  }
			  
			  return this.zamestnanecZmluvaDAO;
		}
		
}
