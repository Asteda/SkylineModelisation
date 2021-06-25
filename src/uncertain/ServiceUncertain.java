package uncertain;

import java.util.List;
import java.util.Map;

import main.Service;

public class ServiceUncertain extends Service {

	public Map<String, Map<Float,Float>> QoS;
	
	public ServiceUncertain() {
		super();
	}
	
	public ServiceUncertain(int id, String name, List<String> tags, 
			List<String> lodTags, Map<String, Map<Float,Float>> qoS) {
		super.setId(id);
		super.setName(name);
		super.setTags(tags);
		super.setLodTags(lodTags);
		if(verifQoSProbas(qoS)) this.QoS = qoS;
		super.setProperties(null);
	}

	public ServiceUncertain(int id, String name, List<String> tags, 
			List<String> lodTags, Map<String, Map<Float,Float>> qoS, Map<String,String> properties) {
		super.setId(id);
		super.setName(name);
		super.setTags(tags);
		super.setLodTags(lodTags);
		this.QoS = qoS;
		super.setProperties(properties);
	}
	
	public Map<String, Map<Float,Float>> getQoSUncertain() {
		return this.QoS;
	}
	
	/**
	 * Permet de vérifier que la somme des probas est bien égale à 1 pour chaque QoS.
	 * @param qoS
	 * @return
	 */
	public static boolean verifQoSProbas(Map<String, Map<Float, Float>> qoS) {
		float somme;
		for (Map.Entry<String, Map<Float,Float>> mapentry : qoS.entrySet()) {
			somme=0;
			for (Map.Entry<Float,Float> couples : mapentry.getValue().entrySet()) {
				somme += couples.getValue();
			}
			if(somme < 0.95 || somme > 1.05) return false;
		}
		return true;
	}
	
	public String toString() {
		String res ="";
		
		res += "[Service] ID: " + this.getId() + " Name: " + this.getName() + "\n";
		
		res += "| Tags: ";
		if(this.getTags() == null) {
			res += "(no tag)";
		}
		else {
			for(String tag : this.getTags()) {
				res += tag + ", ";
			}
		}
		
		res += "\n" + "| LodTags: ";
		if(this.getLodTags() == null) {
			res += "(no LodTag)";
		}
		else {
			for(String tag : this.getLodTags()) {
				res += tag + ", ";
			}
		}
		res += "\n" + "| QoS: ";
		if(this.QoS == null) res += "null";
		else for (Map.Entry<String, Map<Float, Float>> mapentry : this.QoS.entrySet()) {
			res += mapentry.getKey() + "=>" + mapentry.getValue() + " / ";
	    }
		
		if(this.getProperties() != null && this.showProperties) {
			res += "\n" + "| Properties: \n";
			for (Map.Entry<String, String> mapentry : this.getProperties().entrySet()) {
				res += "- "+ mapentry.getKey() + "\t= " + mapentry.getValue() + " \n";
		    }
		}
		
		res += "\n-----";
		
		return res;
	}
	
	
	

}
