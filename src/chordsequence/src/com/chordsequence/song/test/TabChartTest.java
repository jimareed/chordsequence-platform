package com.chordsequence.song.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.chordsequence.song.Tab;
import com.chordsequence.song.TabChart;

public class TabChartTest {
	static protected String BASICTAB = "E-0--------0----------------0-0------0-----------|\nB-2-0---0--3------3---------2-----0--3--3--------|\nG-2---2----2----2---2-------2---2----2----2------|\nD-2--------3--3-------3-----2--------3------3----|\nA-0--------0------------0---0--------0--------0--|\nD------------------------------------------------|\n";

	
	@Test
	public void test_basic() {
		
		Tab tab = new Tab("riff 1");
		TabChart chart = new TabChart();
		
		String svg = chart.render(tab, "small", false);
		
		assertTrue(svg != null);
		assertTrue(svg.contains("<svg"));
		
	}

	@Test
	public void test_image() {
		
		Tab tab = new Tab("riff 1");
		TabChart chart = new TabChart();
		
		String svg = chart.renderImage(tab, 50, 480);
		
		assertTrue(svg != null);
		assertTrue(!svg.contains("<svg"));
		
	}
	
}
