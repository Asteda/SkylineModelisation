package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.Service;

class ServiceTest {
	private static Service s1;
	
	private static List<String> LodTags;
	private static List<String> tags ;
	private static Map<String, Float> qos;
	
	@BeforeAll
	static void initMashup() {
		
		LodTags = new ArrayList<String>();
		LodTags.add("tag 1");
		LodTags.add("tag 2");
		LodTags.add("tag 3");
		
		tags = new ArrayList<String>();
		tags.add("tag 1");
		tags.add("tag 2");
		tags.add("tag 3");
		
		qos = new HashMap<>();
		qos.put("qos1", 0.62f);
		qos.put("qos2", 0.42f);
		qos.put("qos3", 0.65f);
		qos.put("qos4", 0.45f);
		
		s1 = new Service(1, "s1", tags, LodTags, qos);
	}

	@Test
	void testGetters() {
		assertEquals(1, s1.getId());
		assertTrue(s1.getName().equals("s1"));
		assertTrue(s1.getTags().equals(tags));
		assertTrue(s1.getLodTags().equals(LodTags));
		assertTrue(s1.getQoS().equals(qos));
	}

}
