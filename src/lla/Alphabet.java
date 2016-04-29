package lla;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Alphabet {

	
	public Alphabet() {
		try {
			Scanner s = new Scanner(new File("alphabet.txt"));
			String temp = s.nextLine();
			
			for (int i = 0; i < temp.length(); i++) {
				Letter l = new Letter(temp.charAt(i));
				l.setPosInAlphabet(i);
				mAlphabetList.add(l);
			}
			printAlphabet();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}				
	}
	
	public void printAlphabet() {
		System.out.println("Alphabet : " + mAlphabetList);
	}
	
	
	private int mAlphabetSize;
	private List<Letter> mAlphabetList = new ArrayList<Letter>();
}
