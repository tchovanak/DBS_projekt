package sk.fiit.tomas.chovanak.dbs.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tools {
	public static String readFile( String file ) throws IOException {
	    @SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader( new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }

	    return stringBuilder.toString();
	}
	
	public static void main(String[] args){
		try {
			System.out.println(readFile("etc/query_statistics1"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
