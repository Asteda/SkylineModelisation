package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.Mashup;
import main.Service;
import main.Mashup.Operation;
import uncertain.MashupUncertain;
import uncertain.ServiceUncertain;

class MashupUncertainTest {
	
	
	public static Logger log = Logger.getLogger(MainTest.class);
	
	public static MashupUncertain m1, m2, m3, m4, m5, m6;
	
	public static ServiceUncertain[] services = new ServiceUncertain[9];
	public static MashupUncertain[] mashups = new MashupUncertain[6];
	
	@BeforeAll
	static void initialiserServices() {
		Map<String, Map<Float, Float>> qos;
		Map<Float, Float> sous_qos;
		
		final Float values[][][][] = {
				/*s1*/ {
					/*RT*/ {{1.4f, 1.5f, 2f}, {0.2f, 0.6f, 0.2f}},
					/*Cost*/{{3.5f}, {1f}}
				},
				/*s2*/ {
					/*RT*/{{2.1f, 2.5f}, {0.1f, 0.9f}},
					/*Cost*/{{5.5f, 5.7f, 6f}, {0.5f, 0.3f, 0.2f}}
				},
				/*s3*/ {
					/*RT*/{{4f, 4.5f, 5f},{0.1f, 0.7f, 0.2f}},
					/*Cost*/{{6f, 6.5f, 7f},{0.2f, 0.7f, 0.1f}}
				},
				/*s4*/ {
					/*RT*/{{6.5f},{1f}},
					/*Cost*/{{3f, 3.5f},{0.5f, 0.5f}}
				},
				/*s5*/ {
					/*RT*/{{8.5f, 9f, 9.5f},{0.2f, 0.6f, 0.2f}},
					/*Cost*/{{4.5f, 5.5f},{0.8f, 0.2f}}
				},
				/*s6*/ {
					/*RT*/{{10f, 10.3f, 11f},{0.2f, 0.7f, 0.1f}},
					/*Cost*/{{3f, 4f},{0.5f, 0.5f}}
				},
				/*s7*/ {
					/*RT*/{{6f, 7f},{0.6f, 0.4f}},
					/*Cost*/{{5.5f, 6f},{0.2f,0.8f}}
				},
				/*s8*/ {
					/*RT*/{{4f, 5.5f},{0.2f, 0.8f}},
					/*Cost*/{{5f, 5.5f},{0.5f, 0.5f}}
				},
				/*s9*/ {
					/*RT*/{{1f, 5f, 10f},{0.3f, 0.5f, 0.2f}},
					/*Cost*/{{7f, 10f, 15f},{0.1f, 0.7f, 0.2f}}
				}
		};
		
		for(int num_service = 0; num_service < services.length; num_service++) {
			qos = new HashMap<>();
			
			for(int num_qos=0; num_qos < values[num_service].length; num_qos++) {
				sous_qos = new HashMap<>();
				
				for(int indice_val=0; indice_val<values[num_service][num_qos][0].length; indice_val++) {
					sous_qos.put(values[num_service][num_qos][0][indice_val], values[num_service][num_qos][1][indice_val]);
				}
				
				if(num_qos==0) /*RT*/ qos.put("ResponseTime", sous_qos);
				else if(num_qos==1) /*Cost*/ qos.put("Cost", sous_qos);
			}
			
			services[num_service] = new ServiceUncertain(num_service+1, "s"+(num_service+1), null, null, qos);
		}
		
		/*Verif*/
		//for(int i=0; i<services.length; i++) log.info("SERVICE "+(i+1)+" : \n" + services[i].toString());
		
		int numServiceForMashup[][] = {
				/*m1*/ {1,2,3}, /*m2*/ {1,4,5,6}, /*m3*/ {1,5,7,8,9}, /*m4*/ {1,4,6}, /*m5*/ {1,4,9}, /*m6*/ {1,5,6,7,8,9}
		};
		
		List<ServiceUncertain> s;
		Map<String,Mashup.Operation> param = new HashMap<>();
		param.put("ResponseTime", Operation.AVG);
		param.put("Cost", Operation.SUM);
		for(int i=0; i<mashups.length; i++) {
			s = new ArrayList<>();
			for(int j=0; j<numServiceForMashup[i].length; j++) {
				s.add(services[numServiceForMashup[i][j]-1]);
			}
			mashups[i] = new MashupUncertain(i+1, "m"+(i+1), null, null, s, null);
			mashups[i].computeQoS(param);
			//log.info(mashups[i].toString());
		}
		
	}
	
