package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import main.Hotel;

class HotelTest {

	@Test
	void testInter() {
		assertTrue(Hotel.inter("[2,7]", "[4,9]")); // true
		assertTrue(Hotel.inter("[2,7]", "[2,7]")); // true
		assertFalse(Hotel.inter("[2,7]", "[8,9]")); // false
		assertTrue(Hotel.inter("[4,9]", "[2,7]")); // true
	}
	
	@Test
	void testComplet_1() {
		String periodPref = "[3,7]";
		List<Hotel> res = Hotel.dataTransform(Hotel.getListeComplet(), 2, 1, null, periodPref);
		//Hotel.log.info(res.toString());
		assertTrue(res.size() == 10);
		
	}
	
	@Test
	void testComplet_2() {
		String periodPref = "[2,4]";
		List<Hotel> res = Hotel.dataTransform(Hotel.getListeComplet(), 2, 1, null, periodPref);
		//Hotel.log.info(res.toString());
		assertTrue(res.size() == 3);
		assertTrue(res.get(0).getName() == "a");
		assertTrue(res.get(1).getName() == "b");
		assertTrue(res.get(2).getName() == "e");
	}
	
	@Test
	void testIncomplet_1() {
		String periodPref = "[3,7]";
		List<Hotel> res = Hotel.dataTransform(Hotel.getListeIncomplet(), 2, 1, null, periodPref);
		Hotel.log.info(res.toString());
		//assertTrue(res.size() == 10);
	}
	
	@Test
	void testIncomplet_2() {
		String periodPref = "[2,4]";
		List<Hotel> res = Hotel.dataTransform(Hotel.getListeIncomplet(), 2, 1, null, periodPref);
		Hotel.log.info(res.toString());
		//assertTrue(res.size() == 10);
	}

}
