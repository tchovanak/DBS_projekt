package sk.fiit.tomas.chovanak.dbs.DAO_OBJECTS;

import java.sql.Connection;
import java.util.List;

public class PredajProduktuDAO extends DAOobject {
	
	public PredajProduktuDAO(Connection connection) {
		super(connection);
	}

	public void insertNewPredaj(String date,String produkt, String id_klient, String id_predajca, String objem_eur) {
		String query = "INSERT INTO predaj_produktu (datum,id_produkt,id_klient,id_zamestnanec,objem_eur) VALUES \n" + 
				"('"+ date + "'," + produkt + "," + id_klient + "," + id_predajca + ", " + objem_eur + ");";
		executeStatementUpdate(query);
	}

	public String searchForClient(String id_zamestnanec,String id_produkt) {
		String query = "SELECT kl.prve_meno,kl.priezvisko " +
						"FROM klient kl " +
						"WHERE kl.id IN (SELECT pred.id_klient " +
						"FROM predaj_produktu pred " +
						"WHERE pred.id_zamestnanec = (SELECT zm.id " +
						"FROM zamestnanec_zmluva zm " +
						"WHERE zm.id = " + id_zamestnanec + ") AND pred.id_produkt = " + id_produkt + ");";
		
		List<ResultRow> rrlst = executeStatement(query); 
		String output =  "Klienti ktorým predal vybraný zamestnanec vybraný produkt : \n prve meno : priezvisko \n\n";

		for(ResultRow rr : rrlst){
			for(String s : rr.getRowDataList()){
				if(s != null){
					output += s + " ";
				}else{
					output += " null : ";
				}
			}
			output += "\n";
		}
		return output;
	}
}
