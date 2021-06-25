package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import main.Mashup;
import uncertain.MashupUncertain;
import uncertain.ServiceUncertain;

class MashupUncertainTest {
	
	
	public static Logger log = Logger.getLogger(MainTest.class);
	
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
	

}
