package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import main.Mashup;
import main.Mashup.MashupHeaders;
import main.Service;
import main.Service.APIHeaders;
import main.Skyline;

import org.apache.log4j.*;

public class MainTest {
	
	private static Logger log = Logger.getLogger(MainTest.class);
	
	
	public static void main(String[] args) throws IOException {
		
		//lireCSVServices(null);
		lireCSVMashups();
		
	}
	
	public static List<Service> lireCSVServices(List<String> names) throws IOException {

		Reader csvFile = new FileReader("./csv/APIV3.csv");
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader()
				.withHeader(APIHeaders.class)
				.parse(csvFile);
		
		
		List<Service> services = new ArrayList<>();
		Map<String, Float> qos;
		Map<String, String> properties;
		
		int id=0;
		boolean add = false;
		for(CSVRecord record: records) {
			if(names != null) {
				if(names.contains(record.get(APIHeaders.name))) {
					add=true;
				}
				else add=false;
			}
			else add=true;
			
			if(add) {
				id++;
				qos = new HashMap<>();
				
				Service s = new Service(id, record.get(APIHeaders.name).replaceAll("\"", "").trim(), null, null, qos);
				s.hydrateProperties(record);
				
				services.add(s);
			}
			
		}
		
		log.info("Taille: " + services.size());
		
		// afficher le premier service avec ses propriétés
		//services.get(0).showProperties = true;
		
		//log.info("Service 0 : \n" + services.get(0).toString());
		
		return services;
		
	}
	
	public static List<Mashup> lireCSVMashups() throws IOException {
		Reader csvFile = new FileReader("./csv/MashupV2.csv");
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader()
				.withHeader(MashupHeaders.class)
				.parse(csvFile);
		
		
		List<Service> services = new ArrayList<>();
		List<Mashup> mashups = new ArrayList<>();
		Map<String, Float> qos;
		Map<String, String> properties;
		
		List<Service> tousLesServices = lireCSVServices(null);
		
		long d = System.currentTimeMillis();
		
		int id=0;
		for(CSVRecord record: records) {
			id++;
			qos = new HashMap<>();
			
			services = extraireServices(tousLesServices, extraireNoms(record.get(MashupHeaders.relatedAPI)));
			
			Mashup m = new Mashup(id, record.get(MashupHeaders.name).replaceAll("\"", "").trim(), null, null, services, qos);
			m.hydrateProperties(record);
			
			mashups.add(m);
		}
		
		long duration = System.currentTimeMillis() -d;
		
		log.info("Taille mashups: " + mashups.size() + " (duration: "+duration+" ms)");
		
		// afficher le premier service avec ses propriétés
		//mashups.get(0).showProperties = true;
		
		//log.info("Mashup 0 : \n" + mashups.get(0).toString());
		
		// afficher le troisième service avec ses propriétés
		mashups.get(2).showProperties = true;
		
		log.info("Mashup 2 : \n" + mashups.get(2).toString());
		
		return mashups;
	}
	
	/**
	 * Retourne la liste des services contenus dans liste dont le nom est contenu dans names.
	 * @param liste liste des services
	 * @param names liste des noms avec lesquels les services retournés doivent correspondre
	 * @return liste des services dont le nom est dans names
	 */
	public static List<Service> extraireServices(List<Service> liste, List<String> names) {
		List<Service> services = new ArrayList<>();
		
		for(Service s: liste) {
			if(names.contains(s.getName())) services.add(s);
		}
		
		return services;
	}
	
	/**
	 * Retourne une ArrayList de noms, correctement formattés
	 * @param names liste des noms sous forme de chaine de caractères, chaque nom est séparé par une virgule
	 * @return liste des noms, où les guillemets et les espaces aux extrémités ont été supprimés
	 */
	private static List<String> extraireNoms(String names) {
		List<String> n;
		
		String[] tabSplit = names.split(",");
		for(int i=0; i<tabSplit.length; i++) {
			tabSplit[i] = tabSplit[i].replaceAll("\"", "").trim();
			
		}
		n = Arrays.asList(tabSplit);
		
		return n;
	}

}
