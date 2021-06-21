package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	/**
	 * Actualise les valeurs des QoS en fonction de la liste des services.
	 */
	public void calculateQoS() {
		if(this.services != null) {
			this.QoS = new HashMap<>();
			float sommeResponseTime =0;
			float sommeCost =0;
			for(Service s : this.services) {
				sommeResponseTime += s.getQoS().get("ResponseTime");
				sommeCost += s.getQoS().get("Cost");
			}
			this.QoS.put("ResponseTime", sommeResponseTime / this.services.size());
			this.QoS.put("Cost", sommeCost);
		}
	}

	/**
	 * Permet de générer aléatoirement un mashup. Le nombre de services composant le mashup ne dépasse pas nMaxSevices. 
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
		m.calculateQoS();
		
		return m;
		
	}
	
	/**
	 * Permet de calculer les valeurs des QoS du mashup en appliquant les opérations d’agrégations passées en paramètres.
	 * @param QoSAgreg opérations d'agrégation
	 */
	public void computeQoS(Map<String, String> QoSAgreg) { // AVG ou SUM
		if(this.services != null) {
			this.QoS = new HashMap<>();
			float sommeResponseTime =0;
			float sommeCost =0;
			
			for(Service s : this.services) {
				sommeResponseTime += s.getQoS().get("ResponseTime");
				sommeCost += s.getQoS().get("Cost");
			}
			
			float responseTime=0;
			if(QoSAgreg.containsKey("ResponseTime")) {
				if(QoSAgreg.get("ResponseTime") == "SUM") {
					responseTime = sommeResponseTime;
				}
				else if (QoSAgreg.get("ResponseTime") == "AVG") {
					responseTime = sommeResponseTime / this.services.size();
				}
			}
			
			float cost=0;
			if(QoSAgreg.containsKey("Cost")) {
				if(QoSAgreg.get("Cost") == "SUM") {
					cost = sommeCost;
				}
				else if (QoSAgreg.get("Cost") == "AVG") {
					cost = sommeCost / this.services.size();
				}
			}
			
			this.QoS.put("ResponseTime", responseTime);
			this.QoS.put("Cost", cost);
			
		}
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
		
		res += "\nServices: \n";
		for(Service s : this.services) {
			res += s.toString() + "\n"; 
		}
		
		
		
		return res;
	}
}
