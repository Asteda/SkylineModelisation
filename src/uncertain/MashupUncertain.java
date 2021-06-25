package uncertain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Mashup;
import main.Service;

public class MashupUncertain extends Mashup {
	
	
	private Map<String, Map<Float,Float>> QoS;
	private List<ServiceUncertain> services;
	
	public MashupUncertain() {
		super();
	}
	
	public MashupUncertain(int id, String name, List<String> tags, List<String> logTags, 
			List<ServiceUncertain> services, Map<String, Map<Float,Float>> qoS) {
		super.setId(id);
		super.setName(name);
		super.setTags(tags);
		super.setLodTags(logTags);
		this.services = services;
		this.QoS = qoS;
	}
	
	/*public static MashupUncertain generateMashup(int nMaxServices) {
		
		int nbServices = (int)(Math.random()*(nMaxServices)+1);
		System.out.println("Nb services : " + nbServices);
		
		List<ServiceUncertain> services = new ArrayList<>();
		Map<String, Float> qos;
		
		// Génération services
		for(int i=1; i<=nbServices; i++) {
			qos = new HashMap<String, Float>();
			
			qos.put("ResponseTime", (float)(Math.random()*10));
			qos.put("Cost", (float)(Math.random()*10));
			
			services.add(new ServiceUncertain(i, "s"+i, null, null, qos));
		}
		
		// Génération du mashup
		MashupUncertain m = new MashupUncertain(1, "m1", null, null, services, null);
		
		
		return m;
		
	}
	*/
	
	
	public Map<String, Map<Float, Float>> getQoSUncertain() {
		return QoS;
	}

	public List<ServiceUncertain> getServicesUncertain() {
		return services;
	}

