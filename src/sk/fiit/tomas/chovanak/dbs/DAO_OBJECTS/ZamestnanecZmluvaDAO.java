package sk.fiit.tomas.chovanak.dbs.DAO_OBJECTS;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sk.fiit.tomas.chovanak.dbs.gui.MainViewController;

public class ZamestnanecZmluvaDAO extends DAOobject{

	public ZamestnanecZmluvaDAO(Connection connection) {
		super(connection);
	}

	private static Map<String, String> predajcaMap = null;
	
	/**
	 * Vrati vypis vsetkych zaznamov na zaklade vstupnych parametrov
	 * @param id_zamestnanec
	 * @param prve_meno
	 * @param priezvisko
	 * @param datum_nastupu
	 * @param datum_ukoncenia
	 * @return
	 */
	public String getZamestnanec(String id_zamestnanec, String prve_meno, String priezvisko, String datum_nastupu, String datum_ukoncenia) {
		
		String query = "";
		
		// TENTO ROZHODOVACI BLOK RIESI SITUACIE KEDY SU VYPLNENE ALEBO NEVYPLNENE ROZNE COMBO BOX
		if(!id_zamestnanec.equals("") && !prve_meno.equals("") && !priezvisko.equals("")){
			query = "SELECT * FROM zamestnanec_zmluva WHERE id = " + id_zamestnanec + " AND prve_meno = '" + prve_meno + "' AND priezvisko = '" + priezvisko + "';";
		}else if (!prve_meno.equals("") && !priezvisko.equals("")){
			query = "SELECT * FROM zamestnanec_zmluva WHERE prve_meno = '" + prve_meno + "' AND priezvisko = '" + priezvisko + "';";
		}else if (!prve_meno.equals("") && !id_zamestnanec.equals("")){
			query = "SELECT * FROM zamestnanec_zmluva WHERE prve_meno = '" + prve_meno + "' AND id = " + id_zamestnanec + ";";
		}else if (!priezvisko.equals("") && !id_zamestnanec.equals("")){
			query = "SELECT * FROM zamestnanec_zmluva WHERE priezvisko = '" + priezvisko + "' AND id = " + id_zamestnanec + ";";
		}else if (!priezvisko.equals("")){
			query = "SELECT * FROM zamestnanec_zmluva WHERE priezvisko = '" + priezvisko + "';";
		}else if (!prve_meno.equals("")){
			query = "SELECT * FROM zamestnanec_zmluva WHERE prve_meno = '" + prve_meno + "';";
		}else if (!id_zamestnanec.equals("")){
			query = "SELECT * FROM zamestnanec_zmluva WHERE id = " + id_zamestnanec + ";";
		}
		
		List<ResultRow> rs = executeStatement(query);
		String ret = "";
		
		// TU SA URCUJE FORMAT VYPISU 
		for(ResultRow rr : rs){
			List<String> data = rr.getRowDataList();
			List<String> column = rr.getRowColumnList();
			for(int i = 0; i < data.size(); i++){
				ret += "column: " + column.get(i) + " , data : " + data.get(i) + "\n";
			}
			ret += "\n\n";
		}
		
		
		return ret;
	}
	
	/**
	 * preťažená metóda  
	 * @param id_zamestnanec
	 * @return
	 */
	public String getZamestnanec(String id_zamestnanec) {
		return getZamestnanec(id_zamestnanec,"","","","");
	}
	
	
	/**
	 * 
	 * @param id_zamestnanec == id zaznamu, ktorý chcem zmeniť
	 * @param id_zamestnanecNovy
	 * @param prve_menoNovy
	 * @param priezviskoNovy
	 * @param datum_nastupuNovy
	 * @param datum_ukonceniaNovy
	 */
	public void update(String id_povodny, String id_novy,String prve_meno, String priezvisko,
			String datum_nastupu, String datum_ukoncenia) {
			
			String query = "";
		
			if(!id_novy.equals("")){
				query = "UPDATE zamestnanec_zmluva SET id = " + id_novy + 
						" WHERE id = " + id_povodny + " ;";
				executeStatementUpdate(query);
			}
			
			if(!prve_meno.equals("")){
				query = "UPDATE zamestnanec_zmluva SET " +
						" prve_meno = '" + prve_meno + 
						"' WHERE id = " + id_povodny + " ;";
				executeStatementUpdate(query);
			}
			
			if(!priezvisko.equals("")){
				query = "UPDATE zamestnanec_zmluva SET " +  
						" priezvisko = '" + priezvisko +
						"' WHERE id = " + id_povodny + " ;";
				executeStatementUpdate(query);
			}
			
			if(!datum_nastupu.equals("")){
				query = "UPDATE zamestnanec_zmluva SET " +
						"datum_nastup = '" + datum_nastupu +
						"' WHERE id = " + id_povodny + " ;";
				executeStatementUpdate(query);
			}
			
			if(!datum_ukoncenia.equals("")){
				query = "UPDATE zamestnanec_zmluva SET " +
						"datum_skoncenia_platnosti = '" + datum_ukoncenia + 
						"' WHERE id = " + id_povodny + " ;";
				executeStatementUpdate(query);
			}	
	}

	public ObservableList<String> getAllNames() {
		
		//if(predajcaMap == null){
			predajcaMap = new HashMap<String,String>();
			String query = "SELECT zm.priezvisko,zm.prve_meno,zm.id FROM zamestnanec_zmluva zm";
			System.out.println(query);
			List<ResultRow> rrlst = executeStatement(query);
			for (ResultRow rr : rrlst){
				predajcaMap.put(rr.getRowDataList().get(0) + " " + rr.getRowDataList().get(1), rr.getRowDataList().get(2));
		    }
		
		ObservableList<String> ret = FXCollections.observableArrayList();
		for(Entry<String,String> e : predajcaMap.entrySet()){
			ret.add(e.getKey());
		}
		
		return ret;
	}
	
	/**
	 * Vymaže záznam z databázy
	 * @param id_zamestnanec
	 */
	public void delete(String id_zamestnanec) {
		String query = "";	
		query = "DELETE FROM zamestnanec_zmluva WHERE id = " + id_zamestnanec + " ;";
		System.out.println(query);
		executeStatementUpdate(query);
	}
	
	public static String convertNameToId(String name){
		return predajcaMap.get(name);
	}

	


	
}
