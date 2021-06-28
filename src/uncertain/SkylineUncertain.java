package uncertain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import main.Mashup;
import main.Skyline;
import test.MainTest;

public class SkylineUncertain extends Skyline {
	
	private static Logger log = Logger.getLogger(MainTest.class);
	
	public static List<MashupUncertain> computeUncertainSkyline(List<MashupUncertain> M, 
			Map<String,String> QoSPref, float threshold) {
		List<MashupUncertain> S = new ArrayList<>();
		
		float belief=0;
		
		for(MashupUncertain mi : M) {
			for(MashupUncertain mj : M) {
				belief = 0;
				if(mi != mj) {
					belief = 1;
					for(Map.Entry<String,String> mapentry: QoSPref.entrySet()) {
						belief = belief * bel(mi.getQoSUncertain().get(mapentry.getKey()), 
								mj.getQoSUncertain().get(mapentry.getKey()), mapentry.getValue());
					}
					
					if(belief == 0) break;
				}
				else belief = 1;
				//log.debug(mi.getName() + " vs " + mj.getName() + "belief="+belief);
				if(belief < threshold) break;
			}
			if(belief >= threshold) {
				S.add(mi);
			}
		}
		
		return S;
	}

	public static float bel(Map<Float, Float> qos_i, Map<Float, Float> qos_j, String op) {
		float sum_all = 0;
		float s, x;
		for(Map.Entry<Float, Float> mapentry_i: qos_i.entrySet()) {
			s = 0;
			for(Map.Entry<Float, Float> mapentry_j: qos_j.entrySet()) {
				if(compare(mapentry_i.getKey(), mapentry_j.getKey(), op)) {
					s += mapentry_j.getValue();
				}
			}
			x = s*mapentry_i.getValue();
			sum_all += x;
		}
		return sum_all;
	}

}
