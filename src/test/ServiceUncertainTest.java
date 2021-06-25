package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import uncertain.ServiceUncertain;

class ServiceUncertainTest {

	@Test
	public void testSommeProba() {
		Map<String, Map<Float, Float>> qos1 = new HashMap<>();
		Map<String, Map<Float, Float>> qos2 = new HashMap<>();
		
		Map<Float, Float> qos1_cost = new HashMap<>();
		Map<Float, Float> qos1_rt = new HashMap<>();
		
		Map<Float, Float> qos2_cost = new HashMap<>();
		Map<Float, Float> qos2_rt = new HashMap<>();
		
		qos1_cost.put(5f, 0.7f);
		qos1_cost.put(4f, 0.5f); // somme trop élevée
		
		qos1_rt.put(4f, 0.2f);
		qos1_rt.put(3f, 0.8f);
		
		qos2_cost.put(3f, 0.4f);
		qos2_cost.put(1f, 0.6f);
		
		qos2_rt.put(3f, 0.1f); // somme trop faible
		qos2_rt.put(4f, 0.2f);
		qos2_rt.put(5f, 0.5f);
		
		qos1.put("Cost", qos1_cost);
		qos1.put("ResponseTime", qos1_rt);
		
		qos2.put("Cost", qos2_cost);
		qos2.put("ResponseTime", qos2_rt);
		
		List<ServiceUncertain> services = new ArrayList<>();
		services.add(new ServiceUncertain(1, "s1", null, null, qos1)); // ce service ne devrait pas avoir de qos (somme trop élevée)
		services.add(new ServiceUncertain(2, "s2", null, null, qos2)); // celui-ci non plus (somme trop faible)
		
		assertTrue(services.get(0).getQoSUncertain() == null);
		assertTrue(services.get(1).getQoSUncertain() == null);
	}

}
