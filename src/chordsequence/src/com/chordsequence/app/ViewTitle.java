package com.chordsequence.app;

import com.chordsequence.server.App;
import com.chordsequence.song.Song;
import com.chordsequence.util.Log;
import com.chordsequence.util.Template;
import com.chordsequence.util.TemplateSectionHandler;
import com.chordsequence.util.TextFile;

public class ViewTitle implements View, TemplateSectionHandler {

	protected GuitarSongsApp app = null;
	protected Song song = null;
	protected String editView = "";
	protected String view = "";
	protected String name = "";

	@Override
	public void setName(String viewName) {
		name = viewName;		
	}
	
	public void setSong(Song theSong) {
		song = theSong;
	}

	public void setView(String s) {
		view = s;
	}
	
	public void setEditView(String s) {
		editView = s;
	}
	
	boolean isFirstPage() {
		boolean firstPage = true;
		
		if (app != null) {
			String pageNum = app.getParm("pagenum");
			
			if (pageNum != null) {
				firstPage = pageNum.equals("0");
			}
		}
		
		return firstPage;
	}

	String getPageNum() {
		String pageNum = "0";
		
		if (app != null) {
			pageNum = app.getParm("pagenum");
			
			if (pageNum == null) {
				pageNum = "0";
			}
		}
		
		try {
			int i = Integer.parseInt(pageNum);
			
			pageNum = Integer.toString(i+1);
			
		} catch (Exception e) {
			pageNum = "1";
		}
		
		return pageNum;
	}
	
	private String encode(String s) {
		String e =  s.replaceAll("#", "%23");
		return e;		
	}
	

	private String buildRef(String action, String view, String icon, String songId, String title) {
		String ref = "<a href=\"/chordsequence?";
		if (!action.isEmpty()) {
			ref += "action=" + action + "&";
		}
		ref += "view=" + view;
		if (!songId.isEmpty()) {
			ref += "&songid=" + songId;
		}
		ref += "\" title=\"" + title + "\"><img src=\"/images/" + icon + ".png\"/></a>";
		
		return ref;
	}
	
	@Override
	public String renderSection(String section) {
		String s = "";

		if (section.equals("title")) {
			if (song == null) {
				s = "<h1>Songs</h1>";
			} else {
				if (isFirstPage()) {
					s = "<h1>" + song.getTitle() + "</h1>";
				} else {
					s = "<h1>" + song.getTitle() + "</h1>";
				}
			}
		} else if (section.equals("artist")) {
			if (song != null) {
				if (isFirstPage()) {
					s = "<b>" + song.getArtist() + "</b>";
				} else {
					s = "<b>page " + getPageNum() + "</b>";
				}
			}
		} else if (section.equals("editview")) {
			s = editView;
	  	} else if (section.equals("openmode")) {
			if (song == null) {
				s = "edit";
			} else {
				s = "";
			}
		} else if (section.equals("songid")) {
			if (song != null) {
				s = song.getId();
			}
		} else if (section.equals("login")) {
			if (view.equals("openbook") || view.equals("viewsong")) {
				String ref = "<a href=\"/chordsequence?action=";
				if (app.getUser() == null || app.getUser().isEmpty() || app.getUser().equals("guest")) {
					ref+= "login&view=login\">Sign In";
				} else {
					ref+= "logout\">Sign Out";
				}
				ref += "</a>";
				return ref;
			}
			return "";
		} else if (section.equals("addsong")) {
			if (view.equals("openbook") || view.equals("viewsong")) {
				String songId = "";
				if (song != null) {
					songId = song.getId();
				}
				return buildRef("", "addsong", "add", "", "Add");
			}
			return "";
		} else if (section.equals("return")) {
			if (view.equals("editsong")) {
				String songId = "";
				if (song != null) {
					songId = song.getId();
				}
				return buildRef("", "viewsong", "return", songId, "Edit");
			}
			return "";
		} else if (section.equals("openbook")) {
			if (view.equals("openbook") || view.equals("viewsong")) {
				return buildRef("", "openbook", "songbook", "", "Song Index");
			}
			return "";
		} else if (section.equals("export")) {
			if (view.equals("viewsong")) {
				String songId = "";
				if (song != null) {
					songId = song.getId();
				}
				return buildRef("export", "viewsharedsong", "export", songId, "Export");
			}
			return "";
		} else if (section.equals("settings")) {
			if (view.equals("editsong")) {
				String songId = "";
				if (song != null) {
					songId = song.getId();
				}
				return buildRef("", "settings", "settings", songId, "Settings");
			}
			return "";
		} else {
			if (view.equals("openbook") || view.equals("viewsong")) {
				String songId = "";
				if (song != null) {
					songId = song.getId();
				}
				String title = "Song Index";
				if (view.equalsIgnoreCase("viewsong")) {
					title = "View";
				}
				return buildRef("", section, section, songId, title);
			}
			return "";
		}
		
		return s;
	}

	@Override
	public void setApp(App theApp) {
		app = (GuitarSongsApp)theApp;
		
	}

	@Override
	public void setTemplate(Template template) {
		template.addSectionHandler(this, "songid");
		template.addSectionHandler(this, "chord");
		template.addSectionHandler(this, "pagenum");
		template.addSectionHandler(this, "title");		
		template.addSectionHandler(this, "artist");		
		template.addSectionHandler(this, "signin");		
		template.addSectionHandler(this, "loginaction");		
		template.addSectionHandler(this, "editview");		
		template.addSectionHandler(this, "openmode");		
		template.addSectionHandler(this, "newfeatures");
		template.addSectionHandler(this, "settings");
		template.addSectionHandler(this, "comment");
		template.addSectionHandler(this, "addsong");
		template.addSectionHandler(this, "edit");
		template.addSectionHandler(this, "export");
		template.addSectionHandler(this, "login");
		template.addSectionHandler(this, "return");
		template.addSectionHandler(this, "openbook");
	}

	public String render() {
		Template template = new Template();
		
		setTemplate(template);
 		
		TextFile file = new TextFile();
		file.setBuffer(template);
		
		if (!file.load("/projects/chordsequence/template/", "title", "layout")) {
			String error = getClass().getName() + ": error loading title"; 
			Log.println(error);
			return error;
		}
		
		return template.render();
	}
}

