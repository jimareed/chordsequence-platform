package com.chordsequence.song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.chordsequence.util.Log;
import com.chordsequence.util.TextFile;
import com.chordsequence.util.TextFileBuffer;

public class Song implements TextFileBuffer {

	private String id = "";
	private Library lib = null;
	private SongReaderFilter filter = new SongReaderFilter();
	
	private SongReader songReader = null;
	private SongReader altSongReader = null;
	private HashMap<String, ChordPosition> chordPositions = new HashMap<String, ChordPosition>(); 
	
	private static int LINESPERPAGE = 20;
	
	public Song(Library theLib) {
		lib = theLib;
	}

	
	private void clear() {
		songReader = null;
		chordPositions = new HashMap<String, ChordPosition>();
	}
	

	public Tabs getTabs() {
		if (songReader == null) {
			return null;
		}
		return songReader.getTabs();
	}
	
	public HashMap<String, ChordPosition> getChordPositions() {
		return chordPositions;
	}
	
	public Chords getChords() {
		return songReader.getChords();
	}
	
	public void refreshChordPositions() {
		if (lib != null) {
			ChordPositionLibrary chordLib = lib.getChordLib();

			ArrayList<String> chords = getUniqueChords();

			for (int i = 0; i < chords.size(); i++) {
				if (chordPositions.get(chords.get(i)) == null) {
					ChordPosition cp = new ChordPosition(chords.get(i));
					if (chordLib != null) {
						ChordPosition cpLib = chordLib.getPosition(chords.get(i));
						if (cpLib != null) {
							cp.copy(cpLib);
						}
						chordPositions.put(chords.get(i), cp);
					}
				}
			}
		}
	}

	public ChordPosition getChordPosition(String ch) {
		if (chordPositions.size() == 0) {
			refreshChordPositions();
		}
		return chordPositions.get(ch);
	}

	public ArrayList<String> getChordSequence() {
		
		ArrayList<String> sequence = getUniqueChords();
		
		
		Iterator<String> it = getChordPositions().keySet().iterator();

		while(it.hasNext()) {
			String ch = it.next();
			boolean found = false;
			
			for (int i = 0; i < sequence.size() && !found; i++) {
				if (ch.equals(sequence.get(i))) {
					found = true;
				}
			}
			
			if (!found) {
				sequence.add(ch);
			}
		}
		
		return sequence;
	}
	
	private ArrayList<String> getUniqueChords(SongReader reader) {
		ArrayList<String> list = new ArrayList<String>();
		
		for (int i = 0; i < reader.getChords().size(); i++) {
			ChordList chordList = reader.getChords().getChords(i);

			for (int j = 0; j < chordList.size(); j++) {
				Chord chord = reader.getChords().getChords(i).get(j);
				
				boolean hasChord = false;
				for (int k = 0; k < list.size(); k++) {
					if (list.get(k).equals(chord.toString())) {
						hasChord = true;
					}
				}
				
				if (!hasChord) {
					list.add(chord.toString());
				}
			}
		}
		
		
		
		return list;

	}
	public ArrayList<String> getUniqueChords() {
		
		return getUniqueChords(songReader);
	}

	public void renameChord(String fromChord, String toChord) {

		for (int i = 0; i < songReader.getChords().size(); i++) {
			ChordList chordList = songReader.getChords().getChords(i);

			for (int j = 0; j < chordList.size(); j++) {
				Chord chord = songReader.getChords().getChords(i).get(j);

				if (chord.toString().equals(fromChord)) {
					chord.set(toChord);
				}
			}
		}
		
		chordPositions.remove(fromChord);
		
		if (chordPositions.get(toChord) == null) {
			addChord(toChord);
		}
		

	}	
	
	public void deleteChord(String chord) {
		
	}	
	
	public void addChord(String chord) {
		ChordPosition position = new ChordPosition(chord);
		
		if (lib.getChordLib().getPosition(chord) != null) {
			position.copy(lib.getChordLib().getPosition(chord));
		}
		
		chordPositions.put(chord, position);
	}
	
	public String getArtist() {
		return songReader.getArtist();
	}
	
