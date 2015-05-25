package com.chordsequence.server;

import com.chordsequence.util.Log;
import com.chordsequence.util.TextFile;
import com.chordsequence.util.TextFileBuffer;

public class Comments implements TextFileBuffer {

	protected String comment = "";
	
	public void add(String theComment, String user, String sessionId, String folder) {
	
		if (!folder.endsWith("/")) {
			folder += "/";
		}
		
		if (user == null || user.isEmpty()) {
			user = "guest";
		}

		if (theComment != null && !theComment.isEmpty()) {
			comment = theComment;
			
			String fileName = user + "-" + sessionId + "-" + System.currentTimeMillis();
			
			TextFile file = new TextFile();

			file.setBuffer(this);
			
			if (!file.save(folder + "comments/", fileName, "txt")) {
				Log.println("error saving comment " + folder + " " + fileName);
			}
		}
		
	}
	
	@Override
	public String toString() {
		return comment;
	}

	@Override
	public void openBuffer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addLine(String s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeBuffer() {
		// TODO Auto-generated method stub
		
	}
}
