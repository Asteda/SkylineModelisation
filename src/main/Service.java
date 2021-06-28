package main;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;

import test.MainTest;

public class Service {
	
	/**
	 * Identifiant du service
	 */
	private int id;
	/**
	 * Nom du service
	 */
	private String name;
	/**
	 * Liste de mots clés décrivant le service
	 */
	private List<String> tags;
	/**
	 * Liste de ressources LOD correspondant aux mots clés du service
	 */
	private List<String> LodTags;
	/**
	 * Une Map pour stocker les paires de chaque QoS du service, sous la forme <nomQos, valeur Qos>
	 */
	private Map<String, Float> QoS;
	/**
	 * Propriétés du service
	 */
	private Map<String, String> properties;
	
	/**
	 * Précise si on affiche ou non les properties du service lors du toString.
	 */
	public boolean showProperties;
	
	public static enum APIHeaders {
		name, providersPage, homePage, endpoint, version, type, architecturalStyle, 
		deviceSpecific, scope, primaryCategory, secondaryCategories, description,
		related, howDifferent, docsPage, twitterURL, supportEmail, forum, interactiveConsole,
		termsOfService, descriptionFile, descriptionFileType, nonProprietary, SSLsupport, 
		authenticationModel, supportedRequestFormats, otherRequestFormat, responseFormats,
		hypermediaAPI, restrictedAccess, unofficialAPI, submitted
	}
	
	private static Logger log = Logger.getLogger(MainTest.class);
	
	public Service() {
		this.id = 0;
		this.name = null;
		this.tags = null;
		LodTags = null;
		QoS = null;
		this.properties = null;
	}




	public Service(int id, String name, List<String> tags, List<String> lodTags, Map<String, Float> qoS) {
		this.id = id;
		this.name = name;
		this.tags = tags;
		LodTags = lodTags;
		QoS = qoS;
		this.properties = null;
	}

	public Service(int id, String name, List<String> tags, List<String> lodTags, Map<String, Float> qoS, Map<String,String> properties) {
		this.id = id;
		this.name = name;
		this.tags = tags;
		LodTags = lodTags;
		QoS = qoS;
		this.properties = properties;
	}
	


	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<String> getTags() {
		return tags;
	}

	public List<String> getLodTags() {
		return LodTags;
	}

	public Map<String, Float> getQoS() {
		return QoS;
	}
	
	public Map<String, String> getProperties() {
		return properties;
	}




	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void setLodTags(List<String> lodTags) {
		LodTags = lodTags;
	}

	public void setQoS(Map<String, Float> qoS) {
		QoS = qoS;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}




	/**
	 * Permet de mesurer la similarité entre les tags d’un service et la liste de ressources fournie en paramètre
	 * @param resources liste des ressources à comparer
	 * @return similarité entre les tags du Service et la liste des ressources
	 */
	public double computeSimilarity(List<String> resources) {
		
		return 0d;
	}
	
	public String toString() {
		String res ="";
		
		res += "[Service] ID: " + this.id + " Name: " + this.name + "\n";
		
		res += "| Tags: ";
		if(this.tags == null) {
			res += "(no tag)";
		}
		else {
			for(String tag : this.tags) {
				res += tag + ", ";
			}
		}
		
		res += "\n" + "| LodTags: ";
		if(this.LodTags == null) {
			res += "(no LodTag)";
		}
		else {
			for(String tag : this.LodTags) {
				res += tag + ", ";
			}
		}
		res += "\n" + "| QoS: ";
		for (Map.Entry<String, Float> mapentry : this.QoS.entrySet()) {
			res += mapentry.getKey() + "=" + mapentry.getValue() + " / ";
	    }
		
		if(this.properties != null && this.showProperties) {
			res += "\n" + "| Properties: \n";
			for (Map.Entry<String, String> mapentry : this.properties.entrySet()) {
				res += "- "+ mapentry.getKey() + "\t= " + mapentry.getValue() + " \n";
		    }
		}
		
		res += "\n-----";
		
		return res;
	}
	
	
	
	
	/**
	 * Remplit l'attribut properties à partir d'un enregistrement CSVRecord.
	 * Cette méthode utilise l'énumération APIHeaders de cette classe Service. 
	 * Vous devez donc initiliser l'objet CSVRecord en lui précisant d'utiliser
	 * cette énumération comme headers. 
	 * @param record données sources
	 * @param headerslist liste des entêtes de données. La taille doit être la même que record
	 */
	public void hydrateProperties(CSVRecord record, List<String> headerslist) {
		if(headerslist.size() != record.size()) {
			log.error("Parameters haven't the same size");
			return;
		}
		properties = new HashMap<>();
		for(int i=0; i<headerslist.size(); i++) {
			properties.put(headerslist.get(i), record.get(i).replace("\"", "").trim());
		}
	}
	
	
	public static List<Service> lireCSVServices(List<String> names) throws IOException {

		Reader csvFile = new FileReader("./csv/APIV3.csv");
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(csvFile);
		
		List<Service> services = new ArrayList<>();
		Map<String, Float> qos;
		
		List<String> headerslist = new ArrayList<>();
		
		int id=0, indexOfName = -1;
		boolean add = false;
		for(CSVRecord record: records) {
			
			if(id==0) {
				// HEADERS
				for(int i=0; i<record.size(); i++) {
					headerslist.add(record.get(i).replace("\"", "").trim());
					if(indexOfName == -1 && record.get(i).contains("Name")) indexOfName = i;
				}
				if(indexOfName == -1) {
					log.warn("Index of name cannot be found, services will be named by the first column");
					indexOfName = 0;
				}
					
			}
			else {
				// DATA
				if(names != null) {
					if(names.contains(record.get(indexOfName))) {
						add=true;
					}
					else add=false;
				}
				else add=true;
				
				if(add) {
					
					qos = new HashMap<>();
					
					Service s = new Service(id, record.get(indexOfName).replaceAll("\"", "").trim(), 
							null, null, qos);
					s.hydrateProperties(record, headerslist);
					
					services.add(s);
				}
			}
			
			id++;
			
		}
		
		log.info("Taille: " + services.size());
		
		// afficher le premier service avec ses propriétés
		//services.get(0).showProperties = true;
		
		//log.info("Service 0 : \n" + services.get(0).toString());
		
		return services;
		
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
	public static List<String> extraireNoms(String names) {
		List<String> n;
		
		String[] tabSplit = names.split(",");
		for(int i=0; i<tabSplit.length; i++) {
			tabSplit[i] = tabSplit[i].replaceAll("\"", "").trim();
			
		}
		n = Arrays.asList(tabSplit);
		
		return n;
	}
}
