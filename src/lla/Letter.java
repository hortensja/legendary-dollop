package lla;

public class Letter {

	public Letter(char l) {
		mLetter = l;
	}
	
	
	@Override
	public String toString() {
		return mLetter + "(" + mPosInAlphabet + ")";//Character.toString(mLetter);
	}
	
	public void setPosInAlphabet(int pos) {
		mPosInAlphabet = pos;
	}
	
	private char mLetter;
	private int mPosInAlphabet;
}
