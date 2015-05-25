package com.chordsequence.song;


public class Chord {
	
	int line = 0;
	int index = 0;
	String chord = "";
	
	public Chord(String s) {
		chord = s;
	}
	
	public String toString() {
		return chord;
	}

	public void set(String s) {
		chord = s;
	}

	static public boolean isAChord(String s) {

		if (s.matches("^[A-G](b|#)?(m|maj|dim|sus)?([2-7]|9|13)?(\\/[A-G](b|#)?)?")) {
			return true;
		}
		return false;
	}

	static public boolean startsWithAChord(String s) {

		if (s.isEmpty()) {
			return false;
		}
		if (isAChord(s)) {
			return true;
		}
		if (!(s.endsWith("*") || s.endsWith("+"))) {
			return false;
		}
		return startsWithAChord(s.substring(0, s.length()-1));
	}
	
	public int getLine() {
		return line;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int theIndex) {
		index = theIndex;
	}

	public void setLine(int theLine) {
		line = theLine;
	}

	
}
