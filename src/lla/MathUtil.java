package lla;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
		if (n < k) {
			return 0;
		}
		
		ret = gamma(n+1);
		ret /= gamma(k+1);
		ret /= gamma(n-k+1);
		
		return ret;
	}

	
	public static class Combination {

		public Combination(int toDraw, int total) {
			if (toDraw > total) {
				return;
			}
			combinationLength = total;
			this.toDraw = toDraw;
			
			for (int i = 0; i < total-toDraw; i++) {
				combination.add(false);
			}
			for (int i = 0; i < toDraw; i++) {
				combination.add(true);
			}
			//System.out.println(combination);
		}
		
		public List<Integer> getCombination(int seqLength) {
			
			List<Integer> ret = new LinkedList<Integer>();
			ret.add(0);
			for (int i = 0; i < combinationLength; i++) {
				if (combination.get(i)) {
					ret.add(i+1);
				}
			}
			ret.add(seqLength);
			return ret;
		}
		
		public List<Integer> getCombination() {
			List<Integer> ret = new LinkedList<Integer>();
			for (int i = 0; i < combinationLength; i++) {
				if (combination.get(i)) {
					ret.add(i);
				}
			}
			return ret;
		}
		
		public void nextCombination() {
					
			int p = combinationLength - 1;
			
			if (p <= 0) {
				return;
			}

			while (!(combination.get(p) == true && combination.get(p-1) == false)) {
				p--;
				if (p <= 0) {
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

	public static class DirichletDistribution {
		
		public DirichletDistribution() {
			//this.alpha = alpha;
		}
		
		public void addCoordinate(Letter l, Double a) {
			alpha.put(l, a);
		}
		
		public void updateSum() {
			sum = 0;
			for (Double a : alpha.values()) {
				sum += a;
			}
		}
		
		public double getExpectedValue(Letter l) {
			updateSum();
			return alpha.get(l)/sum;
		}
		
		@Override
		public String toString(){
			return "D"+alpha;
		}
		
		private Map<Letter, Double> alpha = new HashMap<Letter, Double>();
		private int sum = 0;
	}
	
	public static class mixtureOfDistributions {
		
		public mixtureOfDistributions() {
			listOfDirichlets = new LinkedList<DirichletDistribution>();
			listOfCoeffs = new LinkedList<Double>();
		}
		
		public void addToMixture(double coeff, DirichletDistribution dd) {
			listOfDirichlets.add(dd);
			listOfCoeffs.add(coeff);
		}
		
		public double getExpectedValue(Letter l) {
			double ret = 0.0;
			for (int i = 0; i < listOfDirichlets.size(); i++) {
				ret += listOfCoeffs.get(i)*listOfDirichlets.get(i).getExpectedValue(l);
			}
			return ret;
		}
		
		@Override
		public String toString() {
			return listOfCoeffs + "\n" + listOfDirichlets;
		}
		
		private List<DirichletDistribution> listOfDirichlets;
		private List<Double> listOfCoeffs;
	}
	
}
