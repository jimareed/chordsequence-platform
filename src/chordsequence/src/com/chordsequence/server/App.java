package com.chordsequence.server;

import java.io.OutputStream;



public interface App {
	
	public void setSessionId(String sessionId);
	
	public Doc getDoc();
	
	public void setParm(String parm, String value);
	public String getParm(String parm);
	
	public String render(String action, String view);
	
	public void renderImage(String action, String view, OutputStream out);
}
