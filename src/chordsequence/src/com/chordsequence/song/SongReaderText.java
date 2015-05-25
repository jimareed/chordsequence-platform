package com.chordsequence.song;


public class SongReaderText implements SongReader {

	private Lyrics lyrics = new Lyrics();
	private Chords chords = new Chords();

	private Tabs tabs = new Tabs();	
		
	private String title = "";
	private String artist = "";

	private String nextChords = "";
	
	private boolean parsedArtist = false;
	
	@Override
	public Chords getChords() {
		return chords;
	}

	@Override
	public Lyrics getLyrics() {
		return lyrics;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getArtist() {
		return artist;
	}
	
	private String parseArtist(String s) {
		String a = s.trim();

		if (s.contains("{st:")) {
			a = s.substring(s.indexOf("{st:")+4, s.indexOf("}"));
		}
		if (s.contains("{subtitle:")) {
			a = s.substring(s.indexOf("{subtitle:")+10, s.indexOf("}"));
		}

		parsedArtist = true;
		return a;
	}

	private String parseTitle(String s) {
		String t = s.trim();

		if (s.contains("{t:")) {
			t = s.substring(s.indexOf("{t:")+3, s.indexOf("}"));
		}
		if (s.contains("{title:")) {
			t = s.substring(s.indexOf("{title:")+7, s.indexOf("}"));
		}
		
		return t;
	}
	

	
	@Override
	public void addLine(String s) {

		if (Tab.isATab(s)) {
			Tab tab = tabs.getCurrentTab();
			if (tab == null || tab.numStrings() == 6) {
				tabs.add(s);
			} else {
				tab.add(s);
			}
			return;		// done processing line
		} else {
			tabs.clearCurrentTab();
		}
		
		if (artist.isEmpty() && !parsedArtist && !title.isEmpty() && !s.isEmpty()) {
			artist = parseArtist(s);
			return;
		}

		if (title.isEmpty() && !s.isEmpty()) {
			title = parseTitle(s);
			return;
		}

		if (hasLyrics(s)) {
			lyrics.add(s);
			chords.add(nextChords);
			nextChords = "";
		} else {
			nextChords = s;
		}

	}

	//chords: [A-G]{#|b}{m|dim|maj|sus}{digit}{/[A-G]{#|b}}

	private boolean hasLyrics(String s) {

		boolean hasText = false;
		
		int numChords = 0;
		
		String chords[] = s.split(" ");

		for (int i = 0; i < chords.length; i++) {
			if (chords[i].length() > 0 && chords[i].length() < 5) {
				char c = chords[i].charAt(0);
				
				if (c >= 'A' && c <= 'G') {
					numChords++;
				} else {
					hasText = true;
				}
				
			}
			
			if (hasText) {
				return true;
			}
		}

		return numChords == 0;
	}

	@Override
	public void copy(SongReader reader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSong(Song song) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Tabs getTabs() {
		return tabs;
	}

}
