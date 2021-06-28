package test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.*;

import main.Mashup;
import main.Service;

public class MainTest {
	
	public static Logger log = Logger.getLogger(MainTest.class);
	
	
	public static void main(String[] args) throws IOException {
		
		Mashup.lireCSVMashups();
		
		
	}
	
	
	
	
	
	

}