	@Test
	void testSimple() { 
		Map<String, Map<Float, Float>> qos1 = new HashMap<>();
		Map<String, Map<Float, Float>> qos2 = new HashMap<>();
		
		Map<Float, Float> qos1_cost = new HashMap<>();
		Map<Float, Float> qos1_rt = new HashMap<>();
		
		Map<Float, Float> qos2_cost = new HashMap<>();
		Map<Float, Float> qos2_rt = new HashMap<>();
		
		qos1_cost.put(5f, 0.7f);
		qos1_cost.put(4f, 0.3f);
		
		qos1_rt.put(1f, 0.2f);
		qos1_rt.put(2f, 0.8f);
		
		qos2_cost.put(3f, 0.4f);
		qos2_cost.put(1f, 0.6f);
		
		qos2_rt.put(3f, 0.3f);
		qos2_rt.put(4f, 0.2f);
		qos2_rt.put(5f, 0.5f);
		
		qos1.put("Cost", qos1_cost);
		qos1.put("ResponseTime", qos1_rt);
		
		qos2.put("Cost", qos2_cost);
		qos2.put("ResponseTime", qos2_rt);
		
		List<ServiceUncertain> services = new ArrayList<>();
		services.add(new ServiceUncertain(1, "s1", null, null, qos1));
		services.add(new ServiceUncertain(2, "s2", null, null, qos2));
		
		MashupUncertain m = new MashupUncertain(1, "m1", null, null, services, null);
		
		
		Map<String, Mashup.Operation> param = new HashMap<>();
		param.put("ResponseTime", Mashup.Operation.AVG);
		param.put("Cost", Mashup.Operation.SUM);
		m.computeQoS(param);
		
		//log.info(m.toString());
		
		assertTrue(m.getQoSUncertain().size() == 2);
		assertTrue(m.getQoSUncertain().get("Cost").size() == 2);
		assertTrue(m.getQoSUncertain().get("ResponseTime").size() == 3);
				
		assertEquals(m.getQoSUncertain().get("Cost").get(8.0f), 0.55, 0.001);
		assertEquals(m.getQoSUncertain().get("Cost").get(5.0f), 0.45, 0.001);
		assertEquals(m.getQoSUncertain().get("ResponseTime").get(2.0f), 0.25, 0.001);
		assertEquals(m.getQoSUncertain().get("ResponseTime").get(3.0f), 0.5, 0.001);
		assertEquals(m.getQoSUncertain().get("ResponseTime").get(2.5f), 0.25, 0.001);
		
	}
	@Test
	void testAvecFusion() { 
		Map<String, Map<Float, Float>> qos1 = new HashMap<>();
		Map<String, Map<Float, Float>> qos2 = new HashMap<>();
		
		Map<Float, Float> qos1_cost = new HashMap<>();
		Map<Float, Float> qos1_rt = new HashMap<>();
		
		Map<Float, Float> qos2_cost = new HashMap<>();
		Map<Float, Float> qos2_rt = new HashMap<>();
		
		qos1_cost.put(5f, 0.7f);
		qos1_cost.put(4f, 0.3f);
		
		qos1_rt.put(4f, 0.2f);
		qos1_rt.put(3f, 0.8f);
		
		qos2_cost.put(3f, 0.4f);
		qos2_cost.put(1f, 0.6f);
		
		qos2_rt.put(3f, 0.3f);
		qos2_rt.put(4f, 0.2f);
		qos2_rt.put(5f, 0.5f);
		
		qos1.put("Cost", qos1_cost);
		qos1.put("ResponseTime", qos1_rt);
		
		qos2.put("Cost", qos2_cost);
		qos2.put("ResponseTime", qos2_rt);
		
		List<ServiceUncertain> services = new ArrayList<>();
		services.add(new ServiceUncertain(1, "s1", null, null, qos1));
		services.add(new ServiceUncertain(2, "s2", null, null, qos2));
		
		MashupUncertain m = new MashupUncertain(1, "m1", null, null, services, null);
		
		
		Map<String, Mashup.Operation> param = new HashMap<>();
		param.put("ResponseTime", Mashup.Operation.AVG);
		param.put("Cost", Mashup.Operation.SUM);
		m.computeQoS(param);
		
		//log.info(m.toString());
		
		assertTrue(m.getQoSUncertain().size() == 2);
		assertTrue(m.getQoSUncertain().get("Cost").size() == 2);
		assertTrue(m.getQoSUncertain().get("ResponseTime").size() == 2);
				
		assertEquals(m.getQoSUncertain().get("Cost").get(8.0f), 0.55, 0.001);
		assertEquals(m.getQoSUncertain().get("Cost").get(5.0f), 0.45, 0.001);
		assertEquals(m.getQoSUncertain().get("ResponseTime").get(3.5f), 0.75, 0.001);
		assertEquals(m.getQoSUncertain().get("ResponseTime").get(2.5f), 0.25, 0.001);
		
	}
	
