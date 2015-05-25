package com.chordsequence.song;


public class SongReaderChordPro implements SongReader {

	private Song song = null;
	
	private Lyrics lyrics = new Lyrics();
	private Chords chords = new Chords();

	private Tabs tabs = new Tabs();	
	
	private String title = "";
	private String artist = "";
	
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

		if (s.trim().startsWith("{") && s.trim().endsWith("}")) {
			if (s.contains("{t:") || s.contains("{title:")) {
				title = parseTitle(s);
			}
			if (s.contains("{id:")) {
				// nothing to do
			}
			if (s.contains("{st:") || s.contains("{subtitle:")) {
				artist = parseArtist(s);
			}
			if (s.contains("{define:")) {
				if (song != null) {
					ChordPosition cp = new ChordPosition(s);
					song.getChordPositions().put(cp.getChord(), cp);
				}
			}
		} else {
			String text = "";
			String nextChord = "";
			String lastChord = "";
			boolean isAChord = false;
			int chordIndex = 0;
			int lastChordIndex = 0;
			ChordList chordList = chords.add();

			for (int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == '[') {
					isAChord = true;
				} 

				if (isAChord) {
					if (s.charAt(i) == '[') {
						nextChord = "";
					} else if (s.charAt(i) == ']') {
						isAChord = false;
						
						lastChord = nextChord;

						if (chordIndex == lastChordIndex) {
							text += " ";
							chordIndex++;
						}
						chordList.addChord(nextChord, chordIndex);
						lastChordIndex = chordIndex;
						
						if (i == s.length()-1) {
							text += " ";
						}
					} else {
						nextChord += s.charAt(i);
					}

				} else {
					text += s.charAt(i);

					if (lastChord.length() > 0) {
						lastChord = lastChord.substring(1);
					}
					chordIndex++;
				}				
			}

			if (!(s.trim().startsWith("{") && s.trim().endsWith("}"))) {
				lyrics.add(text);
			}
			
			if (!s.trim().startsWith("{") && !parsedArtist && artist.isEmpty() && !title.isEmpty() && !s.isEmpty()) {
				artist = parseArtist(s);
				return;
			}

			if (!s.trim().startsWith("{") && title.isEmpty() && !s.isEmpty()) {
				title = parseTitle(s);
				return;
			}

			
		}
	}


	
	@Override
	public void copy(SongReader reader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSong(Song theSong) {
		song = theSong;
		
	}

	@Override
	public Tabs getTabs() {
		return tabs;
	}

}
