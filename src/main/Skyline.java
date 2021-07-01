package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import test.MainTest;

public class Skyline {
	
	/**
	 * Liste de Mashup composant le Skyline
	 */
	public static List<Mashup> mashups;
	private static Logger log = Logger.getLogger(MainTest.class);

	/**
	 * Permet de calculer la liste de mashups composant le skyline.
	 * @param mashups
	 * @param QoSPref
	 * @return
	 */
	public static List<Mashup> computeSkyline(List<Mashup> mashups, Map<String, String> QoSPref) {
		
		boolean debug = true; /* Mettre à true pour obtenir des logs de déboggage */
		
		List<Mashup> result = new ArrayList<>();
		boolean dominated = false;
		
		for(int i=0; i<mashups.size(); i++) {
			for(int j=0; j<mashups.size(); j++) {
				dominated = true;
				if(debug) log.debug("Comparer m"+(i+1) + " avec m"+(j+1));
				if(mashups.get(i) != mashups.get(j)) {
					
					for (Map.Entry<String, String> mapentry : QoSPref.entrySet()) {
						if(debug) log.debug("Compare " + mapentry.getKey() + " par " + mapentry.getValue());
						if(compare(mashups.get(i).getQoS().get(mapentry.getKey()), 
								mashups.get(j).getQoS().get(mapentry.getKey()), 
								mapentry.getValue() ) ) {
							dominated = false;
							if(debug) log.debug(" m"+(i+1)+ " domine m"+(j+1)+"/ BREAK");
							break;
						}
						if(debug) log.debug(" m"+(i+1)+ " dominé par m"+(j+1));
						
				    }
					
					if(dominated) {
						if(debug) log.debug(" m"+(i+1) + " EST DOMINE");
						break;
					}
					
				}
				else dominated = false;
				
				if(debug) log.debug("dominated = " + dominated);
			}
			if(!dominated) {
				result.add(mashups.get(i));
				if(debug) log.debug("AJOUTER "+mashups.get(i).getName()+ " A LA LISTE");
			}
			if(debug) log.debug("FIN BOUCLE dominated = " + dominated);
		}
		
		return result;
		
	}
	
	protected static boolean compare(float value1, float value2, String operator) {
		
		switch(operator) {
		case "<":
			return value1 < value2;
		case "<=":
			return value1 <= value2;
		case ">":
			return value1 > value2;
		case ">=":
			return value1 >= value2;
		case "==":
			return value1 == value2;
		case "!=":
			return value1 != value2;
		default: return false;
		}
	}
}
