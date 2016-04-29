package lla;

public class MathUtil {

	
	public static double gamma(double n) {
		
		if (n < 1) {
			return 0.0;
		} else {
			double ret = 1;
			
			for (int i = 1; i < n; i++) {
				ret *= (double)i;
			}
			
			return ret;
		}
	}
	
}
