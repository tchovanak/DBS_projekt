package sk.fiit.tomas.chovanak.dbs.DAO_OBJECTS;

import java.sql.Connection;
import java.util.List;

public class StatisticsDAO extends DAOobject{

	public StatisticsDAO(Connection connection) {
		super(connection);
	}

	public String getStatisticsOfRegionsIncome(String ziskOd, String ziskDo, String produkt_id) {
		
		String query = "SELECT zisky.region, zisky.pocet_predajov, zisky.suma_zisku, plan.suma_plan, (zisky.suma_zisku/plan.suma_plan) as plnenie_planu " + 
		"FROM (SELECT pred.id_produkt,reg.id as id_region,reg.nazov as region, count(*) as pocet_predajov, sum(pred.objem_eur) as suma_zisku " +
		"FROM " +
			"(SELECT * FROM predaj_produktu " +
			"WHERE datum >= '" + ziskOd + "' AND datum <= '" + ziskDo + "') as pred " + 
		"JOIN zamestnanec_zmluva zm ON pred.id_zamestnanec = zm.id " +
		"JOIN vedenie_pobocky ved_pob ON zm.id = ved_pob.id_zamestnanec " + 
		"JOIN pobocka pob ON pob.id = ved_pob.id_pobocka " + 
		"JOIN region reg ON reg.id = pob.id_region " +
		"GROUP BY reg.id,pred.id_produkt,reg.nazov " + 
		"HAVING pred.id_produkt = " + produkt_id + ") " + 
		"as zisky " +
		"LEFT JOIN (SELECT p.id_region,p.id_produkt,sum(p.plan_objem) as suma_plan FROM " +
				"(SELECT p1.id_region,p1.id_produkt,p1.plan_objem FROM plan_predaja p1 " +
			   "WHERE p1.od_datum >= '" + ziskOd + "' AND p1.do_datum <= '" + ziskDo + "') as p " +
			   "GROUP BY p.id_region,p.id_produkt " +
		       "HAVING p.id_produkt = " + produkt_id + ") as plan " +
		"ON plan.id_region = zisky.id_region AND plan.id_produkt = zisky.id_produkt; "; 
		
		List<ResultRow> rrlst = executeStatement(query); 
		String output =  "region : pocet_predajov : suma_zisku : plan(eur) : plnenie_planu :\n\n";

		for(ResultRow rr : rrlst){
			int i = 0;
			for(String s : rr.getRowDataList()){
				System.out.println(s);
				if(s != null){
					if(i == 4){
						double doub = Double.parseDouble(s);
						int percentage = (int) Math.ceil((doub) * 100);
						output += percentage + "%";
					}else{
						output += s + " : ";
					}
				}else{
					output += " null : ";
				}
				i++;
			}
			output += "\n";
		}
		
		
		return output;
	}
	
	
	public String getStatisticsOfTopSellers(){
		String query = "WITH sub AS(" +
				"SELECT reg.id as region_id, reg.nazov as region,zm.id as zamestnanec_id,zm.prve_meno,zm.priezvisko,sum(pred.objem_eur) as zisk " +
				"FROM predaj_produktu pred " +
				"JOIN zamestnanec_zmluva zm ON pred.id_zamestnanec = zm.id " +
				"JOIN vedenie_pobocky ved_pob ON zm.id = ved_pob.id_zamestnanec " +
				"JOIN pobocka pob ON pob.id = ved_pob.id_pobocka " +
				"JOIN region reg ON reg.id = pob.id_region " +
				"GROUP BY reg.id,zm.id " +
				"ORDER BY reg.nazov,zisk desc " + 
				")" +
				", sub2 AS( " +
				"SELECT sub.region_id, sub.region, max(zisk) as maximalny_zisk " +
				"FROM sub " +
				"GROUP BY sub.region,sub.region_id " +
				")" +
				"SELECT sub.region,sub.prve_meno,sub.priezvisko, sub.zisk " +                  
				"FROM sub " +
				"JOIN sub2 ON sub.region_id = sub2.region_id " +
				"WHERE sub2.maximalny_zisk = sub.zisk AND sub2.region_id = sub.region_id; ";
		
		List<ResultRow> rrlst = executeStatement(query); 
		String output =  "region : prve_meno : priezvisko : zisk(eur)\n\n";

		for(ResultRow rr : rrlst){
			for(String s : rr.getRowDataList()){
				if(s != null){
					output += s + " : ";
				}else{
					output += " null : ";
				}
			}
			output += "\n";
		}
		return output;
	}

	public String getStatisticsOfSellsWithoutPermission(String id_zamestnanec) {
		String query = "SELECT count(*) FROM predaj_produktu pred " +
				"WHERE pred.datum > '1/1/2014' AND id_zamestnanec = " + id_zamestnanec + " AND " +
				"id_produkt NOT IN ( SELECT DISTINCT skol.id_produkt FROM skolenie_na_produkt skol " +
				"WHERE skol.id_zamestnanec_vyskoleny = " + id_zamestnanec + " ); ";
		
		List<ResultRow> rrlst = executeStatement(query); 
		String output =  "Následujúce číslo udáva počet predajov, \nktoré uskutočnil vybraný zamestnanec bez toho aby bol nato vyškolený\n\n";

		for(ResultRow rr : rrlst){
			for(String s : rr.getRowDataList()){
				if(s != null){
					output += s;
				}else{
					output += " null ";
				}
			}
			output += "\n";
		}
		return output;
	}
	

}
