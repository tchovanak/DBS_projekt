package sk.fiit.tomas.chovanak.dbs.DAO_OBJECTS;

import java.sql.Connection;

public class PredajProduktuDAO extends DAOobject {
	
	public PredajProduktuDAO(Connection connection) {
		super(connection);
	}

	public void insertNewPredaj(String date,String produkt, String id_klient, String id_predajca) {
		String query = "INSERT INTO predaj_produktu (datum,id_produkt,id_klient,id_zamestnanec) VALUES \n" + 
				"('"+ date + "'," + produkt + "," + id_klient + "," + id_predajca + ");";
		executeStatementUpdate(query);
	}
}
