package lla;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Sequence {

	public Sequence() {
		
		try {
			Scanner s = new Scanner(new File("seq.txt"));
			String temp = s.nextLine();
			
			for (int i = 0; i < temp.length(); i++) {
				mSequenceList.add(temp.charAt(i));
			}
			printSequence();			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public void printSequence() {
		System.out.println("Sequence : " + mSequenceList);
	}
	
	
	private List<Character> mSequenceList = new LinkedList<Character>();
}
