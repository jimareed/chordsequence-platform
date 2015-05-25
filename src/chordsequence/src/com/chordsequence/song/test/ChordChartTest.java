package com.chordsequence.song.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.chordsequence.song.ChordChart;

public class ChordChartTest {

	@Test
	public void test_basic() {

		ChordChart chart = new ChordChart();
		String chord = "G";
		
		String result = chart.render(null, chord);
		
		assertTrue(result.contains(chord.toString())) ;
	}

	@Test
	public void test_hashtag() {

		ChordChart chart = new ChordChart();
		String chord = "F#m";
		
		String result = chart.render(null, chord);
		
		assertTrue(result.contains(chord.toString())) ;
	}
	
	@Test
	public void test_zoom() {

		ChordChart chart = new ChordChart();
		String chord = "G";
		
		String result = chart.renderZoom(null, chord);
		
		assertTrue(result.contains(chord.toString())) ;
	}
	
}
