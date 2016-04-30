package lla;

import java.util.HashMap;
import java.util.Map;

public class Model {

	public Model(Alphabet alphabet) {
		
		for (int i = 0; i < alphabet.getAlphabetSize(); i++ ) {
			mAlphaVector.put(alphabet.getLetter(i), 1.0);
		}
		
	}
	
	public void setModelIndex(int i) {
		mModelIndex = i;
	}
	
	public double getAlpha(Letter l) {
		return mAlphaVector.get(l);
	}
	
	
	private int mModelIndex;
	private Map<Letter, Double> mAlphaVector = new HashMap<Letter, Double>();
	
}