	public void setQoSUncertain(Map<String, Map<Float, Float>> qoS) throws Exception {
		if(!verifQoSProbas(qoS))
			throw new Exception("The sum of the probabilities is not equal to 1, please provide a correct QoS");
		QoS = qoS;
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

	public void setServicesUncertain(List<ServiceUncertain> services) {
		this.services = services;
	}

	/**
	 * Permet de calculer les valeurs des QoS du mashup en appliquant les opérations 
	 * d’agrégations passées en paramètres.
	 * @param QoSAgreg opérations d'agrégation
	 */
	public void computeQoS(Map<String, Mashup.Operation> QoSAgreg) { 
		//log.debug("appel à computeQoS dans MashupUncertain");
		if(this.getServicesUncertain() != null) {
			this.QoS = new HashMap<>();
			for (Map.Entry<String, Mashup.Operation> mapentry : QoSAgreg.entrySet()) {
				this.QoS.put(mapentry.getKey(), 
						MashupUncertain.computeOneQoSUncertain(this.services, 
								mapentry.getKey(), mapentry.getValue()));
		    }
			if(!verifQoSProbas(this.QoS)) log.warn("The sum of the probabilities is not equal to 1 !");
			
		}
	}
	
	/**
	 * Calcule la valeur d'un QoS grâce aux valeurs de ce QoS dans les services de `services`.
	 * @param services liste des services
	 * @param qosName nom du QoS pour lequel on doit calculer la valeur
	 * @param op opérateur d'agrégation pour calculer le QoS. Valeurs possibles : avg, sum
	 * @return valeur du QoS pour le Mashup en fonction de l'opérateur d'agrégation
	 */
	private static Map<Float, Float> computeOneQoSUncertain(List<ServiceUncertain> services, String qosName, Mashup.Operation op) {
		if(services.size() == 0) {
			return null;
		}
		
		
		List<List<Float>> qosValues = new ArrayList<>();
		List<List<Float>> qosProbas = new ArrayList<>();
		
		// trouver la taille maximale des QoS parmi les services
		int max=0;
		for(ServiceUncertain s: services) {
			if(s.getQoSUncertain().get(qosName).size() > max) {
				max = s.getQoSUncertain().get(qosName).size();
			}
		}
		//log.debug("max trouvé : " + max);
		
		// pré-remplissage des L1 et L2
		for(int i=0; i<max; i++) {
			qosValues.add(new ArrayList<>());
			qosProbas.add(new ArrayList<>());
		}
		
		for(ServiceUncertain s: services) {
			int qosValueIndex=-1;
			for (Map.Entry<Float, Float> mapentry : s.getQoSUncertain().get(qosName).entrySet()) {
				qosValueIndex++;
				qosValues.get(qosValueIndex).add(mapentry.getKey());
				qosProbas.get(qosValueIndex).add(mapentry.getValue());
			}
			if(qosValueIndex < max) {
				// si ce service n'a pas assez de valeurs, remplir le reste de zéros
				for(int k=qosValueIndex+1; k<max; k++) {
					//log.debug("Ajouter un 0 à "+k);
					qosValues.get(k).add(0f);
					qosProbas.get(k).add(0f);
				}
			}
		}
		
		//log.debug("qosValues ("+qosName+") : "+qosValues.toString());
		
		List<Float> qosValuesAgreg = new ArrayList<>();
		List<Float> qosProbasAgreg = new ArrayList<>();
		
		float value=0;
		for(int i=0; i<max; i++) {
			switch(op) {
			case AVG:
				value = (float)qosValues.get(i).stream().mapToDouble(x -> x).average().orElse(0);
				break;
			case SUM:
				value = (float)qosValues.get(i).stream().mapToDouble(x -> x).sum();
				break;
			default: value=0;
			}
			qosValuesAgreg.add(value);
			qosProbasAgreg.add((float)qosProbas.get(i).stream().mapToDouble(x -> x).average().orElse(0));
		}
		
		Map<Float, Float> res = new HashMap<>();
		List<Integer> indexes;
		float proba;
		for(int i=0; i<max; i++) {
			proba=qosProbasAgreg.get(i);
			if(res.containsKey(qosValuesAgreg.get(i))) {
				proba += res.get(qosValuesAgreg.get(i));
			}
			res.put(qosValuesAgreg.get(i), proba);
		}
		
		//log.debug("qosValuesAgreg ("+qosName+") : "+qosValuesAgreg.toString());
		
		return res;
		
		
		
	}
	
	private static List<Integer> getIndexes(List<Float> list, float search) {
		List<Integer> res = new ArrayList<>();
		for(int i=0; i<list.size(); i++) {
			if(list.get(i).equals(search)) res.add(i);
		}
		return res;
	}
	
	public String toString() {
		String res ="";
		
		res += "[Mashup] ID: " + this.getId() + " Name: " + this.getName() + "\n";
		
		res += "Tags: ";
		if(super.getTags() == null) {
			res += "(no tag)";
		}
		else {
			for(String tag : super.getTags()) {
				res += tag + ", ";
			}
		}
		
		res += "\n" + "LodTags: ";
		if(this.getLogTags() == null) {
			res += "(no LodTag)";
		}
		else {
			for(String tag : this.getLogTags()) {
				res += tag + ", ";
			}
		}
		
		res += "\n" + "QoS: ";
		if(this.QoS == null) {
			res += "null";
		}
		else {
			for (Map.Entry<String, Map<Float,Float>> mapentry : this.QoS.entrySet()) {
				res += mapentry.getKey() + " => " +mapentry.getValue().toString() + "\n";
		    }	
		}
			
		
		res += "\nServices: ("+this.services.size()+")\n";
		for(Service s : this.services) {
			res += s.toString() + "\n"; 
		}
		
		if(this.getProperties() != null && this.showProperties) {
			res += "\n" + "Properties: \n";
			for (Map.Entry<String, String> mapentry : this.getProperties().entrySet()) {
				res += "- "+ mapentry.getKey() + "\t= " + mapentry.getValue() + " \n";
		    }
		}
		
		
		
		return res;
	}
	
	

}
