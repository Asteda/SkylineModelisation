package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.Mashup;
import main.Service;
import main.Skyline;
import main.Mashup.Operation;

class SkylineTest {
	
	private static Service s1, s2, s3, s4, s5, s6;
	private static Map<String, Mashup.Operation> param ;
	
	private static Mashup m1, m2, m3, m4;
	private static Logger log = Logger.getLogger(MainTest.class);
	
	public static Mashup[] mashups;
	public static Service[] services;
	
	
	@BeforeAll
	static void initMashup() {
		List<String> lod1 = new ArrayList<String>();
		lod1.add("dbpedia.org/resource/Restaurant");
		List<String> lod2 = new ArrayList<String>();
		lod2.add("dbpedia.org/resource/Hotel");
		List<String> lod3 = new ArrayList<String>();
		lod3.add("dbpedia.org/resource/Inn");
		List<String> lod4 = new ArrayList<String>();
		lod4.add("dbpedia.org/resource/Bread_and_breakfast");
		List<String> lod5 = new ArrayList<String>();
		lod5.add("dbpedia.org/resource/Hotel");
		List<String> lod6 = new ArrayList<String>();
		lod6.add("dbpedia.org/resource/Hotel");
		
		List<String> tags1 = new ArrayList<String>();
		tags1.add("restaurant");
		List<String> tags2 = new ArrayList<String>();
		tags2.add("Hotel");
		List<String> tags3 = new ArrayList<String>();
		tags3.add("Inn");
		List<String> tags4 = new ArrayList<String>();
		tags4.add("BnB");
		List<String> tags5 = new ArrayList<String>();
		tags5.add("Hotel");
		List<String> tags6 = new ArrayList<String>();
		tags6.add("Hotel");
		
		
		
		Map<String, Float> qos1 = new HashMap<>();
		qos1.put("ResponseTime", 2f);
		qos1.put("Cost", 5f);
		Map<String, Float> qos2 = new HashMap<>();
		qos2.put("ResponseTime", 4f);
		qos2.put("Cost", 7f);
		Map<String, Float> qos3 = new HashMap<>();
		qos3.put("ResponseTime", 4f);
		qos3.put("Cost", 3f);
		Map<String, Float> qos4 = new HashMap<>();
		qos4.put("ResponseTime", 3f);
		qos4.put("Cost", 8f);
		Map<String, Float> qos5 = new HashMap<>();
		qos5.put("ResponseTime", 3f);
		qos5.put("Cost", 5f);
		Map<String, Float> qos6 = new HashMap<>();
		qos6.put("ResponseTime", 5f);
		qos6.put("Cost", 10f);
		
		s1 = new Service(1, "s1", tags1, lod1, qos1);
		s2 = new Service(2, "s2", tags2, lod2, qos2);
		s3 = new Service(3, "s3", tags3, lod3, qos3);
		s4 = new Service(4, "s4", tags4, lod4, qos4);
		s5 = new Service(5, "s5", tags5, lod5, qos5);
		s6 = new Service(6, "s6", tags6, lod6, qos6);
		
		param = new HashMap<>();
		param.put("ResponseTime", Mashup.Operation.AVG);
		param.put("Cost", Mashup.Operation.SUM);
		
		ArrayList<Service> services = new ArrayList<>();
		services.add(s1);
		services.add(s2);
		services.add(s3);
		services.add(s4);
		
		m1 = new Mashup(1, "m1", null, null, services, null);
		
		m1.computeQoS(param);
		
		services = new ArrayList<>();
		services.add(s1);
		services.add(s3);
		services.add(s4);
		services.add(s5);
		
		m2 = new Mashup(2, "m2", null, null, services, null);
		
		m2.computeQoS(param);
		
		services = new ArrayList<>();
		services.add(s1);
		services.add(s2);
		services.add(s5);
		services.add(s6);
		
		m3 = new Mashup(3, "m3", null, null, services, null);
		
		m3.computeQoS(param);
		
		services = new ArrayList<>();
		services.add(s1);
		services.add(s5);
		services.add(s6);
		
		m4 = new Mashup(4, "m4", null, null, services, null);
		
		m4.computeQoS(param);
		
		Skyline.mashups = new ArrayList<>();
		Skyline.mashups.add(m1);
		Skyline.mashups.add(m2);
		Skyline.mashups.add(m3);
		Skyline.mashups.add(m4);
		
	}
	
	
	@Test
	void test1() {
		log.info(" ===== TEST 1 ===== ");
		
		Map<String, String> pref = new HashMap<>();
		pref.put("Cost", "<");
		pref.put("ResponseTime", "<");
		
		List<Mashup> res = Skyline.computeSkyline(Skyline.mashups, pref);
		
		assertEquals(res.size(), 2);
		
		assertTrue(res.get(0).getId() == 2);
		assertTrue(res.get(1).getId() == 4);
		
		String info = "Test 1, résultat = ";
		for(Mashup m: res) {
			info += m.getName() + ", ";
		}
		
		log.info(info);
		
	}
	
