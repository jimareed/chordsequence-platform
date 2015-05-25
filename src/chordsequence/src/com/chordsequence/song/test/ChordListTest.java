package com.chordsequence.song.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import com.chordsequence.song.Chord;
import com.chordsequence.song.ChordList;

public class ChordListTest {

	@Test
	public void test_basic() {

		ChordList cl = new ChordList();
		
		cl.add("A  B  C  D  E");
		
		assertTrue(cl.size() == 5);
	}

	@Test
	public void test_addchord() {

		ChordList cl = new ChordList();
		
		cl.add("A  B  C  D  E");
		
		cl.addChord("F", 10);
		
		assertTrue(cl.size() == 6);
	}

	@Test
	public void test_getchords() {

		ChordList cl = new ChordList();
		
		cl.add("A  B  C  D  E");
		
		cl.addChord("F", 15);

		ArrayList<Chord> chords = cl.getChords();
		
		assertTrue(chords.size() == 6);
		assertTrue(chords.get(0).toString().equals("A"));
		assertTrue(chords.get(4).toString().equals("E"));
		assertTrue(chords.get(5).toString().equals("F"));
	}

	@Test
	public void test_getchords2() {

		ChordList cl = new ChordList();
		
		cl.add("A  B  C  D  E");
		
		cl.addChord("F", 4);

		ArrayList<Chord> chords = cl.getChords();
		
		assertTrue(chords.size() == 6);
		assertTrue(chords.get(0).toString().equals("A"));
		assertTrue(chords.get(1).toString().equals("B"));
		assertTrue(chords.get(2).toString().equals("F"));
		assertTrue(chords.get(3).toString().equals("C"));
	}
	
}
