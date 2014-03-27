package sk.fiit.tomas.chovanak.dbs.DAO_OBJECTS;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import sk.fiit.tomas.chovanak.dbs.gui.MainViewController;


/**
 * Zakladna trieda pre DAO objekty poskytuje spolocne metody.
 * @author Tomas
 *
 */
public class DAOobject {
	
	protected static Properties props = new Properties();
	
	// INICILIZACIA konfiguracie databazoveho spojenia
	static{
		try {
			props.load(new FileInputStream("etc/db.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected Connection connection;
	
	public DAOobject(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * vykona query a vrati list riadkov vyslednej tabulky
	 * @param query
	 * @return
	 */
	protected List<ResultRow> executeStatement(String query) {
		List<ResultRow> results = new ArrayList<ResultRow>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
	        stmt = connection.createStatement();
	        rs = stmt.executeQuery(query);
	        while(rs.next()){
	        	ResultRow row = new ResultRow(rs);
	        	results.add(row);
	        }
	    } catch (SQLException e ) {
	       e.printStackTrace();
	    } finally {
	        if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} }
	    }
		
		return results;
	}
	
	/**
	 * vykona query ktora updatuje v databaze
	 * @param query
	 * @return -1 neuspesne vykonanie
	 * 			1 uspesne vykonanie
	 */
	protected int executeStatementUpdate(String query) {
		Statement stmt = null;
		int result = -1;
		try {
	        stmt = connection.createStatement();
	        result = stmt.executeUpdate(query);
	        
	    } catch (SQLException e ) {
	    	MainViewController.outputState(e.getMessage());
	    } finally {
	        if (stmt != null) { try {
	        	stmt.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} }
	        MainViewController.outputState("Transakcia úspešne dokončená!\n");
	    }
		if(result == -1){
			MainViewController.outputState("Transakcia neúspešná");
		}
		return result;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	
}