	@Test
	void test2() {
		log.info(" ===== TEST 2 ===== ");
		
		Map<String, String> pref = new HashMap<>();
		pref.put("Cost", ">");
		pref.put("ResponseTime", "<");
		
		List<Mashup> res = Skyline.computeSkyline(Skyline.mashups, pref);
		
		assertEquals(res.size(), 3);
		
		assertTrue(res.get(0).getId() == 1);
		assertTrue(res.get(1).getId() == 2);
		assertTrue(res.get(2).getId() == 3);
		
		String info = "Test 2, résultat = ";
		for(Mashup m: res) {
			info += m.getName() + ", ";
		}
		
		log.info(info);
	
	}
	
	@Test
	void test3() {
		log.info(" ===== TEST 3 ===== ");
		
		
		Map<String, String> pref = new HashMap<>();
		pref.put("Cost", "<");
		pref.put("ResponseTime", ">");
		
		List<Mashup> res = Skyline.computeSkyline(Skyline.mashups, pref);
		
		//System.out.println(res.toString());
		
		assertEquals(res.size(), 2);
		
		assertTrue(res.get(0).getId() == 3);
		assertTrue(res.get(1).getId() == 4);
		
		String info = "Test 3, résultat = ";
		for(Mashup m: res) {
			info += m.getName() + ", ";
		}
		
		log.info(info);
		
	}
	
