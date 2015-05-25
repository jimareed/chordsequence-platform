package com.chordsequence.image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.chordsequence.util.Log;

import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;

public class Image {

	protected String svgString = "";
	
	static protected String TESTSVG = "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"450\" height=\"500\">\n<text x=\"10\" y=\"10\" fill=\"black\" >BATIK3</text>\n<circle cx=\"45\" cy=\"18\" r=\"2\" stroke=\"black\" stroke-width=\"1.5\" fill=\"black\"/>\n<rect x=\"0\" y=\"0\" width=\"55\" height=\"40\"/></svg>";

	public boolean loadSvg(String svg) {
		svgString = svg;
	
		return true;
	}
	
	
	private boolean saveAsJPeg(InputStream in, OutputStream out) {

		try {
	        // Create a JPEG transcoder
	        PNGTranscoder t = new PNGTranscoder();

	        // Set the transcoding hints.
	        t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY,
	                   new Float(.8));

	        // Create the transcoder input.
	        TranscoderInput input = new TranscoderInput(in);

	        // Create the transcoder output.
	        TranscoderOutput output = new TranscoderOutput(out);

	        // Save the image.
	        t.transcode(input, output);

	        // Flush and close the stream.
	        out.flush();
	        out.close();		
		} catch (Exception e) {
			Log.println(getClass().getName() + ": error");
			return false;
		}
        
        return true;
	}

	
	public boolean save(OutputStream out) {

		boolean rc = true;

		if (svgString.isEmpty()) {
			Log.println(getClass().getName() + ": error, empty svg");
			return false;
		}

		InputStream in = new ByteArrayInputStream(svgString.getBytes());
		
		try {
			saveAsJPeg(in, out);

		} catch (Exception e) {
			Log.println(getClass().getName() + ": error saving file");
			rc = false;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {

				}
			}
		}

		return rc;

	}
}
