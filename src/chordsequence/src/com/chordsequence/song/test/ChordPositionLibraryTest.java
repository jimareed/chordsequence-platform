package com.chordsequence.song.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.chordsequence.song.ChordPosition;
import com.chordsequence.song.ChordPositionLibrary;

public class ChordPositionLibraryTest {

	static protected String FOLDER = "/projects/chordsequence/temp/chordlib/";
	static protected String CMAJOR = "{define: C base-fret 0 frets 0 3 2 0 1 0 fingers - 3 2 - 1 -}";
	static protected String AMINOR =  "{define: Am base-fret 0 frets 0 0 2 2 1 0 fingers - - 3 2 1 -}";	
	static protected String AMAJOR =  "{define: A base-fret 0 frets 0 0 2 2 2 0 fingers - - 3 2 1 -}";
	
	@Test
	public void test_basic() {
		ChordPositionLibrary lib = new ChordPositionLibrary();
	
		assertTrue(lib.load(FOLDER));
	}	

	@Test
	public void test_clear() {
		ChordPositionLibrary lib = new ChordPositionLibrary();
	
		lib.load(FOLDER);
		
		lib.clear();
		
		assertTrue(lib.size() == 0);
	}	

	@Test
	public void test_add() {
		ChordPositionLibrary lib = new ChordPositionLibrary();
	
		lib.load(FOLDER);
		
		lib.clear();
		
		assertTrue(lib.size() == 0);
		
		ChordPosition pos = new ChordPosition(CMAJOR);
		
		lib.add(pos);
		
		assertTrue(lib.getPosition("C") != null);
		assertTrue(lib.hasChanged());
		assertTrue(lib.size() == 1);
	}	

	@Test
	public void test_save() {
		ChordPositionLibrary lib = new ChordPositionLibrary();
	
		lib.load(FOLDER);
		
		lib.clear();
		assertTrue(lib.size() == 0);
		
		ChordPosition pos = new ChordPosition(CMAJOR);
		
		lib.add(pos);

		assertTrue(lib.size() == 1);
		assertTrue(lib.save());

		lib.clear();
		assertTrue(lib.size() == 0);
		
		lib.load(FOLDER);
		assertTrue(lib.size() == 1);
	}	

	@Test
	public void test_addandsave() {
		ChordPositionLibrary lib = new ChordPositionLibrary();
	
		lib.load(FOLDER);
		
		lib.clear();
		lib.save();

		assertTrue(lib.size() == 0);
		
		ChordPosition pos = new ChordPosition(CMAJOR);
		
		lib.addAndSaveAppend(pos);

		assertTrue(lib.size() == 1);

		lib.clear();
		assertTrue(lib.size() == 0);
		
		lib.load(FOLDER);
		assertTrue(lib.size() == 1);
	}	
	
}