	@Test
	void test4() {
		log.info(" ===== TEST 4 ===== ");
		
		
				
		Map<String, Float> qos = new HashMap<>();
		qos.put("ResponseTime", 1.5f);
		qos.put("Cost", 3500f);
		
		Mashup m1 = new Mashup(1, "m1", null, null, null, qos);
		
		qos = new HashMap<>();
		qos.put("ResponseTime", 2.5f);
		qos.put("Cost", 5500f);
		
		Mashup m2 = new Mashup(2, "m2", null, null, null, qos);

		qos = new HashMap<>();
		qos.put("ResponseTime", 4.5f);
		qos.put("Cost", 6500f);
		
		Mashup m3 = new Mashup(3, "m3", null, null, null, qos);

		qos = new HashMap<>();
		qos.put("ResponseTime", 9.5f);
		qos.put("Cost", 3500f);
		
		Mashup m4 = new Mashup(4, "m4", null, null, null, qos);

		qos = new HashMap<>();
		qos.put("ResponseTime", 10.3f);
		qos.put("Cost", 4500f);
		
		Mashup m5 = new Mashup(5, "m5", null, null, null, qos);

		qos = new HashMap<>();
		qos.put("ResponseTime", 14.7f);
		qos.put("Cost", 4000f);
		
		Mashup m6 = new Mashup(6, "m6", null, null, null, qos);

		qos = new HashMap<>();
		qos.put("ResponseTime", 8f);
		qos.put("Cost", 6000f);
		
		Mashup m7 = new Mashup(7, "m7", null, null, null, qos);

		qos = new HashMap<>();
		qos.put("ResponseTime", 7f);
		qos.put("Cost", 5500f);
		
		Mashup m8 = new Mashup(8, "m8", null, null, null, qos);

		qos = new HashMap<>();
		qos.put("ResponseTime", 6.5f);
		qos.put("Cost", 7000f);
		
		
		Mashup m9 = new Mashup(9, "m9", null, null, null, qos);

		qos = new HashMap<>();
		qos.put("ResponseTime", 1f);
		qos.put("Cost", 7500f);
		
		Mashup m10 = new Mashup(10, "m10", null, null, null, qos);
		
		Skyline.mashups = new ArrayList<>();
		Skyline.mashups.add(m1);
		Skyline.mashups.add(m2);
		Skyline.mashups.add(m3);
		Skyline.mashups.add(m4);
		Skyline.mashups.add(m5);
		Skyline.mashups.add(m6);
		Skyline.mashups.add(m7);
		Skyline.mashups.add(m8);
		Skyline.mashups.add(m9);
		Skyline.mashups.add(m10);
		
		Map<String, String> pref = new HashMap<>();
		pref.put("Cost", "<");
		pref.put("ResponseTime", "<");
		
		List<Mashup> res = Skyline.computeSkyline(Skyline.mashups, pref);
		
		if(res.size() != 2) log.debug("res.size() = " + res.size());
				
		assertEquals(res.size(), 2);
				
		assertTrue(res.get(0).getId() == 1);
		assertTrue(res.get(1).getId() == 10);
		
		String info = "Test 4, résultat = ";
		for(Mashup m: res) {
			info += m.getName() + ", ";
		}
		
		log.info(info);
		
	}
	
	
	@BeforeAll
	static void initServicesMashups() { /* pour test complet */
		services = new Service[9];
		mashups = new Mashup[6];
		Map<String, Float> qos;
		List<Service> s;
		float values[][] = {
				/*s1*/{1.5f, 3.5f}, /*s2*/{2.5f, 5.5f}, /*s3*/{4.5f, 6.5f}, /*s4*/{6.5f, 3.5f}, 
				/*s5*/{9f, 4.5f}, /*s6*/{10.7f, 4f}, /*s7*/{6f, 6f}, /*s8*/{5.5f, 5.5f}, /*s9*/{1f, 7f}
		};
		
		for(int i=0; i<services.length; i++) {
			qos = new HashMap<>();
			qos.put("ResponseTime", values[i][0]);
			qos.put("Cost", values[i][1]);
			services[i] = new Service(i+1, "s"+(i+1), null, null, qos);
		}
		
		int numServiceForMashup[][] = {
				/*m1*/ {1,2,3}, /*m2*/ {1,4,5,6}, /*m3*/ {1,5,7,8,9}, /*m4*/ {1,4,6}, /*m5*/ {1,4,9}, /*m6*/ {1,5,6,7,8,9}
		};
		
		param = new HashMap<>();
		param.put("ResponseTime", Operation.AVG);
		param.put("Cost", Operation.SUM);
		for(int i=0; i<mashups.length; i++) {
			s = new ArrayList<>();
			for(int j=0; j<numServiceForMashup[i].length; j++) {
				s.add(services[numServiceForMashup[i][j]-1]);
			}
			mashups[i] = new Mashup(i+1, "m"+(i+1), null, null, s, null);
			mashups[i].computeQoS(param);
		}
		
	}

	@Test 
	void test5() { /* verif skyline pour les mashups de la liste mashups */
		log.info(" ===== TEST 5 ===== ");
		// on est censé avoir 3 mashups : 2, 5 ,6 ?
		
		Skyline.mashups = new ArrayList<>();
		for(int i=0; i<mashups.length; i++) {
			Skyline.mashups.add(mashups[i]);
		}
		Map<String, String> pref = new HashMap<>();
		pref.put("Cost", "<");
		pref.put("ResponseTime", "<");
		List<Mashup> res = Skyline.computeSkyline(Skyline.mashups, pref);
		
		String texte="res=[";
		for(Mashup m: res) {
			texte+=m.getName()+" ";
		}
		log.info(texte.trim()+"]");
		
		
	}


}
