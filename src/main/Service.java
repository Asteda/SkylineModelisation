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
	 * Liste de mots cl�s d�crivant le service
	 */
	private List<String> tags;
	/**
	 * Liste de ressources LOD correspondant aux mots cl�s du service
	 */
	private List<String> LodTags;
	/**
	 * Une Map pour stocker les paires de chaque QoS du service, sous la forme <nomQos, valeur Qos>
	 */
	private Map<String, Float> QoS;
	/**
	 * Propri�t�s du service
	 */
	private Map<String, String> properties;
	
	/**
	 * Pr�cise si on affiche ou non les properties du service lors du toString.
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
	 * Permet de mesurer la similarit� entre les tags d�un service et la liste de ressources fournie en param�tre
	 * @param resources liste des ressources � comparer
	 * @return similarit� entre les tags du Service et la liste des ressources
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
	 * Remplit l'attribut properties � partir d'un enregistrement CSVRecord.
	 * Cette m�thode utilise l'�num�ration APIHeaders de cette classe Service. 
	 * Vous devez donc initiliser l'objet CSVRecord en lui pr�cisant d'utiliser
	 * cette �num�ration comme headers. 
	 * @param record donn�es sources
	 */
	public void hydrateProperties(CSVRecord record) {
		
		properties = new HashMap<>();
		properties.put("API Provider's Home Page", record.get(APIHeaders.providersPage));
		properties.put("API Portal / Home Page", record.get(APIHeaders.homePage));
		properties.put("API Endpoint", record.get(APIHeaders.endpoint));
		properties.put("Version", record.get(APIHeaders.version));
		properties.put("Type", record.get(APIHeaders.type));
		properties.put("Architectural Style", record.get(APIHeaders.architecturalStyle));
		properties.put("Device Specific", record.get(APIHeaders.deviceSpecific));
		properties.put("Scope", record.get(APIHeaders.scope));
		properties.put("Primary Category", record.get(APIHeaders.primaryCategory));
		properties.put("Secondary Categories", record.get(APIHeaders.secondaryCategories));
		properties.put("API Description", record.get(APIHeaders.description));
		properties.put("Is the API Related to any other API ?", record.get(APIHeaders.related));
		properties.put("How is this API different ?", record.get(APIHeaders.howDifferent));
		properties.put("Docs Home Page URL", record.get(APIHeaders.docsPage));
		properties.put("Twitter URL", record.get(APIHeaders.twitterURL));
		properties.put("Support Email Address", record.get(APIHeaders.supportEmail));
		properties.put("API Forum / Message Boards", record.get(APIHeaders.forum));
		properties.put("Interactive Console URL", record.get(APIHeaders.interactiveConsole));
		properties.put("Terms Of Service URL", record.get(APIHeaders.termsOfService));
		properties.put("Description File URL (if public)", record.get(APIHeaders.descriptionFile));
		properties.put("Description File Type", record.get(APIHeaders.descriptionFileType));
		properties.put("Is the API Design/Description Non-Proprietary ?", record.get(APIHeaders.nonProprietary));
		properties.put("SSL Support", record.get(APIHeaders.SSLsupport));
		properties.put("Authentication Model", record.get(APIHeaders.authenticationModel));
		properties.put("Supported Request Formats", record.get(APIHeaders.supportedRequestFormats));
		properties.put("Other Request Format", record.get(APIHeaders.otherRequestFormat));
		properties.put("Supported Response Formats", record.get(APIHeaders.responseFormats));
		properties.put("Is This a Hypermedia API?", record.get(APIHeaders.hypermediaAPI));
		properties.put("Restricted Access ( Requires Provider Approval )", record.get(APIHeaders.restrictedAccess));
		properties.put("Is This an Unofficial API?", record.get(APIHeaders.unofficialAPI));
		properties.put("Submitted", record.get(APIHeaders.submitted));
		
	}
	
	
	public static List<Service> lireCSVServices(List<String> names) throws IOException {

		Reader csvFile = new FileReader("./csv/APIV3.csv");
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader()
				.withHeader(APIHeaders.class)
				.parse(csvFile);
		
		
		List<Service> services = new ArrayList<>();
		Map<String, Float> qos;
		
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
		
		// afficher le premier service avec ses propri�t�s
		//services.get(0).showProperties = true;
		
		//log.info("Service 0 : \n" + services.get(0).toString());
		
		return services;
		
	}
	
	/**
	 * Retourne la liste des services contenus dans liste dont le nom est contenu dans names.
	 * @param liste liste des services
	 * @param names liste des noms avec lesquels les services retourn�s doivent correspondre
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
	 * Retourne une ArrayList de noms, correctement formatt�s
	 * @param names liste des noms sous forme de chaine de caract�res, chaque nom est s�par� par une virgule
	 * @return liste des noms, o� les guillemets et les espaces aux extr�mit�s ont �t� supprim�s
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
