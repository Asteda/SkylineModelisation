package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Skyline {
	
	/**
	 * Liste de Mashup composant le Skyline
	 */
	public static List<Mashup> mashups;

	/**
	 * Permet de calculer la liste de mashups composant le skyline.
	 * @param mashups
	 * @param QoSPref
	 * @return
	 */
	public static List<Mashup> computeSkyline(List<Mashup> mashups, Map<String, String> QoSPref) {
		
		List<Mashup> result = new ArrayList<>();
		boolean dominated = false;
		
		for(int i=0; i<mashups.size(); i++) {
			dominated = true;
			for(int j=0; j<mashups.size(); j++) {
				
				if(mashups.get(i) != mashups.get(j)) {
					for (Map.Entry<String, String> mapentry : QoSPref.entrySet()) {
						if(compare(mashups.get(i).getQoS().get(mapentry.getKey()), 
								mashups.get(j).getQoS().get(mapentry.getKey()), 
								mapentry.getValue() ) ) {
							dominated = false;
							break;
						}
				    }
					
					if(dominated) {
						break;
					}
					
				}
				
			}
			if(!dominated) {
				result.add(mashups.get(i));
			}
		}
		
		return result;
		
	}
	
	private static boolean compare(float value1, float value2, String operator) {
		
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
