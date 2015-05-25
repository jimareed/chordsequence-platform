package com.chordsequence.server;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.*;
import javax.servlet.*;

import com.chordsequence.app.GuitarSongsApp;
import com.chordsequence.song.Library;
import com.chordsequence.song.SetList;
import com.chordsequence.song.Song;
import com.chordsequence.util.Log;

public class GuitarSongsServlet extends HttpServlet implements Server {

	HashMap<String, GuitarSongsApp> apps = new HashMap<String, GuitarSongsApp>();

	private Library sharedLib = new Library();
	
	private static final long serialVersionUID = 1L;
	
    public void init(ServletConfig config) throws ServletException {
    	sharedLib.load("share");
    	super.init(config);
    }
    
	public void doGet (HttpServletRequest req,
	                     HttpServletResponse res)
	    throws ServletException, IOException {
	
		HttpSession session = req.getSession();
		
		if (session == null) {
		    PrintWriter out = res.getWriter();
			
		    out.println(getClass().getName() + ": error, unable to get session id");
		
		    out.close();
		}
		
		String sessionId = session.getId();
		
		GuitarSongsApp app = apps.get(sessionId);

		if (app != null && app.sessionExpired()) {
			apps.remove(sessionId);
			app = null;
		}
		
		if (app == null) {
			app = new GuitarSongsApp(this);

			app.setSharedLibrary(sharedLib);
			app.setSessionId(sessionId);
			apps.put(sessionId, app);
		}
		
		String view = req.getParameter("view");  
		String action = req.getParameter("action");  
		String format = req.getParameter("format");

		app.setParm("song" , req.getParameter("song"));
		app.setParm("comments" , req.getParameter("comments"));
		app.setParm("songid" , req.getParameter("songid"));
		app.setParm("chord" , req.getParameter("chord"));
		app.setParm("fromchord" , req.getParameter("fromchord"));
		app.setParm("x" , req.getParameter("x"));
		app.setParm("y" , req.getParameter("y"));
		app.setParm("column" , req.getParameter("column"));
		app.setParm("line" , req.getParameter("line"));
		app.setParm("direction" , req.getParameter("direction"));
		app.setParm("string" , req.getParameter("string"));
		app.setParm("fret" , req.getParameter("fret"));
		app.setParm("user" , req.getParameter("user"));
		app.setParm("password" , req.getParameter("password"));
		app.setParm("pagenum" , req.getParameter("pagenum"));
		app.setParm("linesperpage" , req.getParameter("linesperpage"));
		app.setParm("openmode" , req.getParameter("openmode"));
		app.setParm("selected-file" , req.getParameter("selected-file"));

		String uri = req.getRequestURI();
		
		if (uri != null && uri.contains("/song/")) {
			String id = uri.substring(uri.indexOf("/song/") + 6);
			if (id.contains("?")) {
				id = id.substring(0, id.indexOf("?"));
			}
			if (id.contains("/")) {
				id = id.substring(0, id.indexOf("/"));
			}
			app.setParm("sharedsongid", id);
			view = "viewsharedsong";
			app.setParm("view", view);
			
			Song sharedSong = app.getSharedSong(id);
			
			if (sharedSong != null) {
				Song song = new Song(app.getDoc());
				song.fromString(sharedSong.toString());
				
				Song oldSong = app.getDoc().findSong(sharedSong.getTitle());
				
				if (oldSong != null && oldSong.getArtist().equals(sharedSong.getArtist())) {
					app.getDoc().deleteSong(oldSong.getId());
				}
				
				String songId = app.getDoc().addSong(song);
				app.setParm("songid", songId);
			}
		
		}

		if (uri != null && uri.contains("/set/")) {
			String id = uri.substring(uri.indexOf("/set/") + 5);
			if (id.contains("?")) {
				id = id.substring(0, id.indexOf("?"));
			}
			if (id.contains("/")) {
				id = id.substring(0, id.indexOf("/"));
			}
			
			SetList setList = app.getSharedSetList(id);
			
			String firstSong = null;
			if (setList == null) {
				Log.println(getClass().getName() + ": error invalid setlist id " + id);
			} else {
				for (int i = 0; i < setList.size(); i++) {
					Song song = app.getSharedSong(setList.get(i));
					if (song == null) {
						Log.println(getClass().getName() + ": error getting shared song " + setList.get(i));
					} else {
						String songId = app.getDoc().copy(song);
						if (songId == null) {
							Log.println(getClass().getName() + ": error copying song " + setList.get(i));
						} else {
							if (firstSong == null) {
								firstSong = songId;
							}
						}
					}
				}
				if (firstSong != null) {
					view = "openbook";
					app.setParm("view", view);
				}
			}
			
		}
		
		if (uri != null && uri.contains("/image/")) {
			String id = uri.substring(uri.indexOf("/image/") + 7);
			if (id.contains("?")) {
				id = id.substring(0, id.indexOf("?"));
			}
			if (id.contains("/")) {
				id = id.substring(0, id.indexOf("/"));
			}
			app.setParm("sharedsongid", id);
			view = "viewsharedsong";
			action = "login";
			format = "image";
			app.setParm("action", action);
			app.setParm("view", view);
			app.setParm("user", "share");
		}
		
		String width = req.getParameter("width");
		
		if (width == null || width.isEmpty()) {
			width = "980";
		}
		app.setParm("width", width);
	
	
	    if (format != null && format.equals("image")) {
	    	res.setContentType("image/png");
	    	
	    	ServletOutputStream out = res.getOutputStream();

	    	app.renderImage(action, view, out);

			out.close();
	    } else {
	    	res.setContentType("text/html");

	    	PrintWriter out = res.getWriter();
		    out.println(app.render(action, view));
		    out.close();
	    }
	
	    
	    if (app.getDoc().hasChanged()) {
	    	if (!app.getDoc().save()) {
	    		Log.println(getClass().getName() + ": error saving library " + app.getDoc().getName());
	    	}
	    }
	    
		Iterator<String> it = apps.keySet().iterator();

		while(it.hasNext()) {
			String s = it.next();
			GuitarSongsApp a = apps.get(s);
			if (a != null) {
				// expire sessions after 24 hours
				if ((System.currentTimeMillis() - a.getLastRenderTime()) > 86400000) {
					apps.remove(s);
					a.expireSession();
				}
			}
		}

	  }


	@Override
	public HashMap<String, GuitarSongsApp> getSessions() {
		return apps;
	}
}
