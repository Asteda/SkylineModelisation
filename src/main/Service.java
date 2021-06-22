package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVRecord;

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
}