	public String getTitle() {
		return songReader.getTitle();
	}
	
	public void setId(String theId) {
		id = theId;
	}

	public String getId() {
		return id;
	}
	
	public int getSvgLine(int y) {
		return (y-80) / 40;
	}

	public int getSvgColumn(int x) {
		return (x - 20) / 11;
	}

	public boolean replaceChordRef(String ch, int line, int column) {

		if (line >= songReader.getChords().size()) {
			Log.println(getClass().getName() + ": error adding chord, invalid line number");
			return false;
		}
		
		ChordList chordList = songReader.getChords().getChordList(line);

		Chord chord = chordList.getAtIndex(column);

		if (chord == null || !chord.toString().equals(ch)) {
			return addChordRef(ch, line, column);
		}
		
		return deleteChordRef(line, column);
	}
	
	public boolean addChordRef(String ch, int line, int column) {
		
		if (line >= songReader.getChords().size()) {
			Log.println(getClass().getName() + ": error adding chord, invalid line number");
			return false;
		}
		
		ChordList chordList = songReader.getChords().getChordList(line);

		Chord chord = chordList.getAtIndex(column);

		if (chord != null) {
			chordList.deleteChord(column);
		}
		chordList.addChord(ch, column);
		
		return true;
	}

	public boolean deleteChordRef(int line, int column) {
		
		if (line >= songReader.getChords().size()) {
			Log.println(getClass().getName() + ": error adding chord, invalid line number");
			return false;
		}
		
		ChordList chordList = songReader.getChords().getChordList(line);

		if (chordList != null) {
			chordList.deleteChord(column);
		}
		return true;
	}

	public void fromString(String text) {
		clear();
		
		TextFile file = new TextFile();
		
		file.setBuffer(this);
		file.fromString(text);
		
		refreshChordPositions();
	}

	public boolean moveChord(int line, int column, String direction) {
	
		int columnOffset = 0;
		int rowOffset = 0;

		if (direction.equals("right")) {
			columnOffset = 1;
		}
		if (direction.equals("left")) {
			columnOffset = -1;
		}
		if (direction.equals("up")) {
			rowOffset = -1;
		}
		if (direction.equals("down")) {
			rowOffset = 1;
		}

		ChordList chordList = songReader.getChords().getChordList(line);
		
		if (chordList == null) {
			Log.println(getClass().getName() + ": invalid line " + line);
			return false;
		}
		Chord chord = chordList.getAtIndex(column);
		if (chord == null) {
			Log.println(getClass().getName() + ": invalid column " + column);
			return false;
		}
		
		if (columnOffset != 0) {
			chord.setIndex(chord.getIndex()+columnOffset);
		}

		if (rowOffset != 0) {
			chordList.deleteChord(column);
			
			ChordList newChordList = songReader.getChords().getChordList(line + rowOffset);
			
			if (newChordList == null) {
				Log.println(getClass().getName() + ": invalid line " + line + rowOffset);
				return false;
			}

			newChordList.addChord(chord.toString(), column);
		}		
		return true;
	}
		
