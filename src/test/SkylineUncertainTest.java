package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.Mashup;
import uncertain.MashupUncertain;
import uncertain.SkylineUncertain;

class SkylineUncertainTest {
	public static Logger log = Logger.getLogger(MainTest.class);
	
	static Map<Float, Float> qos1_w = new HashMap<>();
	static Map<Float, Float> qos2_w = new HashMap<>();
	static Map<Float, Float> qos1_c = new HashMap<>();
	static Map<Float, Float> qos2_c = new HashMap<>();
	static Map<String, Map<Float,Float>> qos1, qos2;
	static List<MashupUncertain> liste = new ArrayList<>();
	static Map<String, String> qospref = new HashMap<>();
	static MashupUncertain m1, m2;
	
	public static MashupUncertain[] mashups;
	
	@BeforeAll
	static void initialisation() {
		qos1_w.put(2f, 0.4f);
		qos1_w.put(6f, 0.1f);
		qos1_w.put(7f, 0.15f);
		qos1_w.put(8f, 0.35f);
		qos2_w.put(2f, 0.35f);
		qos2_w.put(5f, 0.4f);
		qos2_w.put(6f, 0.1f);
		qos2_w.put(7f, 0.15f);
		qos1_c.put(2f, 0.2f);
		qos1_c.put(5f, 0.3f);
		qos1_c.put(7f, 0.4f);
		qos1_c.put(8f, 0.1f);
		qos2_c.put(2f, 0.2f);
		qos2_c.put(5f, 0.4f);
		qos2_c.put(8f, 0.15f);
		qos2_c.put(9f, 0.25f);
		
		qos1 = new HashMap<>();
		qos1.put("weight", qos1_w);
		qos1.put("cost", qos1_c);
		qos2 = new HashMap<>();
		qos2.put("weight", qos2_w);
		qos2.put("cost", qos2_c);
		
		m1 = new MashupUncertain(1, "m1", null, null, null, qos1);
		m2 = new MashupUncertain(2, "m2", null, null, null, qos2);
		
		liste.add(m1);
		liste.add(m2);

		qospref.put("cost", "<=");
		qospref.put("weight", "<=");
		
		MashupUncertainTest.initialiserServices();
		mashups = MashupUncertainTest.mashups;
	}

	@Test
	void testBel1() {
		Map<Float, Float> qos1_w = new HashMap<>();
		Map<Float, Float> qos2_w = new HashMap<>();
		
		qos1_w.put(2f, 0.4f);
		qos1_w.put(6f, 0.1f);
		qos1_w.put(7f, 0.15f);
		qos1_w.put(8f, 0.35f);
		
		qos2_w.put(2f, 0.35f);
		qos2_w.put(5f, 0.4f);
		qos2_w.put(6f, 0.1f);
		qos2_w.put(7f, 0.15f);
		
		float q = SkylineUncertain.bel(qos1_w, qos2_w, "<="); // expected 0.4475
		
		assertEquals(q, 0.4475, 0.01);
	}
	
	@Test
	void testBel2() {
		Map<Float, Float> qos1_c = new HashMap<>();
		Map<Float, Float> qos2_c = new HashMap<>();
		
		qos1_c.put(2f, 0.2f);
		qos1_c.put(5f, 0.3f);
		qos1_c.put(7f, 0.4f);
		qos1_c.put(8f, 0.1f);
		
		qos2_c.put(2f, 0.2f);
		qos2_c.put(5f, 0.4f);
		qos2_c.put(8f, 0.15f);
		qos2_c.put(9f, 0.25f);
		
		float q = SkylineUncertain.bel(qos1_c, qos2_c, "<="); // expected 0.64
		
		assertEquals(q, 0.64, 0.01);
	}
	
	@Test
	void testComputeSkyline_1() {
		
		List<MashupUncertain> m = SkylineUncertain.computeUncertainSkyline(liste, qospref, 0.1f);
		
		assertEquals(m.size(), 2);
	}
	
	@Test
	void testComputeSkyline_2() {
		
		List<MashupUncertain> m = SkylineUncertain.computeUncertainSkyline(liste, qospref, 0.3f);
		
		assertEquals(m.size(), 1);
	}
	
	@Test
	void testComputeSkyline_3() {
		
		List<MashupUncertain> m = SkylineUncertain.computeUncertainSkyline(liste, qospref, 0.5f);
		
		assertEquals(m.size(), 0);
	}
	
	@Test
	void testComputeSkyline_tableau() {
		Map<String, String> qospref = new HashMap<>();
		qospref.put("Cost", "<=");
		qospref.put("ResponseTime", "<=");
		ArrayList<MashupUncertain> m = new ArrayList<>();
		for(int i=0; i<mashups.length; i++) m.add(mashups[i]);
		
		List<MashupUncertain> liste = 
				SkylineUncertain.computeUncertainSkyline(m, qospref, 0.3f);
		
		String texte="res=[";
		for(Mashup ma: liste) {
			texte+=ma.getName()+" ";
		}
		log.info(texte.trim()+"]");
		
	}
	

}
