package lla;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Sequence {

	public Sequence(Alphabet alphabet) {
		
		try {
			Scanner s = new Scanner(new File("seq.txt"));
			String temp = s.nextLine();
			
			for (int i = 0; i < temp.length(); i++) {
				mSequenceList.add(alphabet.getLetter(temp.charAt(i)));
			}
			printSequence();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public void printSequence() {
		System.out.println("Sequence : " + mSequenceList);
	}
	
	public int getSeqLength() {
		return mSequenceList.size();
	}
	
	public double calculateProbWithGivenMu(int mu, Model m, Alphabet alphabet) {
		
		double ret = 0.0;

		System.out.print(mu + " : " + m.getSeqSize() + " ");
		System.out.println(MathUtil.NewtonSymbol(m.getSeqSize()-1, mu));
		
		MathUtil.Combination combination = new MathUtil.Combination(mu, m.getSeqSize()-1);
				
		for (int a = 0; a < MathUtil.NewtonSymbol(m.getSeqSize()-1, mu); a++) {
			//suma po ||A|| = m
			
			
			List<Integer> changePointVector = new LinkedList<Integer>();
			changePointVector.add(0);
			changePointVector.addAll(combination.getCombination());
			changePointVector.add(m.getSeqSize());
			System.out.println("Current combination : " + changePointVector);
			
			combination.nextCombination();
			
			double temp = m.getProbabilityOfAGivenMu(mu);
			
			for (int j = 0; j < mu+1; j++) {
				temp *= calculateProductOfGammas(changePointVector.get(j), changePointVector.get(j+1)-1, m, alphabet);
				System.out.println(temp);
			}
			ret += temp;
		}
		System.out.println("Probability with mu = "+mu+" = "+ret);
		return ret;
	}
	
	private double calculateProductOfGammas(int i, int j, Model m, Alphabet alphabet) {
		double ret = 1;
		
		ret = MathUtil.gamma(countTotalAlphaSum(m, alphabet));
		
		for (Letter l : alphabet.getLetterList()) {
			ret *= MathUtil.gamma(countLettersInSubstring(i, j, l) + m.getAlpha(l));
			ret /= MathUtil.gamma(m.getAlpha(l));
		}
		
		double temp = MathUtil.gamma(j - i + 1 + countTotalAlphaSum(m, alphabet));
		
		ret /= temp;
		
		System.out.println("product for "+i+"->"+j+" : "+ret);
		return ret;
	}
	
	private int countTotalAlphaSum(Model m, Alphabet alphabet) {
		int ret = 0;
		
		for (Letter l : alphabet.getLetterList()) {
			ret += m.getAlpha(l);
		}
		return ret;
	}
		
	private int countLettersInSubstring(int i, int j, Letter l) {
		int ret = 0;
		for (int k = i; k <= j; k++) {
			if (mSequenceList.get(k) == l) {
				ret++;
			}
		}
		return ret;
	}
	
	
	private List<Letter> mSequenceList = new LinkedList<Letter>();
}
