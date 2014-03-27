package sk.fiit.tomas.chovanak.dbs.DAO_OBJECTS;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProduktDAO extends DAOobject {

	private static Map<String, String> productMap = null;
	
	public ProduktDAO(Connection connection) {
		super(connection);
	}
	
	public ObservableList<String> getAllProductsNames() {
		
		if(productMap == null){
			productMap = new HashMap<String,String>();
			String query = "SELECT p.nazov, p.id FROM produkt p";
			List<ResultRow> rrlst = executeStatement(query);
			for (ResultRow rr : rrlst){
				productMap.put(rr.getRowDataList().get(0), rr.getRowDataList().get(1));
			}
		}
		
		ObservableList<String> ret = FXCollections.observableArrayList();
		for(Entry<String,String> e : productMap.entrySet()){
			ret.add(e.getKey());
		}
		
		return ret;
	}
	
	public static String convertNameToId(String name){
		return productMap.get(name);
	}

}
