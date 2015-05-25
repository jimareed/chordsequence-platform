package com.chordsequence.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class TextFile {

	protected TextFileBuffer buffer = null;
	protected String contents;
	
	private String filePath = "/projects/chordsequence/data/";
	private String extension = "txt";
	
	public String toString() {
		return contents;
	}
	
	public void setBuffer(TextFileBuffer theBuffer) {
		buffer = theBuffer;
	}

	public void fromString(String string) {
		
		contents = string;
		
		String lines[] = string.split("\n");

		if (buffer != null) {
			buffer.openBuffer();
		}
		
		for (int i = 0; i < lines.length; i++) {
			String s = lines[i];
			
			if (s.endsWith("\r")) {
				s = s.substring(0, s.length()-1);
			}
			if (buffer != null) {
				buffer.addLine(s);
			}
		}
		
		if (buffer != null) {
			buffer.closeBuffer();
		}
	}

	public boolean load(String path, String name, String ext) {
		
		filePath = path;
		
		if (!filePath.endsWith("/")) {
			filePath += "/";
		}
		
		extension = ext;
		
		return load(name);
	}

	public boolean save(String path, String name, String ext) {

		(new File(path)).mkdirs();
		
		filePath = path;
		
		if (!filePath.endsWith("/")) {
			filePath += "/";
		}
		
		extension = ext;
		
		return save(name);
	}
	
	public boolean load(String name) {
		contents = "";
		String fileName = filePath + name + "." + extension;
		
		
		try {

			if (buffer != null) {
				buffer.openBuffer();
			}
			
			FileInputStream	fstream = new FileInputStream(fileName);
			
			DataInputStream	in = new DataInputStream(fstream);
			BufferedReader 	br = null;
			
			br = new BufferedReader(new InputStreamReader(in));				

			String strLine;
			while ((strLine = br.readLine()) != null) {
				contents += strLine + "\r\n";
				
				if (buffer != null) {
					buffer.addLine(strLine);
				}
	 	}
			in.close();
			if (buffer != null) {
				buffer.closeBuffer();
			}

		} catch (Exception e) {
			if (buffer != null) {
				buffer.closeBuffer();
			}
			e.printStackTrace();
			Log.println(getClass().getName() + " Error: " + e.getMessage() + ", filename: " + fileName);
			return false;
		}

		return true;
	}

	public boolean save(String name) {
		String fileName = filePath + name + "." + extension;
		String contents = buffer.toString();
		
		try {
			// Create file
			
			FileOutputStream fstream = new FileOutputStream(fileName);
			OutputStreamWriter writer = null;
			
			writer = new OutputStreamWriter(fstream);
			
			Writer out = new BufferedWriter(writer);
		    out.write(contents);
		    out.close();

		}catch (Exception e){//Catch exception if any
		  Log.println(getClass().getName() + ": Error: " + e.getMessage());
		  return false;
		}
		
		return true;
	}

}
