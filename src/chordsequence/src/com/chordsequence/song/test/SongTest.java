package com.chordsequence.song.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import com.chordsequence.song.Chord;
import com.chordsequence.song.ChordPosition;
import com.chordsequence.song.ChordPositionLibrary;
import com.chordsequence.song.Chords;
import com.chordsequence.song.Library;
import com.chordsequence.song.Song;
import com.chordsequence.util.TextFile;

public class SongTest {

	static int PAGEWIDTH = 980;
	
	static String HOUSEOFTHERISINGSUN = "House of the Rising Sun\nAnimals\n      Am   C        D          F\nThere is a house in New Orleans,\n";
	static String AMAZINGGRACE = "Amazing Grace\nTraditional\nVerse 1\nG                               C             G\nAmazing grace how sweet the sound\n";
	static String HOUSEOFTHERISINGSUNLONGER = "House of the Rising Sun\nAnimals\n      Am   C        D          F\nThere is a house in New Orleans,\n      Am       C      E7\nThey call the Rising Sun\n";
	static String HOUSEOFTHERISINGSUNLONGER2 = "House of the Rising Sun\nAnimals\n      Am   C        D          F\nThere is a house in New Orleans,\n      Am  F#    C      E7\nThey call the Rising Sun\n";

	static String CHORDPRO_HOUSEOFTHERISINGSUN = "{t:House of the Rising Sun}\n{st:Animals}\nThere [Am]is a [C]house in [D]New Orleans[F],\n";

	static String ALWAYSONMYMIND = "{t:Always On My Mind}\n{st:Willie Nelson}\n[A]But you were [Bm]always [A7]on my [D]mind,  [Em][F#m]\n"; 
	static String SEVENYEARS = "Seven Years\nNohra Jones\n	C     C/B        Am     Am/G\nSpinning, laughing, dancing to\n    Am/F#    F\nher favorite song\n";
	
	static String ONEPAGESONG1 = "T1\nA1\n1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\n16\n17\n18\n19\n20\n";
	static String ONEPAGESONG2 = "T1\nA1\n1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\n";
	static String TWOPAGESONG1 = "T1\nA1\n1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\n16\n17\n18\n19\n20\n21\n22\n23\n24\n25\n26\n27\n28\n29\n30\n31\n32\n33\n34\n35\n36\n37\n38\n39\n40\n";
	static String TWOPAGESONG2 = "T1\nA1\n1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\n16\n17\n18\n19\n20\n21\n22\n23\n24\n25\n26\n27\n28\n29\n30\n31\n32\n33\n34\n35\n36\n37\n38\n39\n";
	static String THREEPAGESONG1 = "T1\nA1\n1\n2\n3\n4\n5\n6\n7\n8\n9\n10\n11\n12\n13\n14\n15\n16\n17\n18\n19\n20\n21\n22\n23\n24\n25\n26\n27\n28\n29\n30\n31\n32\n33\n34\n35\n36\n37\n38\n39\n40\n41\n";

	static String MIXEDFORMATSONG = "{t:House of the Rising Sun}\n{st:Animals}\n      Am   C        D          F\nThere is a house in New Orleans,\n";
	static String MIXEDFORMATSONG2 = "House of the Rising Sun\nAnimals\nThere [Am]is a [C]house in [D]New Orleans[F],\n";
	static String MIXEDFORMATSONG3 = "{t:House of the Rising Sun}\n{st:}\n      Am   C        D          F\nThere is a house in New Orleans,\n";

	static String SPACES = "{t:House of the Rising Sun}\n{st:Animals}\n[Am] [C] [D] [F](2x)\n";
	static String SPACES2 = "{t:House of the Rising Sun}\n{st:Animals}\n[Am] [C] [D] [F]\n[Am] [C] [D] [F] [B]\n";

	static String KEY = "{t:House of the Rising Sun}\n{st:Animals}\nKey: [Am]\n";
	static String CHORDSWITHSPACES = "{t:House of the Rising Sun}\n{st:Animals}\n  [Am]  [B]  The[C]re is a house\n";
	