	@Test
	void testResponseTimeSum() { 
		Map<String, Map<Float, Float>> qos1 = new HashMap<>();
		Map<String, Map<Float, Float>> qos2 = new HashMap<>();
		
		Map<Float, Float> qos1_cost = new HashMap<>();
		Map<Float, Float> qos1_rt = new HashMap<>();
		
		Map<Float, Float> qos2_cost = new HashMap<>();
		Map<Float, Float> qos2_rt = new HashMap<>();
		
		qos1_cost.put(5f, 0.7f);
		qos1_cost.put(4f, 0.3f);
		
		qos1_rt.put(1f, 0.2f);
		qos1_rt.put(2f, 0.8f);
		
		qos2_cost.put(3f, 0.4f);
		qos2_cost.put(1f, 0.6f);
		
		qos2_rt.put(3f, 0.3f);
		qos2_rt.put(4f, 0.2f);
		qos2_rt.put(5f, 0.5f);
		
		qos1.put("Cost", qos1_cost);
		qos1.put("ResponseTime", qos1_rt);
		
		qos2.put("Cost", qos2_cost);
		qos2.put("ResponseTime", qos2_rt);
		
		List<ServiceUncertain> services = new ArrayList<>();
		services.add(new ServiceUncertain(1, "s1", null, null, qos1));
		services.add(new ServiceUncertain(2, "s2", null, null, qos2));
		
		MashupUncertain m = new MashupUncertain(1, "m1", null, null, services, null);
		
		
		Map<String, Mashup.Operation> param = new HashMap<>();
		param.put("ResponseTime", Mashup.Operation.SUM);
		param.put("Cost", Mashup.Operation.SUM);
		m.computeQoS(param);
		
		//log.info(m.toString());
		
		assertTrue(m.getQoSUncertain().size() == 2);
		assertTrue(m.getQoSUncertain().get("Cost").size() == 2);
		assertTrue(m.getQoSUncertain().get("ResponseTime").size() == 3);
				
		assertEquals(m.getQoSUncertain().get("Cost").get(8.0f), 0.55, 0.001);
		assertEquals(m.getQoSUncertain().get("Cost").get(5.0f), 0.45, 0.001);
		assertEquals(m.getQoSUncertain().get("ResponseTime").get(4.0f), 0.25, 0.001);
		assertEquals(m.getQoSUncertain().get("ResponseTime").get(6.0f), 0.5, 0.001);
		assertEquals(m.getQoSUncertain().get("ResponseTime").get(5.0f), 0.25, 0.001);
		
	}
	
