package com.chordsequence.app;

import com.chordsequence.server.App;
import com.chordsequence.song.Library;
import com.chordsequence.song.Song;
import com.chordsequence.util.Log;
import com.chordsequence.util.Template;
import com.chordsequence.util.TemplateSectionHandler;

public class ViewExport  implements View, TemplateSectionHandler {

	GuitarSongsApp app = null;
	protected String name = "";

	@Override
	public void setName(String viewName) {
		name = viewName;		
	}
	
	@Override
	public String renderSection(String section) {

		if (section.equals("song") && app != null) {
			Library lib = (Library)app.getDoc();
			
			Song song = lib.getSongs().get(app.getParm("songid"));
			
			if (song != null) {
				return song.toString();
			}
		}
		if (section.equals("songid") && app != null) {
			Library lib = (Library)app.getDoc();
			
			Song song = lib.getSongs().get(app.getParm("songid"));
			
			if (song != null) {
				return song.getId();
			}
		}
		if (section.equals("pagenum") && app != null) {
			String pageNum = app.getParm("pagenum");
			
			if (pageNum == null) {
				Log.println(getClass().getName() +  ": error getting pagenum");
				return "0";
			}
			return pageNum;
		}
		return "";
	}

	
	@Override
	public void setApp(App theApp) {
		app = (GuitarSongsApp)theApp;
		
	}

	@Override
	public void setTemplate(Template template) {
		template.addSectionHandler(this, "body");
		template.addSectionHandler(this, "song");
		template.addSectionHandler(this, "songid");
		template.addSectionHandler(this, "pagenum");
		
	}

}

