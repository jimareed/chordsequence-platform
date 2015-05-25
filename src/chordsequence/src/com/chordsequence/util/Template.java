package com.chordsequence.util;

import java.util.HashMap;

public class Template implements TextFileBuffer {

	protected String result = "";
	protected TemplateSectionHandler handlers2 = null;
	protected HashMap<String, TemplateSectionHandler> handlers = new HashMap<String, TemplateSectionHandler>(); 
	
	public void addSectionHandler(TemplateSectionHandler handler, String name) {
	
		handlers2 = handler;
		
		handlers.put(name, handler);
	}
	
	public String render() {
		return result;
	}

	private void addLinePart(String s) {
		String section = "";

		if (s.trim().startsWith("{$") && s.trim().endsWith("}")) {
			section = s.trim();
			section = section.substring(2, section.length()-1);

			if (!section.isEmpty() && handlers.containsKey(section)) {
				result += handlers.get(section).renderSection(section);
			} else {
				result += s;
			}
		} else {
			if (s.contains("{$") && s.substring(s.indexOf("{$")).contains("}")) {
				addLinePart(s.substring(0,s.indexOf("{$")));
				addLinePart(s.substring(s.indexOf("{$"),s.indexOf("}")+1));
				if (s.indexOf("}") < s.length()) {
					addLinePart(s.substring(s.indexOf("}")+1,s.length()));
				}
			} else {
				result += s;
			}
		}
	}
	
	@Override
	public void addLine(String s) {

		addLinePart(s);
		
		result += "\n";
	}

	@Override
	public void openBuffer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeBuffer() {
		// TODO Auto-generated method stub
		
	}
}
