package test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.*;

import main.Mashup;
import main.Service;
import uncertain.MashupUncertain;
import uncertain.SkylineUncertain;

public class MainTest {
	
	public static Logger log = Logger.getLogger(MainTest.class);
	
	
	public static void main(String[] args) throws IOException {
		
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
		
		log.info(SkylineUncertain.bel(qos1_w, qos2_w, "<=")); // expected 0.4475
		
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
		
		log.info(SkylineUncertain.bel(qos1_c, qos2_c, "<=")); // expected 0.64
		
		Map<String, Map<Float,Float>> qos1, qos2;
		
		qos1 = new HashMap<>();
		qos1.put("weight", qos1_w);
		qos1.put("cost", qos1_c);
		
		qos2 = new HashMap<>();
		qos2.put("weight", qos2_w);
		qos2.put("cost", qos2_c);
		
		MashupUncertain m1 = new MashupUncertain(1, "m1", null, null, null, qos1);
		MashupUncertain m2 = new MashupUncertain(2, "m2", null, null, null, qos2);
		
		List<MashupUncertain> liste = new ArrayList<>();
		liste.add(m1);
		liste.add(m2);
		
		Map<String, String> qospref = new HashMap<>();
		qospref.put("cost", "<=");
		qospref.put("weight", "<=");
		
		List<MashupUncertain> m = SkylineUncertain.computeUncertainSkyline(liste, qospref, 0.3f);
		
		String res="";
		for(MashupUncertain a: m) {
			res += a.getName() + " ";
		}
		log.info("Mashups: "+res);
		
	}
	
	
	
	
	
	

}
