package com.chordsequence.song;

import java.util.ArrayList;

public class Chords {

	ArrayList<ChordList> chordLists = new ArrayList<>();

	public ChordList getChordList(int index) {
		return chordLists.get(index);
	}

	public void add(String line) {
		
		ChordList chordList = new ChordList();
		
		chordList.add(line);
	
		chordLists.add(chordList);
	}

	public ChordList add() {
		
		ChordList chordList = new ChordList();
		
		chordLists.add(chordList);
		
		return chordList;
	}
	
	public String get(int index) {

		return chordLists.get(index).toString();
	}
	
	public ChordList getChords(int index) {
		
		return chordLists.get(index);
	}

	public int size() {
		return chordLists.size();
	}

	
}