	public String toSvgZoomed(int theLine, int theColumn, String songId, String pageNum) {
		
		int line = theLine;
		int numLines = 4;

		line -= 1;
		
		if (line < 0) {
			line = 0;
		}
		if (line > songReader.getLyrics().size() - numLines) {
			line = songReader.getLyrics().size() - numLines;
		}

		int maxWidth = maxLineWidth(line, numLines, 24);
		
		
		String s = "";

		s += "<table width=\"100%\">";
		s += "		<tr valign=\"top\" >";
		s += "		<td width=\"100%\">";
		
		s += "<svg width=\"" + maxWidth + "\" height=\"260\">\n";
		s += "<text x = \"10\" y = \"45\" font-size = \"40\" font-family=\"Courier\">\n";

		for (int i = line; i < line+numLines-1; i++) {
			s += "<tspan onclick=\"click(event)\" x=\"0\" y=\"" + Integer.toString((i+1-line)*80) + "\">" + songReader.getLyrics().get(i) + "</tspan>\n";
		}
		
		s += "</text>\n";

		s += "<text x = \"10\" y = \"45\" font-size = \"40\" font-family=\"Courier\" fill=\"blue\" font-weight=\"bold\">\n";

		
		for (int i = line; i < line+numLines-1; i++) {
			ChordList chordList = songReader.getChords().getChords(i);

			for (int j = 0; j < chordList.size(); j++) {
				Chord chord = songReader.getChords().getChords(i).get(j);
				
				if (chord != null) {
					s += "<tspan onclick=\"click(event)\" x=\"" + Integer.toString((chord.getIndex())*24) + "\" y=\"" + Integer.toString((i+1-line)*80-40) + "\">" + chord.toString() + "</tspan>\n";					
				}
			}
			
		}
		
		s += "</text>\n";

		int rectWidth = 60;
		Chord rectChord = getChord(theLine, theColumn);

		if (rectChord != null) {
			rectWidth += (rectChord.toString().length()-1)*24;
		}
		
		int rectY = (theLine-line)*80-5;
		
		s += " <rect x=\"" + Integer.toString(theColumn*24-18) + "\" y=\""+ rectY +"\" width=\"" + rectWidth + "\" height=\"60\" style=\"fill:blue;stroke:blue;stroke-width:1;fill-opacity:0.1;stroke-opacity:0.6\" \"/>";
		
		s += "</svg>\n";

		s += "</td>";
		s += "</tr>";
		s += "<tr>";
		s += "<td>";
		s += "<tr>";
		s += "<td>";
		s += "<table align=\"center\">";
		s += "<tr>";
		s += "<td>";
		s += "<img src=\"/images/blank.png\"/>";
		s += "</td><td>";
		s += "<a href=\"/chordsequence?action=movechord&view=zoominsong&direction=up&column=" + theColumn + "&line=" + theLine + "&songid=" + songId + "&pagenum=" + pageNum + "\" >";
		s += "<img src=\"/images/uparrow.png\"/>";
		s += "</a></td>";
		s += "</tr><tr>";
		s += "<td>";
		s += "<a href=\"/chordsequence?action=movechord&view=zoominsong&direction=left&column=" + theColumn + "&line=" + theLine + "&songid=" + songId + "&pagenum=" + pageNum + "\">";
		s += "<img src=\"/images/leftarrow.png\"/>";
		s += "</a></td>";
		s += "<td>";
		s += "<a href=\"/chordsequence?action=deletechordref&view=editsong&column=" + theColumn + "&line=" + theLine + "&songid=" + songId + "&pagenum=" + pageNum + "\">";
		s += "<img src=\"/images/delete.png\" height=\"70\" width=\"70\"/>";
		s += "</a></td>";
		s += "<td>";
		s += "<a href=\"/chordsequence?action=movechord&view=zoominsong&direction=right&column=" + theColumn + "&line=" + theLine + "&songid=" + songId + "&pagenum=" + pageNum + "\">";
		s += "<img src=\"/images/rightarrow.png\"/>";
		s += "</a></td>";
		s += "</tr><tr>";
		s += "<td>";
		s += "<img src=\"/images/blank.png\"/>";
		s += "</td>";
		s += "<td>";
		s += "<a href=\"/chordsequence?action=movechord&view=zoominsong&direction=down&column=" + theColumn + "&line=" + theLine + "&songid=" + songId + "&pagenum=" + pageNum + "\">";
		s += "<img src=\"/images/downarrow.png\"/>";
		s += "</a></td>";
		s += "</tr>";
		s += "</table>";
		s += "</td>";
		s += "</tr>";
		s += "</table>";
		
		return s;
	}
	
	private int lastPosition = 0;
	private int lastLength = 0;
	private int lastLine = -1;
	
	private void resetXPosition() {
		lastPosition = 0;
		lastLength = 0;
		lastLine = -1;
	}
	
	private String getXPosition(int index, int length, int line, int charWidth) {

		int position = index*charWidth;

		if (lastLine == line) {
			if (position < lastPosition + (lastLength +1)*charWidth) {
				position = lastPosition + (lastLength +1)*charWidth;
			}
		}

		lastPosition = position;
		lastLength = length;
		lastLine = line;
			
		return Integer.toString(position);
	}

