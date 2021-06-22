package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import main.Mashup;
import main.Service;
import main.Service.APIHeaders;
import main.Skyline;

public class MainTest {
	
	
	public static void main(String[] args) throws IOException {
		
		lireCSVServices();
		
		
	}
	
	public static void lireCSVServices() throws IOException {

		Reader csvFile = new FileReader("./csv/APIV3.csv");
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader()
				.withHeader(APIHeaders.class)
				.parse(csvFile);
		
		
		List<Service> services = new ArrayList<>();
		Map<String, Float> qos;
		Map<String, String> properties;
		
		int id=0;
		for(CSVRecord record: records) {
			id++;
			qos = new HashMap<>();
			
			Service s = new Service(id, record.get(APIHeaders.name), null, null, qos);
			s.hydrateProperties(record);
			
			services.add(s);
		}
		
		System.out.println("Taille: " + services.size());
		
		// afficher le premier service avec ses propriétés
		services.get(0).showProperties = true;
		System.out.println("Service 0 : \n" + services.get(0).toString());
	}

}