	static String CHORDWITHTAB = "{t:House of the Rising Sun}\n{st:Animals}\nThere [Am]is a [C]house in [D]New Orleans[F],\nE-0--------0----------------0-0------0-----------|\nB-2-0---0--3------3---------2-----0--3--3--------|\nG-2---2----2----2---2-------2---2----2----2------|\nD-2--------3--3-------3-----2--------3------3----|\nA-0--------0------------0---0--------0--------0--|\nE------------------------------------------------|\n";

	static String CHORDWITHTAB2 = "{t:House of the Rising Sun}\n{st:Animals}\nThere [Am]is a [C]house in [D]New Orleans[F]\n" +
	"e------------0--------2----------2-----2-----2-------| \n" +
	"B----------------------------------------------------|\n" +
	"G----------------------------------------------------|\n" +
	"D----------------------------------------------------|\n" +
	"A----2--4--5-------2--4--5--4--2-------5--4--2-------|\n" +
	"E---(0)-----------------(0)--------------------------|\n";
	
	@Test
	public void test_basic() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUN);

		String result = song.toString();
		
		assertTrue(result.contains("[Am]is")) ;
		assertTrue(result.contains("[C]house")) ;
		assertTrue(result.contains("[D]New Orleans")) ;
		assertTrue(result.contains("New Orleans[F],")) ;
		assertTrue(song.getChords().size() == 1);
	}

	@Test
	public void test_sidebysidechords() {

		Library lib = new Library();
		lib.setChordLib(new ChordPositionLibrary());

		Song song = new Song(lib);

		song.fromString(ALWAYSONMYMIND);

		ArrayList<String> chords = song.getUniqueChords();
		
		assertTrue(chords.size()==6);
	}

	@Test
	public void test_filterkey() {

		Library lib = new Library();
		lib.setChordLib(new ChordPositionLibrary());

		Song song = new Song(lib);

		song.fromString(KEY);

		ArrayList<String> chords = song.getUniqueChords();
		
		assertTrue(chords.size()==0);
	}

	@Test
	public void test_chordswithspaces() {

		Library lib = new Library();
		lib.setChordLib(new ChordPositionLibrary());

		Song song = new Song(lib);

		song.fromString(CHORDSWITHSPACES);

		ArrayList<String> chords = song.getUniqueChords();
		
		assertTrue(chords.size()==3);
	}
	
	@Test
	public void test_fromstring() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUN);

		String result = song.toString();
		
		assertTrue(result.contains("[Am]is")) ;
		assertTrue(result.contains("[C]house")) ;
		assertTrue(result.contains("[D]New Orleans")) ;
		assertTrue(result.contains("New Orleans[F],")) ;
		assertTrue(song.getChords().size() == 1);
		
		song.fromString(AMAZINGGRACE);
		
		result = song.toString();

		assertTrue(result.contains("[G]Amazing grace how sweet the soun[C]d"));
	}

	
	@Test
	public void test_titleandartist() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUN);

		assertTrue(song.getTitle().equals("House of the Rising Sun")) ;
		assertTrue(song.getArtist().equals("Animals")) ;
	}

	@Test
	public void test_titleandartist_chordpro() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(CHORDPRO_HOUSEOFTHERISINGSUN);

		assertTrue(song.getTitle().equals("House of the Rising Sun")) ;
		assertTrue(song.getArtist().equals("Animals")) ;
	}
	
	@Test
	public void test_svg() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUN);

		String result = song.toSvg(0, PAGEWIDTH);
		
		assertTrue(result.contains("<svg")) ;
		assertTrue(result.contains("There is a house in New Orleans,")) ;
	}

	@Test
	public void test_svgonepage() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);

		song.fromString(ONEPAGESONG1);
		assertTrue(song.numPages() == 1);

		song.fromString(ONEPAGESONG2);
		assertTrue(song.numPages() == 1);

		song.fromString(TWOPAGESONG1);
		assertTrue(song.numPages() == 2);

		song.fromString(TWOPAGESONG2);
		assertTrue(song.numPages() == 2);

		song.fromString(THREEPAGESONG1);
		assertTrue(song.numPages() == 3);
		
		String result = song.toSvg(0, PAGEWIDTH);
		
		assertTrue(result.contains(">1<"));
		assertTrue(result.contains(">20<"));
		assertTrue(!result.contains(">21<"));

		result = song.toSvg(1, PAGEWIDTH);
		
		assertTrue(result.contains(">21<"));
		assertTrue(result.contains(">40<"));
		assertTrue(!result.contains(">1<"));
	
	}	
	
	/*
	@Test
	public void test_svgzoomed() {

		Song song = new Song(null);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUNLONGER);

		String result = song.toSvgZoomed(2, 0, "song0");
		
		assertTrue(result.contains("<svg")) ;
		assertTrue(result.contains("There is a house in New Orleans,")) ;
		assertTrue(!result.contains("They call the")) ;
	}*/

	@Test
	public void test_moveChord() {
		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUNLONGER);

		assertTrue(song.getChord(0, 6) != null);
		assertTrue(song.getChord(0, 7) == null);
		
		assertTrue(song.moveChord(0, 6, "right"));

		assertTrue(song.getChord(0, 6) == null);
		assertTrue(song.getChord(0, 7) != null);
		
		assertTrue(song.moveChord(0, 7, "left"));

		assertTrue(song.getChord(0, 6) != null);
		assertTrue(song.getChord(0, 7) == null);

		/*
		assertTrue(song.getChord(1, 6) == null);

		assertTrue(song.moveChord(2, 6, "up"));
	
		assertTrue(song.getChord(1, 6) != null);
		assertTrue(song.getChord(2, 6) == null); */
	}
	
	@Test
	public void test_getuniquechords() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUN);

		ArrayList<String> list = song.getUniqueChords();
		
		assertTrue(list.size() == 4) ;
	}

	@Test
	public void test_getchordsequence() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUN);

		ArrayList<String> list = song.getChordSequence();
		
		assertTrue(list.size() == 4) ;
		assertTrue(list.get(0).equals("Am")) ;
		assertTrue(list.get(1).equals("C")) ;
		assertTrue(list.get(2).equals("D")) ;
		assertTrue(list.get(3).equals("F")) ;
		
		song.addChordRef("E7", 0, 15);

		list = song.getChordSequence();
		
		assertTrue(list.size() == 5) ;
		assertTrue(list.get(0).equals("Am")) ;
		assertTrue(list.get(1).equals("C")) ;
		assertTrue(list.get(2).equals("E7")) ;
		assertTrue(list.get(3).equals("D")) ;
		assertTrue(list.get(4).equals("F")) ;
		
		song.addChord("G");

		list = song.getChordSequence();
		
		assertTrue(list.size() == 6) ;
		assertTrue(list.get(5).equals("G")) ;
	}
