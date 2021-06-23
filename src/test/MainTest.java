package test;

import java.io.IOException;
import org.apache.log4j.*;

import main.Mashup;

public class MainTest {
	
	private static Logger log = Logger.getLogger(MainTest.class);
	
	
	public static void main(String[] args) throws IOException {
		
		//Service.lireCSVServices(null);
		Mashup.lireCSVMashups();
		
	}
	
	
	
	
	
	

}
