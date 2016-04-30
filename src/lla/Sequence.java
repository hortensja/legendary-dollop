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
	
	public double calculateProductOfGammas(int i, int j, Model m, Alphabet alphabet) {
		double ret = 1;
		
		ret = MathUtil.gamma(countTotalAlphaSum(m, alphabet));
		
		for (Letter l : alphabet.getLetterList()) {
			ret *= MathUtil.gamma(countLettersInSubstring(i, j, l) + m.getAlpha(l));
			ret /= MathUtil.gamma(m.getAlpha(l));
		}
		
		double temp = MathUtil.gamma(j - i + 1 + countTotalAlphaSum(m, alphabet));
		
		ret /= temp;
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