/*	
	@Test
	public void test_getuniquechordsforwardslash() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(SEVENYEARS);

		ArrayList<String> list = song.getUniqueChords();

		Log.println(getClass().getName() + ": test_getuniquechordsforwardslash not implemented yet");
		Log.println(song.toString());
//		assertTrue(list.size() == 6) ;
	}
*/	
	@Test
	public void test_getuniquechords_chordpro() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(CHORDPRO_HOUSEOFTHERISINGSUN);

		ArrayList<String> list = song.getUniqueChords();
		
		assertTrue(list.size() == 4) ;
	}

	@Test
	public void test_spaces() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(SPACES);

		ArrayList<String> list = song.getUniqueChords();

		assertTrue(song.toString().contains("[Am]"));
		assertTrue(song.toString().contains("[C]"));
		assertTrue(song.toString().contains("[D]"));
		assertTrue(song.toString().contains("[F]"));
		assertTrue(song.toString().contains("(2x)"));
		
		assertTrue(list.size() == 4) ;
	}

	@Test
	public void test_spaces2() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(SPACES2);


		ArrayList<String> list = song.getUniqueChords();

		assertTrue(song.toString().contains("[Am]"));
		assertTrue(song.toString().contains("[C]"));
		assertTrue(song.toString().contains("[D]"));
		assertTrue(song.toString().contains("[F]"));
		assertTrue(song.toString().contains("[B]"));

		
		assertTrue(list.size() == 5) ;
	}
	
	
	@Test
	public void test_getuniquechords2() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(AMAZINGGRACE);

		ArrayList<String> list = song.getUniqueChords();
		
		assertTrue(list.size() == 2) ;
	}

	@Test
	public void test_addchord() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUN);

		Chords chords = song.getChords();
		
		assertTrue(chords.getChordList(0).size() == 4) ;

		song.addChordRef("C", 0, 15);

		assertTrue(chords.getChordList(0).size() == 5) ;
		
	}

	@Test
	public void test_deletechord() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUN);

		Chords chords = song.getChords();
		
		assertTrue(chords.getChordList(0).size() == 4) ;

		song.deleteChordRef(0, 6);

		assertTrue(chords.getChordList(0).size() == 3) ;
		
	}

	@Test
	public void test_addandgetchord() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUN);

		Chords chords = song.getChords();
		
		song.addChordRef("C", 0, 15);

		Chord chord = song.getChord(0, 15);
		
		assertTrue(chord != null) ;

		assertTrue(chord.toString().equals("C"));

		chord = song.getChord(0, 16);

		assertTrue(chord == null) ;
		
	}

	@Test
	public void test_getchord() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUN);

		Chord chord = song.getChord(0, 5);
		assertTrue(chord == null) ;
		
		chord = song.getChord(0, 6);
		
		assertTrue(chord != null) ;
		assertTrue(chord.toString().equals("Am"));

		chord = song.getChord(0, 11);
		
		assertTrue(chord != null) ;
		assertTrue(chord.toString().equals("C"));
		
	}

	@Test
	public void test_getchord_chordpro() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(CHORDPRO_HOUSEOFTHERISINGSUN);

		Chord chord = song.getChord(2, 5);
		assertTrue(chord == null) ;
		
		chord = song.getChord(0, 6);
		
		assertTrue(chord != null) ;
		assertTrue(chord.toString().equals("Am"));
		
		chord = song.getChord(0, 11);
		
		assertTrue(chord != null) ;
		assertTrue(chord.toString().equals("C"));
		
	}
	
	@Test
	public void test_getsvglineandcol() {
		
		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUN);

		assertTrue(song.getSvgLine(160) == 2);
		assertTrue(song.getSvgColumn(200) == 16);
	}	
	
	@Test
	public void test_tostring() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUN);

		String result = song.toString();

		Song s2 = new Song(lib);
		
		file.setBuffer(s2);
		file.fromString(result);
		
		result = s2.toString();
		
		assertTrue(result.contains("[Am]is")) ;
		assertTrue(result.contains("[C]house")) ;
		assertTrue(result.contains("[D]New Orleans")) ;
		assertTrue(result.contains("New Orleans[F],")) ;

		assertTrue(song.getChords().size() == 1);
	}

	@Test
	public void test_tostringwithchordchange() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUN);

		String result = song.toString();

		assertTrue(result.contains("{t:House of the Rising Sun}")) ;
		assertTrue(result.contains("{define: Am")) ;
		
		ChordPosition pos = song.getChordPosition("Am");

		assertTrue(pos != null) ;
		
		pos.updateString(4, 2);
		
		result = song.toString();
		
		assertTrue(result.contains("{define: Am")) ;
		
		song.fromString(result);
		result = song.toString();
		
		assertTrue(result.contains("{define: Am")) ;
	}

	@Test
	public void test_updateChordPositions() {
		Library lib = new Library();
		Song s = new Song(lib);

		/*
		ChordPosition 
		*/
		
		s.fromString(HOUSEOFTHERISINGSUN);
	
		HashMap<String, ChordPosition> positions = s.getChordPositions();
		
		assertTrue(positions.size() == 4);
		
		
	}

	@Test
	public void test_renamechord() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		song.fromString(HOUSEOFTHERISINGSUN);

		ArrayList<String> list = song.getChordSequence();
		
		assertTrue(list.size() == 4) ;
		assertTrue(list.get(0).equals("Am")) ;
		assertTrue(list.get(1).equals("C")) ;
		assertTrue(list.get(2).equals("D")) ;
		assertTrue(list.get(3).equals("F")) ;
		
		song.renameChord("D", "E7");

		list = song.getChordSequence();
		
		assertTrue(list.size() == 4) ;
		assertTrue(list.get(0).equals("Am")) ;
		assertTrue(list.get(1).equals("C")) ;
		assertTrue(list.get(2).equals("E7")) ;
		assertTrue(list.get(3).equals("F")) ;

		song.renameChord("Am", "C");

		list = song.getChordSequence();

		assertTrue(list.size() == 3) ;
		assertTrue(list.get(0).equals("C")) ;
		assertTrue(list.get(1).equals("E7")) ;
		assertTrue(list.get(2).equals("F")) ;
		
	}

	@Test
	public void test_tosvg() {
		Library lib = new Library();
		lib.setChordLib(new ChordPositionLibrary());

		Song song = new Song(lib);

		song.fromString(ALWAYSONMYMIND);
		
		String svg = song.toSvg();
		
		assertTrue(svg != null);
	}	
	
	@Test
	public void test_mixedformatsong() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(MIXEDFORMATSONG);

		assertTrue(song.getUniqueChords().size() == 4) ;
		assertTrue(song.getTitle().equals("House of the Rising Sun")) ;
		assertTrue(song.getArtist().equals("Animals")) ;
	}

	@Test
	public void test_mixedformatsong2() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(MIXEDFORMATSONG2);

		assertTrue(song.getUniqueChords().size() == 4) ;
		assertTrue(song.getTitle().equals("House of the Rising Sun")) ;
		assertTrue(song.getArtist().equals("Animals")) ;
	}
	@Test
	public void test_mixedformatsong3() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(MIXEDFORMATSONG3);

		assertTrue(song.getUniqueChords().size() == 4) ;
		assertTrue(song.getTitle().equals("House of the Rising Sun")) ;
		assertTrue(song.getArtist().isEmpty()) ;
	}

	@Test
	public void test_chordwithtab() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(CHORDWITHTAB);

		String result = song.toString();
		
		ArrayList<String> chords = song.getUniqueChords();
		
		assertTrue(result.contains("A-0--------0------------0---0--------0--------0--|"));

	}
	
	@Test
	public void test_chordwithtab2() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(CHORDWITHTAB2);

		String result = song.toSvg(0, 800, song.numLines(), false);

		assertTrue(!result.contains("e------------0--------2----------2-----2-----2-------"));

		result = song.toString();

		assertTrue(result.contains("riff 1:"));
	}
	
	
	@Test
	public void test_tosvgwithtab() {

		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(CHORDWITHTAB);

		String result = song.toSvg();
		
		assertTrue(result != null);

	}

	@Test
	public void test_addchordref() {
		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUNLONGER2);

		assertTrue(song.getChord(0, 7) == null);

		song.addChordRef("Am", 0, 7);
		assertTrue(song.getChord(0, 7) != null);
		assertTrue(song.getChord(0, 7).toString().equals("Am"));
		song.addChordRef("C", 0, 7);
		assertTrue(song.getChord(0, 7) != null);
		assertTrue(song.getChord(0, 7).toString().equals("C"));
		song.addChordRef("C", 0, 7);
		assertTrue(song.getChord(0, 7) != null);
		assertTrue(song.getChord(0, 7).toString().equals("C"));
		song.replaceChordRef("Am", 0, 7);
		assertTrue(song.getChord(0, 7) != null);
		assertTrue(song.getChord(0, 7).toString().equals("Am"));
		song.replaceChordRef("Am", 0, 7);
		assertTrue(song.getChord(0, 7) == null);
		song.replaceChordRef("F#", 0, 7);
		assertTrue(song.getChord(0, 7) != null);
		assertTrue(song.getChord(0, 7).toString().equals("F#"));
	}
	
	@Test
	public void test_fromsvg() {
		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUNLONGER);

		String result = song.toSvg(0, 980, song.numLines(), false);
		
		assertTrue(result != null);
		
		Song song2 = new Song(lib);
		
		assertTrue(song2.fromSvg(result));
		
		ArrayList<String> chords = song2.getUniqueChords();

		assertTrue(chords.size() == 5);
	}	


	@Test
	public void test_fromsvg2() {
		Library lib = new Library();
		ChordPositionLibrary chordLib = new ChordPositionLibrary();
		lib.setChordLib(chordLib);
		
		Song song = new Song(lib);
		TextFile file = new TextFile();
		
		file.setBuffer(song);

		file.fromString(HOUSEOFTHERISINGSUN);

		String result = song.toSvg(0, 980, song.numLines(), false);
		
		assertTrue(result != null);
		
		Song song2 = new Song(lib);
		
		assertTrue(song2.fromSvg(result));
		
		ArrayList<String> chords = song2.getUniqueChords();

		assertTrue(chords.size() == 4);

		
	}	

	
}
