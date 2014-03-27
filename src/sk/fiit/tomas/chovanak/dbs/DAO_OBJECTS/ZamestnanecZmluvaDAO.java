package sk.fiit.tomas.chovanak.dbs.DAO_OBJECTS;

import java.sql.Connection;
import java.util.List;

import sk.fiit.tomas.chovanak.dbs.gui.ViewController;

public class ZamestnanecZmluvaDAO extends DAOobject{

	public ZamestnanecZmluvaDAO(Connection connection) {
		super(connection);
	}

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
		
		if(id_zamestnanec != ViewController.undefined && prve_meno != ViewController.undefined && priezvisko != ViewController.undefined){
			query = "SELECT * FROM zamestnanec_zmluva WHERE id = " + id_zamestnanec + " AND prve_meno = '" + prve_meno + "' AND priezvisko = '" + priezvisko + "';";
		}else if (prve_meno != ViewController.undefined && priezvisko != ViewController.undefined){
			query = "SELECT * FROM zamestnanec_zmluva WHERE prve_meno = '" + prve_meno + "' AND priezvisko = '" + priezvisko + "';";
		}else if (prve_meno != ViewController.undefined && id_zamestnanec != ViewController.undefined){
			query = "SELECT * FROM zamestnanec_zmluva WHERE prve_meno = '" + prve_meno + "' AND id = " + id_zamestnanec + ";";
		}else if (priezvisko != ViewController.undefined && id_zamestnanec != ViewController.undefined){
			query = "SELECT * FROM zamestnanec_zmluva WHERE priezvisko = '" + priezvisko + "' AND id = " + id_zamestnanec + ";";
		}else if (priezvisko != ViewController.undefined){
			query = "SELECT * FROM zamestnanec_zmluva WHERE priezvisko = '" + priezvisko + "';";
		}else if (prve_meno != ViewController.undefined){
			query = "SELECT * FROM zamestnanec_zmluva WHERE prve_meno = '" + prve_meno + "';";
		}else if (id_zamestnanec != ViewController.undefined){
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
		
			if(id_novy != ViewController.undefined){
				query = "UPDATE zamestnanec_zmluva SET id = " + id_novy + 
						" WHERE id = " + id_povodny + " ;";
				executeStatementUpdate(query);
			}
			
			if(prve_meno != ViewController.undefined){
				query = "UPDATE zamestnanec_zmluva SET " +
						" prve_meno = '" + prve_meno + 
						"' WHERE id = " + id_povodny + " ;";
				executeStatementUpdate(query);
			}
			
			if(priezvisko != ViewController.undefined){
				query = "UPDATE zamestnanec_zmluva SET " +  
						" priezvisko = '" + priezvisko +
						"' WHERE id = " + id_povodny + " ;";
				executeStatementUpdate(query);
			}
			
			if(datum_nastupu != ViewController.undefined){
				query = "UPDATE zamestnanec_zmluva SET " +
						"datum_nastup = '" + datum_nastupu +
						"' WHERE id = " + id_povodny + " ;";
				executeStatementUpdate(query);
			}
			
			if(datum_ukoncenia != ViewController.undefined){
				query = "UPDATE zamestnanec_zmluva SET " +
						"datum_skoncenia_platnosti = '" + datum_ukoncenia + 
						"' WHERE id = " + id_povodny + " ;";
				executeStatementUpdate(query);
			}	
	}
}
