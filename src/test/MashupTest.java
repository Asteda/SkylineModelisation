package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.Mashup;
import main.Service;

class MashupTest {
	
	private static Service s1, s2, s3, s4, s5, s6;
	private static Map<String, Mashup.Operation> param ;
	
	private static Mashup m1, m2, m3, m4;
	
	
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
		
	}

	@Test
	void testM1_ResponseTime() {
		assertTrue(m1.getQoS().get("ResponseTime") > 3.24 && m1.getQoS().get("ResponseTime") < 3.26);
	}
	@Test
	void testM1_Cost() {
		assertTrue(m1.getQoS().get("Cost") == 23);
	}
	
	@Test
	void testM2_ResponseTime() {
		assertTrue(m2.getQoS().get("ResponseTime") > 2.9 && m2.getQoS().get("ResponseTime") < 3.1);
	}
	@Test
	void testM2_Cost() {
		assertTrue(m2.getQoS().get("Cost") == 21);
	}
	
	@Test
	void testM3_ResponseTime() {
		assertTrue(m3.getQoS().get("ResponseTime") > 3.4 && m3.getQoS().get("ResponseTime") < 3.6);
	}
	@Test
	void testM3_Cost() {
		assertTrue(m3.getQoS().get("Cost") == 27);
	}
	
	@Test
	void testM4_ResponseTime() {
		assertTrue(m4.getQoS().get("ResponseTime") > 3.32 && m4.getQoS().get("ResponseTime") < 3.34);
	}
	@Test
	void testM4_Cost() {
		assertTrue(m4.getQoS().get("Cost") == 20);
	}
	
	

}
