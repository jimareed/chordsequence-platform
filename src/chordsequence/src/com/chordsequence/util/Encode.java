package com.chordsequence.util;

public class Encode {

	static public String urlEncode(String s) {
		String e =  s.replaceAll("#", "%23");
		return e;				
	}
}
