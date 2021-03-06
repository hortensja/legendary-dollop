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

	private Sequence(Sequence seq, int i, int j) {
		for (int k = i; k <= j; k++) {
			mSequenceList.add(seq.getLetter(k));
		}
		printSequence();
	}

	public void printSequence() {
		System.out.println("Sequence : " + mSequenceList);
	}

	public int getSeqLength() {
		return mSequenceList.size();
	}

	public void printResults(Model m, Alphabet alphabet) {
		System.out.println("~~RESULTS~~\n");
		for (int mu = 0; mu < getSeqLength(); mu++) {
			System.out.println("P(x|mu=" + mu + ") = " + calculateProbWithGivenMu(mu, m, alphabet));
		}

		System.out.println("\nP(x) = " + calculateProbOfSequence(m, alphabet) + '\n');

		for (int mu = 0; mu < getSeqLength(); mu++) {
			System.out.println("P(mu=" + mu + "|x) = " + calculateProbOfMuGivenX(mu, m, alphabet));
		}
		System.out.println();
		System.out.println("P(no change points|x) = " + calculateProbOfMuGivenX(0, m, alphabet));
		for (int Am = 1; Am < getSeqLength(); Am++) {
			System.out.println("P(Am=" + Am + " for some m|x) = " + calculateProbOfAmGivenX(Am, m, alphabet));
		}

		findPosteriorPDFs(m, alphabet);
		/*double cos = 0.0;
		int mu = 1;
		int j = 3;
		for (int l = 0; l < j; l++) {
			cos += ((new Sequence(this, 0,l)).calculateProbWithGivenMu(mu-1, m, alphabet))*((new Sequence(this, l, j)).calculateProbWithGivenMu(1, m, alphabet));
			System.out.println("1st : " + new Sequence(this, 0,l).calculateProbWithGivenMu(mu-1, m, alphabet));
			System.out.println("2nd : " + new Sequence(this, l, j).calculateProbWithGivenMu(1, m, alphabet));
		}
		System.out.println("uwaga! " + cos);*/
	}

	private void findPosteriorPDFs(Model m, Alphabet alphabet) {
		for (int i = 0; i < getSeqLength(); i++) {

			MathUtil.Combination combination = new MathUtil.Combination(2, getSeqLength()+1);
			MathUtil.mixtureOfDistributions mixd = new MathUtil.mixtureOfDistributions();
			
			for (int j = 0; j < MathUtil.NewtonSymbol(getSeqLength() + 1, 2); j++) {
				List<Integer> bounds = combination.getCombination();

				if (i >= bounds.get(0) && i < bounds.get(1)) {
					//System.out.println(bounds);
					MathUtil.DirichletDistribution dd = new MathUtil.DirichletDistribution();
					for (Letter l : alphabet.getLetterList()) {
						dd.addCoordinate(l, (double) countLettersInSubstring(bounds.get(0), bounds.get(1)-1, l) + 1);
					}
					//System.out.println(dd);
					mixd.addToMixture(findProbOfBounds(bounds.get(0), bounds.get(1), m), dd);
				}
				combination.nextCombination();
			}
			System.out.println(mixd);
			m.addPosteriorPDF(mixd);
		}			
	}

	private double findProbOfBounds(int i, int j, Model m) {
		double ret = 0.0;
		//System.out.println("finding for ["+i+", "+j+"]");
		for (int mu = 0; mu < getSeqLength(); mu++) {
			MathUtil.Combination combination = new MathUtil.Combination(mu, getSeqLength()-1);

			for (int a = 0; a < MathUtil.NewtonSymbol(getSeqLength()-1, mu); a++) {
				//suma po ||A|| = m
				List<Integer> changePointVector = combination.getCombination(getSeqLength());
				combination.nextCombination();

				for (int p = 0; p < changePointVector.size()-1; p++) {
					if ((i >= changePointVector.get(p) && j <= changePointVector.get(p+1)) && 
							(changePointVector.contains(i) && changePointVector.contains(j))) {
						//System.out.println(changePointVector + " -> " + (m.getProbabilityOfAGivenMu(mu)*m.getProbabilityOfMu(mu)));
						ret += m.getProbabilityOfAGivenMu(mu)*m.getProbabilityOfMu(mu);
					}
				}

			}
		}
		return ret;
	}

	private double calculateProbOfAmGivenX(int Am, Model m, Alphabet alphabet) {

		double ret = 0.0;

		for (int mu = 1; mu < getSeqLength(); mu++) {
			MathUtil.Combination combination = new MathUtil.Combination(mu, getSeqLength()-1);

			for (int a = 0; a < MathUtil.NewtonSymbol(getSeqLength()-1, mu); a++) {

				List<Integer> changePointVector = combination.getCombination(getSeqLength());

				//System.out.println("Current combination : " + changePointVector);	
				combination.nextCombination();

				if (changePointVector.contains(Am)) {
					//System.out.println(changePointVector);
					ret += calculateProbForChangePointsGivenMu(mu, changePointVector, m, alphabet)*m.getProbabilityOfMu(mu);//*m.getProbabilityOfAGivenMu(mu);
				}
			}
		}
		return ret/calculateProbOfSequence(m, alphabet);
	}

	private double calculateProbOfMuGivenX(int mu, Model m, Alphabet alphabet) {
		return ((calculateProbWithGivenMu(mu, m, alphabet)*m.getProbabilityOfMu(mu))/calculateProbOfSequence(m, alphabet));		
	}

	private double calculateProbOfSequence(Model m, Alphabet alphabet) {
		double ret = 0.0;
		for (int mu = 0; mu < getSeqLength(); mu++) {
			ret += calculateProbWithGivenMu(mu, m, alphabet)*m.getProbabilityOfMu(mu);
		}
		return ret;
	}

	private double calculateProbWithGivenMu(int mu, Model m, Alphabet alphabet) {
		double ret = 0.0;
		MathUtil.Combination combination = new MathUtil.Combination(mu, getSeqLength()-1);

		for (int a = 0; a < MathUtil.NewtonSymbol(getSeqLength()-1, mu); a++) {
			//suma po ||A|| = m
			List<Integer> changePointVector = combination.getCombination(getSeqLength());
			combination.nextCombination();

			ret += calculateProbForChangePointsGivenMu(mu, changePointVector, m, alphabet);
		}
		return ret;
	}

	private double calculateProbForChangePointsGivenMu(int mu, List<Integer> changePointVector, Model m, Alphabet alphabet) {
		double temp = m.getProbabilityOfAGivenMu(mu);

		for (int j = 0; j < mu+1; j++) {
			temp *= calculateProductOfGammas(changePointVector.get(j), changePointVector.get(j+1)-1, m, alphabet);
			//System.out.println(temp);
		}
		//System.out.println("A="+changePointVector+" wynik "+temp);
		return temp;
	}

	private double calculateProductOfGammas(int i, int j, Model m, Alphabet alphabet) {
		double ret = 1.0;

		ret = MathUtil.gamma(countTotalAlphaSum(m, alphabet));

		for (Letter l : alphabet.getLetterList()) {
			ret *= MathUtil.gamma(countLettersInSubstring(i, j, l) + m.getAlpha(l));
			ret /= MathUtil.gamma(m.getAlpha(l));
		}

		double temp = MathUtil.gamma(j - i + 1 + countTotalAlphaSum(m, alphabet));

		ret /= temp;

		//System.out.println("product for "+i+"->"+j+" : "+ret);
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
		//System.out.println("i " + i + " j "+j);
		for (int k = i; k <= j; k++) {
			//System.out.println("index " + k);
			if (mSequenceList.get(k) == l) {
				ret++;
			}
		}
		return ret;
	}

	private Letter getLetter(int k) {
		return mSequenceList.get(k);
	}

	private List<Letter> mSequenceList = new LinkedList<Letter>();
}
