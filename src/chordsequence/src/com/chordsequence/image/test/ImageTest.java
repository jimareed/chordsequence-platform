package com.chordsequence.image.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.Test;

import com.chordsequence.image.Image;
import com.chordsequence.util.Log;


public class ImageTest {

	static private String CHORDCHARTSVG = "<svg width=\"55\" height=\"40\"><rect x=\"0\" y=\"0\" width=\"55\" height=\"40\" style=\"fill:rgb(0,0,0);stroke-width:0;stroke:rgb(0,0,0) stroke-opacity=\"0.0\" fill-opacity=\"0.0\" onclick=\"editchord('F%23m')\"/><line x1=\"7\" y1=\"6\" x2=\"45\" y2=\"6\" style=\"stroke:rgb(0,0,0);stroke-width:3\" /><line x1=\"7\" y1=\"15\" x2=\"45\" y2=\"15\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" /><line x1=\"7\" y1=\"22\" x2=\"45\" y2=\"22\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" /><line x1=\"7\" y1=\"30\" x2=\"45\" y2=\"30\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" /><line x1=\"7\" y1=\"37\" x2=\"45\" y2=\"37\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" /><line x1=\"7\" y1=\"5\" x2=\"7\" y2=\"37\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" /><line x1=\"15\" y1=\"5\" x2=\"15\" y2=\"37\" style=\"stroke:rgb(0,0,0);stroke-width:.25\" /><line x1=\"22\" y1=\"5\" x2=\"22\" y2=\"37\" style=\"stroke:rgb(0,0,0);stroke-width:.25\" /><line x1=\"30\" y1=\"5\" x2=\"30\" y2=\"37\" style=\"stroke:rgb(0,0,0);stroke-width:.25\" /><line x1=\"37\" y1=\"5\" x2=\"37\" y2=\"37\" style=\"stroke:rgb(0,0,0);stroke-width:.25\" /><line x1=\"45\" y1=\"5\" x2=\"45\" y2=\"37\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" /><circle cx=\"7\" cy=\"18\" r=\"2\" stroke=\"black\" stroke-width=\"1.5\" fill=\"black\"/><circle cx=\"15\" cy=\"33\" r=\"2\" stroke=\"black\" stroke-width=\"1.5\" fill=\"black\"/><circle cx=\"22\" cy=\"33\" r=\"2\" stroke=\"black\" stroke-width=\"1.5\" fill=\"black\"/><circle cx=\"30\" cy=\"18\" r=\"2\" stroke=\"black\" stroke-width=\"1.5\" fill=\"black\"/><circle cx=\"37\" cy=\"18\" r=\"2\" stroke=\"black\" stroke-width=\"1.5\" fill=\"black\"/><circle cx=\"45\" cy=\"18\" r=\"2\" stroke=\"black\" stroke-width=\"1.5\" fill=\"black\"/></svg>";

	
	@Test
	public void test_fromsvg() {
		
		assertTrue(true);
		
	}
	
}