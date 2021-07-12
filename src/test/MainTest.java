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

import main.Hotel;
import main.Mashup;
import main.Service;
import uncertain.MashupUncertain;
import uncertain.SkylineUncertain;

public class MainTest {
	
	public static Logger log = Logger.getLogger(MainTest.class);
	
	
	public static void main(String[] args) throws IOException {
		
		
		log.info(Hotel.inter("[2,7]", "[4,9]")); // true
		log.info(Hotel.inter("[2,7]", "[2,7]")); // true
		log.info(Hotel.inter("[2,7]", "[8,9]")); // false
		log.info(Hotel.inter("[4,9]", "[2,7]")); // true
	}
	
	
	
	
	
	

}
