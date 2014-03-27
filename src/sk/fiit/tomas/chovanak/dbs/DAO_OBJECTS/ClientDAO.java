package sk.fiit.tomas.chovanak.dbs.DAO_OBJECTS;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClientDAO extends DAOobject{

	private static Map<String, String> clientMap = null;
	
	public ClientDAO(Connection connection) {
		super(connection);
	}
	
	public ObservableList<String> getAllClientNames() {
		
		if(clientMap == null){
			clientMap = new HashMap<String,String>();
			String query = "SELECT k.priezvisko,k.prve_meno,k.id FROM klient k";
			List<ResultRow> rrlst = executeStatement(query);
			for (ResultRow rr : rrlst){
				clientMap.put(rr.getRowDataList().get(0) + " " + rr.getRowDataList().get(1), rr.getRowDataList().get(2));
			}
		}
		
		ObservableList<String> ret = FXCollections.observableArrayList();
		for(Entry<String,String> e : clientMap.entrySet()){
			ret.add(e.getKey());
		}
		
		return ret;
	}
	
	public static String convertNameToId(String name){
		return clientMap.get(name);
	}

}
