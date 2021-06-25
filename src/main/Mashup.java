package main;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;

import main.Mashup.MashupHeaders;
import main.Service.APIHeaders;
import test.MainTest;

public class Mashup {
	
	/**
	 * Identifiant du mashup
	 */
	private int id;
	/**
	 * Nom du mashup
	 */
	private String name;
	/**
	 * La liste de mots clés décrivant le mashup
	 */
	private List<String> tags;
	/**
	 * La liste de ressources LOD correspondant aux mots clés du mashup
	 */
	private List<String> LodTags;
	/**
	 * La liste des services composant le mashup
	 */
	private List<Service> services;
	/**
	 * Une Map pour stocker les paires de chaque QoS d'un service, sous la forme :
	 * <nom QoS, valeur QoS>. Les valeurs sont calculées à partir de la liste des
	 * services composant le mashup.
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
	
	/**
	 * Enumération permettant l'exploration du fichier csv MashupV2.csv
	 */
	public static enum MashupHeaders {
		name, company, url, primaryCategory, secondaryCategories, description, 
		relatedAPI /*comma separated*/, type, submitted
	}
	
	protected static Logger log = Logger.getLogger(MainTest.class);
	
	public Mashup() {
		this.id = 0;
		this.name = null;
		this.tags = null;
		LodTags = null;
		this.services = null;
		QoS = null;
	}

	public Mashup(int id, String name, List<String> tags, List<String> logTags, List<Service> services,
			Map<String, Float> qoS) {
		this.id = id;
		this.name = name;
		this.tags = tags;
		LodTags = logTags;
		this.services = services;
		QoS = qoS;
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

	public List<String> getLogTags() {
		return LodTags;
	}

	public List<Service> getServices() {
		return services;
	}

	public Map<String, Float> getQoS() {
		return QoS;
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

	public void setLodTags(List<String> logTags) {
		LodTags = logTags;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	public void setQoS(Map<String, Float> qoS) {
		QoS = qoS;
	}
	
	

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	/**
	 * Permet de générer aléatoirement un mashup. Le nombre de services 
	 * composant le mashup ne dépasse pas nMaxSevices. Après cette méthode
	 * il faut appeler computeQoS sur le Mashup généré.
	 * @param nMaxServices nombre maximum de services 
	 */
	public static Mashup generateMashup(int nMaxServices) {
		
		int nbServices = (int)(Math.random()*(nMaxServices)+1);
		System.out.println("Nb services : " + nbServices);
		
		List<Service> services = new ArrayList<>();
		Map<String, Float> qos;
		
		// Génération services
		for(int i=1; i<=nbServices; i++) {
			qos = new HashMap<String, Float>();
			
			qos.put("ResponseTime", (float)(Math.random()*10));
			qos.put("Cost", (float)(Math.random()*10));
			
			services.add(new Service(i, "s"+i, null, null, qos));
		}
		
		// Génération du mashup
		Mashup m = new Mashup(1, "m1", null, null, services, null);
		
		
		return m;
		
	}
	
	/**
	 * Permet de calculer les valeurs des QoS du mashup en appliquant les opérations 
	 * d’agrégations passées en paramètres.
	 * @param QoSAgreg opérations d'agrégation
	 */
	public void computeQoS(Map<String, String> QoSAgreg) { 
		if(this.services != null) {
			this.QoS = new HashMap<>();
			for (Map.Entry<String, String> mapentry : QoSAgreg.entrySet()) {
				this.QoS.put(mapentry.getKey(), 
						Mashup.computeOneQoS(this.services, mapentry.getKey(), mapentry.getValue()));
		    }
			
		}
	}
	
	/**
	 * Calcule la valeur d'un QoS grâce aux valeurs de ce QoS dans les services de `services`.
	 * @param services liste des services
	 * @param qosName nom du QoS pour lequel on doit calculer la valeur
	 * @param op opérateur d'agrégation pour calculer le QoS. Valeurs possibles : avg, sum
	 * @return valeur du QoS pour le Mashup en fonction de l'opérateur d'agrégation
	 */
	private static float computeOneQoS(List<Service> services, String qosName, String op) {
		float value=0;
		List<Float> qosValues = new ArrayList<>();
		for(Service s: services) {
			if(s.getQoS().get(qosName) != null) qosValues.add(s.getQoS().get(qosName));
		}
		if(qosValues.size() > 0) {
			switch(op) {
			case "avg":
				value = (float)qosValues.stream().mapToDouble(x -> x).average().orElse(0);
				break;
			case "sum":
				value = (float)qosValues.stream().mapToDouble(x -> x).sum();
				break;
			default: value=0;
			}
			
		}
		
		return value;
	}
	
	
	public String toString() {
		String res ="";
		
		res += "[Mashup] ID: " + this.id + " Name: " + this.name + "\n";
		
		res += "Tags: ";
		if(this.tags == null) {
			res += "(no tag)";
		}
		else {
			for(String tag : this.tags) {
				res += tag + ", ";
			}
		}
		
		res += "\n" + "LodTags: ";
		if(this.LodTags == null) {
			res += "(no LodTag)";
		}
		else {
			for(String tag : this.LodTags) {
				res += tag + ", ";
			}
		}
		
		res += "\n" + "QoS: ";
		for (Map.Entry<String, Float> mapentry : this.QoS.entrySet()) {
			res += mapentry.getKey() + "=" + mapentry.getValue() + " / ";
	    }
		
		res += "\nServices: ("+this.services.size()+")\n";
		for(Service s : this.services) {
			res += s.toString() + "\n"; 
		}
		
		if(this.properties != null && this.showProperties) {
			res += "\n" + "Properties: \n";
			for (Map.Entry<String, String> mapentry : this.properties.entrySet()) {
				res += "- "+ mapentry.getKey() + "\t= " + mapentry.getValue() + " \n";
		    }
		}
		
		
		
		return res;
	}
	
	public void hydrateProperties(CSVRecord record) {
		properties = new HashMap<>();
		properties.put("Company", record.get(MashupHeaders.company));
		properties.put("URL", record.get(MashupHeaders.url));
		properties.put("Primary Category", record.get(MashupHeaders.primaryCategory));
		properties.put("Secondary Categories", record.get(MashupHeaders.secondaryCategories));
		properties.put("Mashup Description", record.get(MashupHeaders.description));
		properties.put("Mashup/App Type", record.get(MashupHeaders.type));
		properties.put("Submitted", record.get(MashupHeaders.submitted));
	}
	
	/**
	 * Renvoie une liste de Mashup associés aux valeurs du fichier MashupV2.csv.
	 * @return liste des mashups
	 * @throws IOException le fichier n'existe pas
	 */
	public static List<Mashup> lireCSVMashups() throws IOException {
		Reader csvFile = new FileReader("./csv/MashupV2.csv");
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader()
				.withHeader(MashupHeaders.class)
				.parse(csvFile);
		
		
		List<Service> services = new ArrayList<>();
		List<Mashup> mashups = new ArrayList<>();
		Map<String, Float> qos;
		Map<String, String> properties;
		
		List<Service> tousLesServices = Service.lireCSVServices(null);
		
		long d = System.currentTimeMillis();
		
		int id=0;
		for(CSVRecord record: records) {
			id++;
			qos = new HashMap<>();
			
			services = Service.extraireServices(tousLesServices, Service.extraireNoms(record.get(MashupHeaders.relatedAPI)));
			
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
		mashups.get(2).getServices().get(0).showProperties=true;
		log.info("Mashup 2 : \n" + mashups.get(2).toString());
		
		return mashups;
	}
}
