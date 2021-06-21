package main;

import java.util.List;
import java.util.Map;

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
	
	
	
	
	public Service() {
		this.id = 0;
		this.name = null;
		this.tags = null;
		LodTags = null;
		QoS = null;
	}




	public Service(int id, String name, List<String> tags, List<String> lodTags, Map<String, Float> qoS) {
		this.id = id;
		this.name = name;
		this.tags = tags;
		LodTags = lodTags;
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

	public List<String> getLodTags() {
		return LodTags;
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

	public void setLodTags(List<String> lodTags) {
		LodTags = lodTags;
	}

	public void setQoS(Map<String, Float> qoS) {
		QoS = qoS;
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
		
		res += "\n-----";
		
		return res;
	}
}
