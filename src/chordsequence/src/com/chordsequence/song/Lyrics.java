package com.chordsequence.song;

import java.util.ArrayList;

public class Lyrics {

	ArrayList<String> lyrics = new ArrayList<>();

	public void add(String line) {
		lyrics.add(line);
	}
	
	public String get(int index) {

		return lyrics.get(index);
	}

	public int size() {
		return lyrics.size();
	}


}
