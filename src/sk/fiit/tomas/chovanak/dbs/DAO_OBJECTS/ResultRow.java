package sk.fiit.tomas.chovanak.dbs.DAO_OBJECTS;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultRow {

	private ResultSet rs;
	
	private List<String> rowDataList = new ArrayList<String>();
	private List<String> rowColumnList = new ArrayList<String>();
	
	public ResultRow(ResultSet rs) {
		this.rs = rs;
		retrieveDataList();
		retrieveColumnList();
	}
	
	
	
	private void retrieveColumnList() {
		try {
			ResultSetMetaData rsMeta = rs.getMetaData();
			for(int i = 0; i < rsMeta.getColumnCount(); i++){
				rowColumnList.add(rsMeta.getColumnLabel(i+1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	private void retrieveDataList() {
		try {
			ResultSetMetaData rsMeta = rs.getMetaData();
			for(int i = 0; i < rsMeta.getColumnCount(); i++){
				rowDataList.add(rs.getString(i+1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	
	public List<String> getRowColumnList() {
		return rowColumnList;
	}



	public List<String> getRowDataList() {
		return rowDataList;
	}
	
}
