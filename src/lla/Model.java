package lla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

	public Model(Alphabet alphabet, int n) {
		
		for (int i = 0; i < alphabet.getAlphabetSize(); i++) {
			mAlphaVector.put(alphabet.getLetter(i), 1.0);
			
		}
		mSequenceSize = n;
		for (int i = 0; i < n; i++) {
			mProbabilityOfAGivenMu.add(1/MathUtil.NewtonSymbol(n-1, i));
			mProbabilityOfMu.add((1.0/(double) n));
		}
	}
	
	public void setModelIndex(int i) {
		mModelIndex = i;
	}
	
	public int getSeqSize() {
		return mSequenceSize;
	}
	
	public double getAlpha(Letter l) {
		return mAlphaVector.get(l);
	}
	
	public double getProbabilityOfMu(int mu) {
		return mProbabilityOfMu.get(mu);
	}
	
	public double getProbabilityOfAGivenMu(int mu) {
		return mProbabilityOfAGivenMu.get(mu);
	}
	
	
	public void printModel() {
		System.out.println("\nModel nr " + mModelIndex);
		System.out.println("Alpha : " + mAlphaVector);
		System.out.println("Probability of mu : " + mProbabilityOfMu);
		System.out.println("Probability of A given mu : " + mProbabilityOfAGivenMu + '\n');
	}
	
	
	
	private int mModelIndex;
	private int mSequenceSize;
	private Map<Letter, Double> mAlphaVector = new HashMap<Letter, Double>();
	private List<Double> mProbabilityOfAGivenMu = new ArrayList<Double>();
	private List<Double> mProbabilityOfMu = new ArrayList<Double>();
	
}
