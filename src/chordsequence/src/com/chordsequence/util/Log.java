package com.chordsequence.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class Log {
	
	static String fileName = "";
	static boolean debugEnabled = false;
	static boolean initialized = false;
	
	public static void enableDebug(boolean enable) {
		debugEnabled = enable;
	}
	
	public static void init() {

		initialized = true;

		String logPath = "/projects/chordsequence/logs/";
		String name = "chordsequence";
		
		(new File(logPath)).mkdirs();

		fileName = logPath + name + ".log";
		
		File file = new File(fileName);
		
		if (!file.exists()) {
			try {
				Writer output;
				output = new BufferedWriter(new FileWriter(fileName));
				output.append("ChordSequence Log\n");
				output.close();		
			} catch (Exception e) {
			}
		}
		
	}

	public static void println(String entry) {
		
		if (!initialized) {
			init();
		}
		
		System.out.println(entry);
		
		if (!entry.endsWith("\n")) {
			entry += "\n";
		}
		
		try {
			Writer output;
			output = new BufferedWriter(new FileWriter(fileName, true));
			output.append(entry);
			output.close();		
		} catch (Exception e) {
		}
	}
	
	public static void debug(String entry) {

		if (debugEnabled) {
			println(entry);
		}
	}

}
