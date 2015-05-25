package com.chordsequence.server;

import java.util.HashMap;

import com.chordsequence.util.Log;
import com.chordsequence.util.TextFile;
import com.chordsequence.util.TextFileBuffer;

public class Users implements TextFileBuffer {

	protected HashMap<String, User> users = new HashMap<String, User>(); 
	
	static protected String FOLDER = "/projects/chordsequence/data/";
	static protected String FILENAME = "users";
	static protected String EXTENSION = "txt";
	
	
	public void load() {
		TextFile file = new TextFile();
		
		file.setBuffer(this);
		
		file.load(FOLDER, FILENAME, EXTENSION);
	}
	
	public boolean login(String name, String password) {
		
		if (users.size() == 0) {
			load();
		}
		
		User user = users.get(name);
		
		if (user == null) {
			Log.println(getClass().getName() + ": invalid user name" + name);
			return false;
		}
		
		if (!user.getPassword().isEmpty() && !user.getPassword().equals(password)) {
			Log.println(getClass().getName() + ": invalid password for user " + name);
			return false;
		}

		return true;
	}
	
	@Override
	public void openBuffer() {
	}

	@Override
	public void addLine(String s) {
		User user = new User();
		
		user.fromString(s);
		
		if (!user.getName().isEmpty()) {
			users.put(user.getName(), user);
		}
	}


	@Override
	public void closeBuffer() {
	}
	
}