	public int numPages() {
		return numPages(LINESPERPAGE);
	}
	
	public int numLines() {
		return songReader.getLyrics().size();
	}
	
	public int numPages(int linesPerPage) {
		int numPages = songReader.getLyrics().size() / linesPerPage;
		boolean partialPage = songReader.getLyrics().size() % linesPerPage > 0;
		
		if (partialPage) {
			numPages++;
		}
		
		return numPages;
	}

	public String toSvg(int pageNum, int pageWidth, int numLines, boolean draganddrop) {
		
		if (pageNum < 0 || pageNum > numPages()) {
			Log.println(getClass().getName() + ": error, invalid page number " + pageNum);
			return null;
		}
	
		int firstLine = pageNum*numLines;
		
		if (numLines+firstLine > songReader.getLyrics().size()) {
			numLines = songReader.getLyrics().size()-firstLine;
		}
		
		return toSvgPage(firstLine, numLines, pageWidth, draganddrop);
	}
	
	public String toSvg(int pageNum, int pageWidth) {

		return toSvg(pageNum, pageWidth, LINESPERPAGE, true);
	}
	
	
	private int maxLineWidth(int firstLine, int numLines, int charWidth) {

		int maxLineLength = 0;
		
		for (int i = firstLine; i < numLines+firstLine; i++) {
			int length = songReader.getLyrics().get(i).length();
			
			if (length > maxLineLength) {
				maxLineLength = length;
			}
		}

		int maxWidth = maxLineLength*charWidth;

		for (int i = firstLine; i < numLines+firstLine; i++) {
			ChordList chordList = songReader.getChords().getChords(i);

			for (int j = 0; j < chordList.size(); j++) {
				Chord chord = songReader.getChords().getChords(i).get(j);
				
				if (chord != null) {
					int xPosition = Integer.parseInt(getXPosition(chord.getIndex(), chord.toString().length(), i, charWidth)) + chord.toString().length()*charWidth;
					
					if (maxWidth < xPosition) {
						maxWidth = xPosition;
					}
				}
			}
			
		}
	
		return maxWidth;
	}

	
	private String toSvgPage(int firstLine, int numLines, int pageWidth, boolean draganddrop) {

		int maxWidth = maxLineWidth(firstLine, numLines, 12);
		
		int marginWidth = 0;
		
		if (maxWidth < pageWidth) {
			marginWidth = (pageWidth-maxWidth)/2;
		}
		
		String s = "";

		s += "<table>";
		s += "<tr align=\"center\">";
		s += "<td width=\"" + marginWidth + "\">";
		s += "</td>";
		s += "<td>";

		s += "<svg width=\"" + maxWidth + "\" height=\"" + Integer.toString(numLines * 40 + 10) + "\">\n";
		s += "<text x = \"10\" y = \"45\" font-size = \"20\" font-family=\"Courier\">\n";

		for (int i = firstLine; i < numLines+firstLine; i++) {
			s += "<tspan x=\"0\" y=\"" + Integer.toString((i-firstLine+1)*40) + "\">" + songReader.getLyrics().get(i) + "</tspan>\n";
		}
		
		s += "</text>\n";

		s += "<text x = \"10\" y = \"45\" font-size = \"20\" font-family=\"Courier\" fill=\"blue\" font-weight=\"bold\">\n";

		resetXPosition();
				
		for (int i = firstLine; i < numLines+firstLine; i++) {
			ChordList chordList = songReader.getChords().getChords(i);

			for (int j = 0; j < chordList.size(); j++) {
				Chord chord = songReader.getChords().getChords(i).get(j);
				
				if (chord != null) {
					s += "<tspan onclick=\"click(event," + i + "," + chord.getIndex() + ")\" x=\"" + getXPosition(chord.getIndex(), chord.toString().length(), i, 12) + "\" y=\"" + Integer.toString((i-firstLine+1)*40-20) + "\">" + chord.toString() + "</tspan>\n";
				}
			}
			
		}
		
		s += "</text>\n";

		for (int i = firstLine; i < numLines+firstLine; i++) {
			ChordList chordList = songReader.getChords().getChords(i);
			int lineLength = songReader.getLyrics().get(i).length();

			for (int j = 0; j < lineLength+5; j++) {
				Chord chord = chordList.getAtIndex(j);
				
				if (draganddrop) {
					if (chord == null) {
						s += " <rect x=\"" + Integer.toString(j*12) + "\" y=\"" + Integer.toString((i-firstLine+1)*40-40) + "\" width=\"12\" height=\"40\" style=\"fill:blue;stroke:pink;stroke-width:0;fill-opacity:0.0;stroke-opacity:0.0\" ondrop=\"drop(event," + i + "," + j + ")\" ondragover=\"allowDrop(event)\"/>";
					}					
				} else {
					s += " <rect x=\"" + Integer.toString(j*12) + "\" y=\"" + Integer.toString((i-firstLine+1)*40-40) + "\" width=\"12\" height=\"40\" style=\"fill:blue;stroke:pink;stroke-width:0;fill-opacity:0.0;stroke-opacity:0.0\" onclick=\"click(event," + i + "," + j + ")\"/>";
				}
			}
			
		}

		
		s += "</svg>\n";
		
		s += "</td>";
		s += "</tr>";
		s += "</table>";

		return s;
	}
	
