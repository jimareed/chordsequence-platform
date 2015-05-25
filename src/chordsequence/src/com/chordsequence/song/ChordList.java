package com.chordsequence.song;

import java.util.ArrayList;

public class ChordList {

	ArrayList<Chord> chords = new ArrayList<>();

	public ArrayList<Chord> getChords() {
		return chords;
	}
	
	public void add(String s) {
		if (!s.isEmpty()) {
			toChords(s);
		}
	}
	
	public String toString() {
		String s = "";
		
		if (chords.size() > 0) {
			for (int i=0; i < chords.size(); i++) {
				s += chords.get(i).toString();
				if (i < chords.size()-1) {
					s += ",";
				}
			}
		}
		
		return s;
	}
	
	public Chord get(int index) {

		return chords.get(index);
	}

	public Chord getAtIndex(int index) {

		for (int i = 0; i < chords.size(); i++) {
			if (chords.get(i).getIndex() == index) {
				return chords.get(i);
			}
		}
		return null;
	}
	
	public int size() {
		return chords.size();
	}

	public void addChord(String ch, int index) {
		
		Chord chord = new Chord(ch);
		chord.setIndex(index);
		
		ArrayList<Chord> newChords = new ArrayList<>();
		
		for (int i = 0; i < chords.size(); i++) {
			if (index < chords.get(i).getIndex()) {
				newChords.add(chord);
				index = 10000;
			}
			newChords.add(chords.get(i));
		}

		if (chords.size() == newChords.size()) {
			newChords.add(chord);
		}
		
		chords = newChords;
	}

	public void deleteChord(int index) {

		for (int i = 0; i < chords.size(); i++) {
			if (chords.get(i).getIndex() == index) {
				chords.remove(i);
			}
		}
	}

	String[] splitChords(String ch) {
		String s[] = null;

		int index = splitAt(ch);

		if (index <= 0) {
			s = new String[1];
			s[0] = ch;
		} else {
			s = new String[2];
			s[0] = ch.substring(0, index);
			s[1] = ch.substring(index,ch.length());
		}
		return s;
	}
	
	private int splitAt(String ch) {

		for (int i = 1; i < ch.length(); i++) {
			if (ch.charAt(i) == 'A' || ch.charAt(i) == 'B' || 
				ch.charAt(i) == 'C' || ch.charAt(i) == 'D' || 
				ch.charAt(i) == 'E' || ch.charAt(i) == 'F' || 
				ch.charAt(i) == 'G' || ch.charAt(i) == '-') {
				return i;	
			}
			if (ch.charAt(i) == '/') {
				return 0;
			}
		}
		return 0;
	}
	
	private void addToChords(String ch, int index) {
		if (splitAt(ch) > 0) {
			String s[] = splitChords(ch);
			
			for (int i = 0; i < s.length; i++) {
				addToChords(s[i], index+s[i].length());
			}
			
		} else {
			if (ch.endsWith(",") || ch.endsWith("-")) {
				ch = ch.substring(0, ch.length()-1);
			}
			if (ch.startsWith("-")) {
				ch = ch.substring(1, ch.length());
			}

			if (Chord.startsWithAChord(ch)) {
				Chord chord = new Chord(ch);		
				chord.setIndex(index);

				chords.add(chord);
			}
		}
		
	}
	
	private void toChords(String s) {
		
		int index = 0;
		
		
		String strings[] = s.split(" ");

		for (int i = 0; i < strings.length; i++) {
			if (strings[i].length() > 0) {
				addToChords(strings[i], index);
				
				index += strings[i].length();
			}

			index++;
		}

	}
	
}
