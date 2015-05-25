package com.chordsequence.song.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.chordsequence.song.Chord;

public class ChordTest {

	@Test
	public void test_isAChord() {

		assertTrue(Chord.isAChord("A"));
		assertTrue(Chord.isAChord("B"));
		assertTrue(Chord.isAChord("C"));
		assertTrue(Chord.isAChord("D"));
		assertTrue(Chord.isAChord("E"));
		assertTrue(Chord.isAChord("F"));
		assertTrue(Chord.isAChord("G"));
		assertTrue(!Chord.isAChord("H"));

		assertTrue(Chord.isAChord("C#"));
		assertTrue(Chord.isAChord("Bb"));
		assertTrue(!Chord.isAChord("Bd"));

		assertTrue(Chord.isAChord("B7"));

		assertTrue(Chord.isAChord("F9"));
		assertTrue(Chord.isAChord("Fm7"));
		assertTrue(Chord.isAChord("Gm9"));

		assertTrue(Chord.isAChord("Cmaj7"));
		
		assertTrue(Chord.isAChord("B7/A"));

		assertTrue(Chord.isAChord("C#7/Gb"));

		assertTrue(Chord.isAChord("Am"));
		assertTrue(Chord.isAChord("Am6"));
		assertTrue(Chord.isAChord("A#dim7"));
		
	}

	@Test
	public void test_startsWithAChord() {

		assertTrue(Chord.startsWithAChord("A"));
		assertTrue(Chord.startsWithAChord("B"));
		assertTrue(Chord.startsWithAChord("C"));
		assertTrue(Chord.startsWithAChord("D*"));
		assertTrue(Chord.startsWithAChord("E"));
		assertTrue(Chord.startsWithAChord("F*"));
		assertTrue(Chord.startsWithAChord("G"));
		assertTrue(!Chord.startsWithAChord("H"));

		assertTrue(Chord.startsWithAChord("C#*"));
		assertTrue(Chord.startsWithAChord("Bb"));
		assertTrue(!Chord.startsWithAChord("Bd"));

		assertTrue(Chord.startsWithAChord("B7*"));

		assertTrue(Chord.startsWithAChord("B7**"));

		assertTrue(Chord.startsWithAChord("B7+"));
		
		assertTrue(Chord.startsWithAChord("B7/A"));

		assertTrue(Chord.startsWithAChord("C#7/Gb"));

		assertTrue(Chord.startsWithAChord("Am"));
		assertTrue(Chord.startsWithAChord("Am6"));
		assertTrue(Chord.startsWithAChord("A#dim7"));
		
	}
	
}
