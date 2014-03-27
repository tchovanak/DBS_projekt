package sk.fiit.tomas.chovanak.dbs.DAO_OBJECTS;

import java.sql.Connection;
import java.util.List;

public class StatisticsDAO extends DAOobject{

	public StatisticsDAO(Connection connection) {
		super(connection);
	}

	public String getStatisticsOfRegionsIncome() {
		String query = "SELECT zisky.region, zisky.pocet_predajov, zisky.suma_zisku, plan.plan_objem, (zisky.suma_zisku/plan.plan_objem) as plnenie_planu FROM " + 
						"(SELECT pred.id_produkt,reg.id as id_region,reg.nazov as region, count(*) as pocet_predajov, sum(pred.objem_eur) as suma_zisku " +
						"FROM predaj_produktu pred "+
						"JOIN zamestnanec_zmluva zm ON pred.id_zamestnanec = zm.id "+
						"JOIN vedenie_pobocky ved_pob ON zm.id = ved_pob.id_zamestnanec "+
						"JOIN pobocka pob ON pob.id = ved_pob.id_pobocka "+
						"JOIN region reg ON reg.id = pob.id_region "+
						"GROUP BY reg.id,pred.id_produkt,reg.nazov "+
						"HAVING pred.id_produkt = 1 "+
						") as zisky "+

						"JOIN plan_predaja plan ON plan.id_region = zisky.id_region AND plan.id_produkt = zisky.id_produkt "+
						"WHERE plan.od_datum >= '1/1/2014' AND plan.do_datum <= '31/1/2014';";
		
		
		List<ResultRow> rrlst = executeStatement(query);
		String output =  "region : pocet_predajov : suma_zisku : plan(eur) : plnenie_planu :\n\n";
		
		for(ResultRow rr : rrlst){
			int i = 0;
			for(String s : rr.getRowDataList()){
				
				if(i == 4){
					double doub = Double.parseDouble(s);
					int percentage = (int) Math.ceil((doub) * 100);
					output += percentage + "%";
				}else{
					output += s + " : ";
				}
				i++;
			}
			output += "\n";
		}
		return output;
	}
	
	

}