	public void updateChordPositions() {
		
	}
	
	@Override
	public String toString() {
		String s = "";

		s += "{t:" + songReader.getTitle() + "}\n";
		s += "{st:" + songReader.getArtist() + "}\n";

		ChordPositionLibrary chordLib = null;
		if (lib == null) {
			Log.println(getClass().getName() + ": error can't find song library");			
		} else {
			chordLib = lib.getChordLib();
			if (chordLib == null) {
				Log.println(getClass().getName() + ": error can't find chord library");
			}
		}
		
		Iterator<String> it = chordPositions.keySet().iterator();

		while(it.hasNext()) {
			String ch = it.next();
			ChordPosition pos = chordPositions.get(ch);
			if (pos != null) {
				s += pos.toString() + "\n";
			}
		}
		
		for (int i = 0; i < songReader.getLyrics().size(); i++) {

			if (songReader.getLyrics().get(i).length() == 0) {
				ChordList chordList = songReader.getChords().getChords(i);

				for (int j = 0; j < chordList.size(); j++) {
					Chord chord = songReader.getChords().getChords(i).get(j);
					
					if (chord != null) {
						s += "[" + chord + "]";
					}
				}
			}
			
			for (int j = 0; j < songReader.getLyrics().get(i).length(); j++) {
				
				Chord chord = songReader.getChords().getChords(i).getAtIndex(j);
				
				if (chord != null) {
					s += "[" + chord + "]";
				}
				
				s += songReader.getLyrics().get(i).charAt(j);
			}				

			if (songReader.getLyrics().get(i).length() > 0) {
				for (int j = 0; j < songReader.getChords().getChords(i).size(); j++) {
					Chord chord = songReader.getChords().getChords(i).get(j);
					
					if (chord != null && chord.getIndex() > songReader.getLyrics().get(i).length()) {
						s += "[" + chord + "]";
					}
				}
			}
			
			s += "\n";
		}
		
		String ts = getTabs().toString();
		
		if (ts != null) {
			s += ts;
		}
		
		return s;
	}
	
	@Override
	public void addLine(String l) {
		
		String s = filter.filter(l);
		
		if (songReader == null) {
			
			if (s.startsWith("{t") || s.startsWith("{id")) {
				songReader = new SongReaderChordPro();
				altSongReader = new SongReaderText();								
			} else {
				songReader = new SongReaderText();								
				altSongReader = new SongReaderChordPro();
			}
			songReader.setSong(this);
			altSongReader.setSong(this);
		}
		songReader.addLine(s);
		altSongReader.addLine(s);
	}

	public Chord getChord(int line, int column) {

		if (songReader.getChords().size() < line+1) {
			Log.println(getClass().getName() + ": error invalid line " + line);
			return null;
		}

		ChordList chordList = songReader.getChords().getChordList(line);
		
		return chordList.getAtIndex(column);
	}

	@Override
	public void openBuffer() {
	
	}

