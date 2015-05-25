package com.chordsequence.app;

import com.chordsequence.server.App;
import com.chordsequence.util.Template;
import com.chordsequence.util.TemplateSectionHandler;

public class ViewSettings  implements View, TemplateSectionHandler {

	String chord = "";
	
	GuitarSongsApp app = null;
	protected String name = "";

	@Override
	public void setName(String viewName) {
		name = viewName;		
	}
	
	
	
	@Override
	public String renderSection(String section) {

		if (section.equals("songid")) {
			return app.getParm("songid");
		}
		if (section.equals("linesperpage")) {
			return Integer.toString(app.getLinesPerPage());
		}
		if (section.equals("pagenum")) {
			return app.getParm("pagenum");
		}
		if (section.equals("chord")) {
			return app.getParm("chord");
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
		template.addSectionHandler(this, "linesperpage");
		template.addSectionHandler(this, "pagenum");
		template.addSectionHandler(this, "chord");
	}

}