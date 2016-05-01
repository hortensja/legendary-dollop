package lla;

import java.util.LinkedList;
import java.util.List;

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
	
	public static double NewtonSymbol(double n, double k) {
		
		double ret;
		
		ret = gamma(n+1);
		ret /= gamma(k+1);
		ret /= gamma(n-k+1);
		
		return ret;
	}

	
	public static class Combination {

		public Combination(int toDraw, int total) {
			combinationLength = total;
			this.toDraw = toDraw;
			
			for (int i = 0; i < total-toDraw; i++) {
				combination.add(false);
			}
			for (int i = 0; i < toDraw; i++) {
				combination.add(true);
			}
			System.out.println(combination);
		}
		
		public List<Integer> getCombination() {
			
			List<Integer> ret = new LinkedList<Integer>();
			
			for (int i = 0; i < combinationLength; i++) {
				if (combination.get(i)) {
					ret.add(i+1);
				}
			}
			return ret;
		}
		
		
		public void nextCombination() {
					
			int p = combinationLength - 1;

			while (!(combination.get(p) == true && combination.get(p-1) == false)) {
				p--;
				if (p==0) {
					return;
				}
			}
			int s = 0;
			for (int i = p+1; i < combinationLength; i++) {
				if (combination.get(i)) {
					s++;
				}
			}
			
			combination.set(p-1, true);
			for (int i = p; i < combinationLength-s; i++) {
				combination.set(i, false);
			}
			for (int i = combinationLength-s; i < combinationLength; i++) {
				combination.set(i, true);
			}
		}



		private List<Boolean> combination = new LinkedList<Boolean>();
		private int combinationLength;
		private int toDraw;
	}

}
