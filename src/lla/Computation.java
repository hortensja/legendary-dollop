package lla;

public class Computation {

	public static void main(String[] args) {

		Alphabet alphabet = new Alphabet();
		Sequence seq = new Sequence(alphabet);
		Model model0 = new Model(alphabet, seq.getSeqLength());
		
		model0.printModel();
		
		//int mu = 1;
		
		for (int mu = 0; mu < seq.getSeqLength(); mu++) {
			seq.calculateProbWithGivenMu(mu, model0, alphabet);
		}
		
		
		//System.out.println(MathUtil.gamma(6));
		
	}

}
