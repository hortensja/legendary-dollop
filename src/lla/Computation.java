package lla;

public class Computation {

	public static void main(String[] args) {

		Alphabet alphabet = new Alphabet();
		Sequence seq = new Sequence(alphabet);
		
		System.out.println(MathUtil.gamma(6));
		
	}

}
