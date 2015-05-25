package com.chordsequence.app;

import com.chordsequence.server.App;
import com.chordsequence.util.Template;
import com.chordsequence.util.TemplateSectionHandler;

public class ViewAddChord implements View, TemplateSectionHandler {

	String chord = "";
	
	GuitarSongsApp app = null;
	protected String name = "";
	
	public void setChord(String theChord) {
		if (theChord != null) {
			chord = theChord;
		}
	}
	
	@Override
	public String renderSection(String section) {

		if (section.equals("songid")) {
			return app.getParm("songid");
		}
		if (section.equals("chord")) {
			return chord;
		}
		if (section.equals("action")) {
			if (chord.isEmpty()) {
				return "addchord";
			} else {
				return "renamechord";
			}
		}
		if (section.equals("pagenum")) {
			return app.getParm("pagenum");
		}
		return "";
	}

	@Override
	public void setApp(App theApp) {
		app = (GuitarSongsApp)theApp;
		
	}

	@Override
	public void setTemplate(Template template) {
		template.addSectionHandler(this, "songid");
		template.addSectionHandler(this, "pagenum");
		template.addSectionHandler(this, "chord");
		template.addSectionHandler(this, "action");
	}

	@Override
	public void setName(String viewName) {
		name = viewName;
		
	}

}