	@Override
	public void closeBuffer() {

		if (getUniqueChords(altSongReader).size() > getUniqueChords(songReader).size()) {
			songReader = altSongReader;
		}

		for (int i = 0; i < getTabs().size(); i++) {
			Tab tab = getTabs().get(i);
			if (tab != null) {
				addChord(tab.getName());
			}
		}
		
		
		refreshChordPositions();
		
	}
	
	private int part1Index() {
		int index = 0;
		String []s = getTitle().split(" ");
		 
		for (int i = 0; i < s.length; i++) {
			index += s[i].length();
			if (index >= 10) {
				return index;
			} else {
				index++;
			}
		}
		
		return 0;
	}
	private String getTitlePart1() {
		return getTitle().substring(0, part1Index());
	}

	private String getTitlePart2() {
		return getTitle().substring(part1Index()+1);
	}
	
	public String toSvg() {

		int svgWidth = 420;
		
		String result = "";
		
  		getChordPosition("");	// need this to load chord positions

  		ArrayList<String> sequence = getChordSequence();
		result = "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"" + Integer.toString(svgWidth)+ "\" height=\"315\">";

		if (getTitle().length() < 20) {
			result += "<text x = \"400\" y = \"45\" font-size = \"32\" font-weight=\"bold\" text-anchor=\"end\" font-family=\"Super Sans\">" + getTitle() + "</text>";
			result += "<text x = \"400\" y = \"68\" font-size = \"16\" font-weight=\"bold\" text-anchor=\"end\" font-family=\"Super Sans\">" + getArtist() + "</text>";
		} else {
			result += "<text x = \"400\" y = \"45\" font-size = \"32\" font-weight=\"bold\" text-anchor=\"end\" font-family=\"Super Sans\">" + getTitlePart1() + "</text>";
			result += "<text x = \"400\" y = \"72\" font-size = \"32\" font-weight=\"bold\" text-anchor=\"end\" font-family=\"Super Sans\">" + getTitlePart2() + "</text>";
			result += "<text x = \"400\" y = \"95\" font-size = \"16\" font-weight=\"bold\" text-anchor=\"end\" font-family=\"Super Sans\">" + getArtist() + "</text>";
		}


		int chordCount = 0;
		
  		for (int i = 0; i < sequence.size(); i++) {
  			String ch = sequence.get(i);

  			if (!ch.startsWith("riff")) {
  				ChordChart chart = new ChordChart();
  				
  				ChordPosition position = getChordPosition(ch);

  				result += chart.renderSmallForImage(position, ch, chordCount);
  				chordCount++;
  			}			
  		}

  		int riffHeight = (chordCount / 6) * 60 + 150;
  		
  		for (int i = 0; i < getTabs().size(); i++) {
  			Tab tab = getTabs().get(i);
  			
  			if (tab != null) {
				TabChart chart = new TabChart();
  				
				result += chart.renderImage(tab, riffHeight, svgWidth);
  			}
  		}
  		
		result += "</svg>";

		return result;
	}
	
	public boolean fromSvg(String svg) {
		String lines[] = svg.split("\n");
		String lyrics = "";
		String chords = "";
		boolean lyricsStarted = false;
		boolean lyricsDone = false;
		boolean chordsStarted = false;
		boolean chordsDone = false;

		for (int i = 0; i < lines.length && !chordsDone; i++) {
			if (lines[i].startsWith("</text>")) {
				if (!lyricsDone) {
					lyricsDone = true;
				} else {
					chordsDone = true;
				}
			}
			if (lines[i].startsWith("<text")) {
				if (!lyricsStarted) {
					lyricsStarted = true;
				} else {
					chordsStarted = true;
				}
			}
			if (lines[i].startsWith("<tspan")) {
				if (lines[i].contains(">") && lines[i].contains("</tspan>")) {
					String s = lines[i].substring(lines[i].indexOf(">")+1, lines[i].indexOf("</tspan>"));
					if (chordsStarted) {
						chords += " " + s;
					} else {
						lyrics += s + "\n";
					}					
				}
 			}
		}
		
		fromString("title\nartist\n" + chords + "\n" + lyrics);

		return true;
	}
}
