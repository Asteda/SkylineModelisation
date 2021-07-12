package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import test.MainTest;

public class Hotel {
	private int id;
	private String name, period;
	private float price, distance;
	public static Logger log = Logger.getLogger(MainTest.class);
	public float pi, size;
	
	public Hotel() {
		this.id = 0;
		this.name = "";
		this.period = "[0,0]";
		this.price = 0;
		this.distance = 0;
	}

	public Hotel(int id, String name, String period, float price, float distance) {
		this.id = id;
		this.name = name;
		this.period = period;
		this.price = price;
		this.distance = distance;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPeriod() {
		return period;
	}

	public float getPrice() {
		return price;
	}

	public float getDistance() {
		return distance;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	public String toString() {
		return this.name;
	}
	
	/**
	 * Extrait une valeur pour une chaine de caractères de la forme "[valeur1,valeur2]"
	 * @param first true si on veut la valeur 1 ; false pour la valeur 2
	 * @param values chaine de caractères de la forme "[valeur1,valeur2]"
	 * @return valeur extraite
	 */
	private static Float extractValue(boolean first, String values) {
		char firstChar = first ? '[' : ',';
		char secondChar = first ? ',' : ']';
		
		String sous = values.substring(values.indexOf(firstChar)+1, values.indexOf(secondChar));
		Float a;
		try {
			a = Float.valueOf(sous);
		}
		catch(Exception e) {
			a = null;
		}
		
		return a;
	}
	
	private static Float b1(String values) {
		return extractValue(true, values);
	}
	private static Float b2(String values) {
		return extractValue(false, values);
	}
	
	public static boolean inter(String p1, String p2) {
		String inf, sup;
		if(b1(p1) < b1(p2)) {
			inf = p1;
			sup = p2;
		}
		else {
			inf = p2;
			sup = p1;
		}
		return b2(inf) >= b1(sup);
	}
	
	public static List<Hotel> dataTransform(List<Hotel> S, float threshold, 
			float step, String t, String periodPref) {
		List<Hotel> Sres = new ArrayList<>();
		String Lt;
		for(Hotel L: S) {
			Lt = L.getPeriod();
			if(b1(Lt) != null && b2(Lt) != null && inter(Lt, periodPref)) {
				L.pi = 0;
				L.size = Math.min(b2(Lt), b2(periodPref)) - Math.max(b1(Lt), b1(periodPref));
				Sres.add(L);
			}
			else if(b1(Lt) != null && b2(Lt) == null) {
				if(b1(Lt) >= b1(periodPref) && b1(Lt) <= b2(periodPref)) {
					L.pi = 0;
					L.size = 1;
					Sres.add(L);
				}
				else if(b1(Lt) < b1(periodPref) && (b1(periodPref) - b1(Lt)) <= threshold * step) {
					L.pi = b1(periodPref) - b1(Lt);
					L.size = 1;
					Sres.add(L);
				}
			}
			else if(b1(Lt) == null && b2(Lt) != null) {
				if(b2(Lt) >= b1(periodPref) && b2(Lt) <= b2(periodPref)) {
					L.pi = 0;
					L.size = 1;
					Sres.add(L);
				}
				else if(b2(Lt) > b2(periodPref) && (b2(Lt) - b2(periodPref)) <= threshold * step) {
					L.pi = b2(Lt) - b2(periodPref);
					L.size = 1;
					Sres.add(L);
				}
			}
		}
		
		return Sres;
		
	}
	
	public static List<Hotel> getListeComplet() {
		List<Hotel> l = new ArrayList<>();
		l.add(new Hotel(1, "a", "[1,10]", 15, 200));
		l.add(new Hotel(2, "b", "[4,8]", 25, 550));
		l.add(new Hotel(3, "c", "[6,10]", 45, 1000));
		l.add(new Hotel(4, "d", "[5,7]", 95, 200));
		l.add(new Hotel(5, "e", "[3,10]", 103, 350));
		l.add(new Hotel(6, "f", "[6,7]", 147, 275));
		l.add(new Hotel(7, "g", "[5,7]", 80, 850));
		l.add(new Hotel(8, "h", "[6,8]", 70, 670));
		l.add(new Hotel(9, "i", "[5,10]", 65, 1400));
		l.add(new Hotel(10, "j", "[5,12]", 10, 1300));
		return l;
	}
	
	public static List<Hotel> getListeIncomplet() {
		List<Hotel> l = new ArrayList<>();
		l.add(new Hotel(1, "a", "[4,8]", 15, 1200));
		l.add(new Hotel(2, "b", "[6,7]", 25, 550));
		l.add(new Hotel(3, "c", "[5,-]", 45, 1000));
		l.add(new Hotel(4, "d", "[-,7]", 95, 200));
		l.add(new Hotel(5, "e", "[-,8]", 103, 350));
		l.add(new Hotel(6, "f", "[4,-]", 147, 275));
		l.add(new Hotel(7, "g", "[-,9]", 80, 850));
		l.add(new Hotel(8, "h", "[3,-]", 70, 670));
		l.add(new Hotel(9, "i", "[-,10]", 65, 1400));
		l.add(new Hotel(10, "j", "[2,-]", 83, 1300));
		l.add(new Hotel(11, "k", "[-,-]", 120, 2300));
		return l;
	}
	
}
