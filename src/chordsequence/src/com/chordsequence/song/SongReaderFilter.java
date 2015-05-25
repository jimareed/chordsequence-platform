package com.chordsequence.song;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SongReaderFilter {

	public String filter(String s) {
		
		Pattern p = Pattern.compile("Key\\: \\[(.+?)\\]");
		Matcher matcher = p.matcher(s.trim());
		if (matcher.matches()) {
			s = "Key: " + matcher.group(1).trim();
		} 
		
		return s;
	}
	
}
