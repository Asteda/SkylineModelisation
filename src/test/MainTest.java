package test;

import java.io.IOException;

import org.apache.log4j.*;

import main.Mashup;

public class MainTest {
	
	public static Logger log = Logger.getLogger(MainTest.class);
	
	
	public static void main(String[] args) throws IOException {
		
		/*Map<String, Map<Float, Float>> qos1 = new HashMap<>();
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
		
		log.info(m.toString());*/
		
		log.info(Mashup.Operation.AVG.toString());
		
		
	}
	
	
	
	
	
	

}