	@Test
	public void testSommeProba() {
		Map<String, Map<Float, Float>> qos1 = new HashMap<>();
		Map<Float, Float> qos1_cost = new HashMap<>();
		Map<Float, Float> qos1_rt = new HashMap<>();
		
		qos1_cost.put(5f, 0.7f);
		qos1_cost.put(4f, 0.5f); // trop élevé
		
		qos1_rt.put(4f, 0.2f);
		qos1_rt.put(3f, 0.8f);
		
		qos1.put("Cost", qos1_cost);
		qos1.put("ResponseTime", qos1_rt);
		
		MashupUncertain m = new MashupUncertain();
		
		try {
			m.setQoSUncertain(qos1);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("The sum of the probabilities is not equal to 1"));
		}
		
		qos1_cost.put(4f, 0.1f); // trop faible
		try {
			m.setQoSUncertain(qos1);
		} catch (Exception e) {
			assertTrue(e.getMessage().contains("The sum of the probabilities is not equal to 1"));
		}
		
		qos1_cost.put(4f, 0.3f); // correct
		try {
			m.setQoSUncertain(qos1);
		} catch (Exception e) {
		}
		assertEquals(qos1, m.getQoSUncertain());
	
	}
	
	@Test
	public void testUncertainValues() {
		
		float[][][] expectedValues = {
				{//m1
					{2.5f, 0.133f, 2.833f, 0.733f, 2.333f, 0.133f}, /*RT*/
					{15f, 0.566f, 12.2f, 0.33f, 13f, 0.1f}          /*Cost*/
				},{//m2
					{6.725f, 0.5f, 5.25f, 0.225f, 5.45f, 0.275f},   /*RT*/
					{14f, 0.7f, 13f, 0.3f}                          /*Cost*/
				},{//m3
					{4.28f, 0.38f, 5.5f, 0.5f, 4.3f, 0.12f},        /*RT*/
					{25.5f, 0.52f, 27f, 0.44f, 15f, 0.04f}          /*Cost*/
				},{//m4
					{5.96f, 0.466f, 4.16f, 0.233f, 4.1f, 0.3f},     /*RT*/
					{9.5f, 0.66f, 7.5f, 0.333f}                     /*Cost*/
				},{//m5
					{2.96f, 0.5f, 2.16f, 0.366f, 4f, 0.133f},       /*RT*/
					{13.5f, 0.933f, 15f, 0.066f}                    /*Cost*/
				},{//m6
					{5.233f, 0.35f, 6.41f, 0.433f, 5.3f, 0.216f},   /*RT*/
					{28.5f, 0.516f, 31f, 0.45f, 15f, 0.033f}        /*Cost*/
				}
				
		};
		
		int a, b;
		for(int i=0; i<mashups.length; i++) {
			for(Entry<String, Map<Float, Float>> qos: mashups[i].getQoSUncertain().entrySet()) {
				a = qos.getKey().equals("Cost") ? 1 : 0;
				for(Map.Entry<Float, Float> val: qos.getValue().entrySet()) {
					b=find(expectedValues[i][a], val.getKey());
					//log.debug("i="+i+" a="+a+" b="+b+" b+1="+(b+1));
					assertEquals(expectedValues[i][a][b], val.getKey(), 0.01);
					assertEquals(expectedValues[i][a][b+1], val.getValue(), 0.01);
					b++;
				}
				
			}
		}
		
	}
	
	int find(float[] t, float val) {
		for(int i=0; i<t.length; i++) if(Math.abs(val - t[i]) < 0.01) return i;
		log.warn("Valeur introuvable : " +val );
		return 0;
	}
	
	
	
}
