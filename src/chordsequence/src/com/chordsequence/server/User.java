package com.chordsequence.server;

import com.chordsequence.util.Log;

public class User {
	private String name = "";
	private String password = "";
	
	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}
	
	public void fromString(String credentials) {
		String[]s = credentials.split("\t");
		
		if (s.length < 2) {
			Log.println(getClass().getName() + ": fromString error");
		}
		
		name = s[0];
		password = s[1];
		
		if (password.equals("<none>")) {
			password = "";
		}
	}
}
