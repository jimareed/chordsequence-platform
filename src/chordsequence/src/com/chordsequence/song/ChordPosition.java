package com.chordsequence.song;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.chordsequence.util.Log;

public class ChordPosition {
	
	private String chord = "";
	
	private String el = "0";
	private String a = "0";
	private String d = "0";
	private String g = "0";
	private String b = "0";
	private String eh = "0";
	
	private String baseFret = "0";
	
	
	public boolean isEmpty() {
		return el.equals("0") && a.equals("0") && d.equals("0") && g.equals("0") && b.equals("0") && eh.equals("0") && baseFret.equals("0");
	}
	
	public boolean equals(ChordPosition pos) {
		if (pos == null ||
			!chord.equals(pos.chord) ||
			!el.equals(pos.el) ||
			!a.equals(pos.a) ||
			!d.equals(pos.d) ||
			!g.equals(pos.g) ||
			!b.equals(pos.b) ||
			!eh.equals(pos.eh)) {
			return false;
		}
		return true;
	}
	
	public void copy(ChordPosition pos) {
		
		if (pos != null) {
			chord = pos.chord;
			
			el = pos.el;
			a = pos.a;
			d = pos.d;
			g = pos.g;
			b = pos.b;
			eh = pos.eh;
			
			baseFret = pos.baseFret;
		}
		
	}

	public ChordPosition() {
		chord = "";
	}
	
	public ChordPosition(String ch) {
		
		if (ch.startsWith("{")) {
			fromString(ch);
		} else {
			chord = ch;
		}
	}
	
	public void updateString(int string, int fret) {
		String fretStr = Integer.toString(fret);
		if (fretStr.equals("0")) {
			fretStr = "x";
		}
		
		if (string == 1) {
			if (el.equals(fretStr)) {
				el = "0";
			} else {
				el = fretStr;
			}
		}
		if (string == 2) {
			if (a.equals(fretStr)) {
				a = "0";
			} else {
				a = fretStr;
			}
		}
		if (string == 3) {
			if (d.equals(fretStr)) {
				d = "0";
			} else {
				d = fretStr;
			}
		}
		if (string == 4) {
			if (g.equals(fretStr)) {
				g = "0";
			} else {
				g = fretStr;
			}
		}
		if (string == 5) {
			if (b.equals(fretStr)) {
				b = "0";
			} else {
				b = fretStr;
			}
		}
		if (string == 6) {
			if (eh.equals(fretStr)) {
				eh = "0";
			} else {
				eh = fretStr;
			}
		}
	}
	
	public String getChord() {
		return chord;
	}
	
	public String getEl() {
		return el;
	}
	
	public String getA() {
		return a;
	}

	public String getD() {
		return d;
	}
	public String getG() {
		return g;
	}
	public String getB() {
		return b;
	}
	public String getEh() {
		return eh;
	}

	//{define: E base-fret 0 frets 0 2 2 1 0 0 fingers - 3 2 1 - -}
	
	public void fromString(String position) {

		chord = "";

		String s = "";
		
		if (position.length() > 2) {
			s = position.substring(1,position.length()-1);
		}

		try {
			
			Pattern p = Pattern.compile("define: (.+?) base-fret (.+?) frets (.+?) (.+?) (.+?) (.+?) (.+?) (.+?) fingers (.+?) (.+?) (.+?) (.+?) (.+?) (.+?)");
			Matcher matcher = p.matcher(s);
			if (matcher.matches()) {
				chord = matcher.group(1);
				baseFret = matcher.group(2);
				
				el = matcher.group(3);
				a = matcher.group(4);
				d = matcher.group(5);
				g = matcher.group(6);
				b = matcher.group(7);
				eh = matcher.group(8);
				
			} else {
				Log.println(getClass().getName() + ": error compiling ");
				return;
			}
		} catch (Exception e) {
			Log.println(getClass().getName() + ": compile error");
			return;
		}
	}
	
	public String toString() {
		return "{define: " + chord + " base-fret " + baseFret + " frets " + el + " " + a + " " + d + " " + g + " " + b + " " + eh + " fingers - - - - - -}";
	}
	
	public String toSvg() {
		
		String s = "";

		if (el.equals("1")) {
			s += "<circle cx=\"15\" cy=\"22\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (el.equals("2")) {
			s += "<circle cx=\"15\" cy=\"37\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (el.equals("3")) {
			s += "<circle cx=\"15\" cy=\"52\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (el.equals("4")) {
			s += "<circle cx=\"15\" cy=\"67\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (el.equals("x")) {
			int cxx = 1 * 15 - 5;
			s +=  "<line x1=\"" + Integer.toString(cxx) + "\" y1=\"0\" x2=\"" + Integer.toString(cxx+10) + "\" y2=\"10\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />";
			s +=  "<line x1=\"" + Integer.toString(cxx+10) + "\" y1=\"0\" x2=\"" + Integer.toString(cxx) + "\" y2=\"10\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />";
		}
		
		if (a.equals("1")) {
			s += "<circle cx=\"30\" cy=\"22\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (a.equals("2")) {
			s += "<circle cx=\"30\" cy=\"37\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (a.equals("3")) {
			s += "<circle cx=\"30\" cy=\"52\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (a.equals("4")) {
			s += "<circle cx=\"30\" cy=\"67\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (a.equals("x")) {
			int cxx = 2 * 15 - 5;
			s +=  "<line x1=\"" + Integer.toString(cxx) + "\" y1=\"0\" x2=\"" + Integer.toString(cxx+10) + "\" y2=\"10\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />";
			s +=  "<line x1=\"" + Integer.toString(cxx+10) + "\" y1=\"0\" x2=\"" + Integer.toString(cxx) + "\" y2=\"10\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />";
		}

		if (d.equals("1")) {
			s += "<circle cx=\"45\" cy=\"22\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (d.equals("2")) {
			s += "<circle cx=\"45\" cy=\"37\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (d.equals("3")) {
			s += "<circle cx=\"45\" cy=\"52\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (d.equals("4")) {
			s += "<circle cx=\"45\" cy=\"67\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (d.equals("x")) {
			int cxx = 3 * 15 - 5;
			s +=  "<line x1=\"" + Integer.toString(cxx) + "\" y1=\"0\" x2=\"" + Integer.toString(cxx+10) + "\" y2=\"10\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />";
			s +=  "<line x1=\"" + Integer.toString(cxx+10) + "\" y1=\"0\" x2=\"" + Integer.toString(cxx) + "\" y2=\"10\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />";
		}

		if (g.equals("1")) {
			s += "<circle cx=\"60\" cy=\"22\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (g.equals("2")) {
			s += "<circle cx=\"60\" cy=\"37\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (g.equals("3")) {
			s += "<circle cx=\"60\" cy=\"52\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (g.equals("4")) {
			s += "<circle cx=\"60\" cy=\"67\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (g.equals("x")) {
			int cxx = 4 * 15 - 5;
			s +=  "<line x1=\"" + Integer.toString(cxx) + "\" y1=\"0\" x2=\"" + Integer.toString(cxx+10) + "\" y2=\"10\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />";
			s +=  "<line x1=\"" + Integer.toString(cxx+10) + "\" y1=\"0\" x2=\"" + Integer.toString(cxx) + "\" y2=\"10\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />";
		}
		
		if (b.equals("1")) {
			s += "<circle cx=\"75\" cy=\"22\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (b.equals("2")) {
			s += "<circle cx=\"75\" cy=\"37\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (b.equals("3")) {
			s += "<circle cx=\"75\" cy=\"52\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (b.equals("4")) {
			s += "<circle cx=\"75\" cy=\"67\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (b.equals("x")) {
			int cxx = 5 * 15 - 5;
			s +=  "<line x1=\"" + Integer.toString(cxx) + "\" y1=\"0\" x2=\"" + Integer.toString(cxx+10) + "\" y2=\"10\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />";
			s +=  "<line x1=\"" + Integer.toString(cxx+10) + "\" y1=\"0\" x2=\"" + Integer.toString(cxx) + "\" y2=\"10\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />";
		}

		if (eh.equals("1")) {
			s += "<circle cx=\"90\" cy=\"22\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (eh.equals("2")) {
			s += "<circle cx=\"90\" cy=\"37\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (eh.equals("3")) {
			s += "<circle cx=\"90\" cy=\"52\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (eh.equals("4")) {
			s += "<circle cx=\"90\" cy=\"67\" r=\"4\" stroke=\"black\" stroke-width=\"3\" fill=\"black\" />";
		} 
		if (eh.equals("x")) {
			int cxx = 6 * 15 - 5;
			s +=  "<line x1=\"" + Integer.toString(cxx) + "\" y1=\"0\" x2=\"" + Integer.toString(cxx+10) + "\" y2=\"10\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />";
			s +=  "<line x1=\"" + Integer.toString(cxx+10) + "\" y1=\"0\" x2=\"" + Integer.toString(cxx) + "\" y2=\"10\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />";
		}

		if (!baseFret.equals("0")) {
			s += "<text x = \"100\" y = \"27\" font-size = \"15\" font-family=\"Courier\" font-weight=\"bold\">" + baseFret + "</text>\n";
		}
		
		return s;
	}

	
	public String getBaseFret() {
		return baseFret;
	}
	
	public void nextBaseFret() {
		if (!baseFret.equals("30")) {
			Integer i = Integer.parseInt(baseFret);
			i++;
			baseFret = i.toString();
		}
	}

	public void previousBaseFret() {
		if (!baseFret.equals("0")) {
			Integer i = Integer.parseInt(baseFret);
			i--;
			baseFret = i.toString();
		}
	}
	
	private boolean isPressed(int string, int fret) {
		String theFret = Integer.toString(fret);
		
		if (theFret.equals("0")) {
			theFret = "x";
		}
		
		if (string == 1) {
			return el.equals(theFret);
		}
		if (string == 2) {
			return a.equals(theFret);
		}
		if (string == 3) {
			return d.equals(theFret);
		}
		if (string == 4) {
			return g.equals(theFret);
		}
		if (string == 5) {
			return b.equals(theFret);
		}
		if (string == 6) {
			return eh.equals(theFret);
		}
		
		return false;
	}
	
	private String drawCircle(boolean visible, int string, int fret) {
		String s= "";

		int cx = string * 30;
		int cy = 14 + (fret-1)*30 + 30;
		
		s += "<circle cx=\"";
		s += Integer.toString(cx) + "\" cy=\"";
		s += Integer.toString(cy) + "\" r=\"8\" stroke=\"black\" stroke-width=\"6\" fill=\"black\"";
		if (!visible) {
			s += " opacity=\"0.0\" ";
		}
		s += "onclick=\"selectchordstring('" + encode(chord) + "','" + string + "','" + fret + "')\" ";
		s += "/>";
		
		return s;

	}

	private String drawCircleBig(boolean visible, int string, int fret) {
		String s= "";

		int cx = string * 45;
		int cy = 21 + (fret-1)*45 + 45;
		
		s += "<circle cx=\"";
		s += Integer.toString(cx) + "\" cy=\"";
		s += Integer.toString(cy) + "\" r=\"12\" stroke=\"black\" stroke-width=\"9\" fill=\"black\"";
		if (!visible) {
			s += " opacity=\"0.0\" ";
		}
		s += "onclick=\"selectchordstring('" + encode(chord) + "','" + string + "','" + fret + "')\" ";
		s += "/>";
		
		return s;

	}
	
	private String drawX(boolean visible, int string, int fret) {


		String s= "";

		int cx = string * 30;
		int cxx = string * 30 - 10;
		int cy = 14 + (fret-1)*30 + 30;
		
		s += "<circle cx=\"";
		s += Integer.toString(cx) + "\" cy=\"";
		s += Integer.toString(cy) + "\" r=\"8\" stroke=\"black\" stroke-width=\"6\" fill=\"black\"";
		s += " opacity=\"0.0\" ";
		s += "onclick=\"selectchordstring('" + encode(chord) + "','" + string + "','" + fret + "')\" ";
		s += "/>";
		
		if (visible) {
			s +=  "<line x1=\"" + Integer.toString(cxx) + "\" y1=\"0\" x2=\"" + Integer.toString(cxx+20) + "\" y2=\"20\" style=\"stroke:rgb(0,0,0);stroke-width:4\" />";
			s +=  "<line x1=\"" + Integer.toString(cxx+20) + "\" y1=\"0\" x2=\"" + Integer.toString(cxx) + "\" y2=\"20\" style=\"stroke:rgb(0,0,0);stroke-width:4\" />";
		}
 		return s;
	
	}

	private String drawXBig(boolean visible, int string, int fret) {


		String s= "";

		int cx = string * 45;
		int cxx = string * 45 - 15;
		int cy = 21 + (fret-1)*45 + 45;
		
		s += "<circle cx=\"";
		s += Integer.toString(cx) + "\" cy=\"";
		s += Integer.toString(cy) + "\" r=\"12\" stroke=\"black\" stroke-width=\"9\" fill=\"black\"";
		s += " opacity=\"0.0\" ";
		s += "onclick=\"selectchordstring('" + encode(chord) + "','" + string + "','" + fret + "')\" ";
		s += "/>";
		
		if (visible) {
			s +=  "<line x1=\"" + Integer.toString(cxx) + "\" y1=\"0\" x2=\"" + Integer.toString(cxx+30) + "\" y2=\"30\" style=\"stroke:rgb(0,0,0);stroke-width:6\" />";
			s +=  "<line x1=\"" + Integer.toString(cxx+30) + "\" y1=\"0\" x2=\"" + Integer.toString(cxx) + "\" y2=\"30\" style=\"stroke:rgb(0,0,0);stroke-width:6\" />";
		}
 		return s;
	
	}
	
	private String drawSmallCircle(int string, int fret) {
		
		int yOffset = 0;
		
		String s= "";

		int cx = (int)(string * 7.5);
		int cy = (int)(3.5 + (fret-1)*7.5 + 7.5) + yOffset;
		
		s += "<circle cx=\"";
		s += Integer.toString(cx) + "\" cy=\"";
		s += Integer.toString(cy) + "\" r=\"2\" stroke=\"black\" stroke-width=\"1.5\" fill=\"black\"";
		s += "/>";
		
		return s;

	}

	private String drawSmallX(int string, int fret) {

		int yOffset = 0;
		
		String s= "";

		int cx = (int)((string * 7.5) - 2.5);
		
		int y1 = 0 + yOffset;
		int y2 = 5 + yOffset;
		
		s +=  "<line x1=\"" + Integer.toString(cx) + "\" y1=\"" + Integer.toString(y1) + "\" x2=\"" + Integer.toString(cx+5) + "\" y2=\"" + Integer.toString(y2) + "\" style=\"stroke:rgb(0,0,0);stroke-width:1\" />";
		s +=  "<line x1=\"" + Integer.toString(cx+5) + "\" y1=\""+ Integer.toString(y1) + "\" x2=\"" + Integer.toString(cx) + "\" y2=\"" + Integer.toString(y2) + "\" style=\"stroke:rgb(0,0,0);stroke-width:1\" />";
 		return s;
	
	}

	private String drawSmallCircle(int string, int fret, int xOffset, int yOffset) {
		
		String s= "";

		int cx = (int)(string * 7.5);
		int cy = (int)(3.5 + (fret-1)*7.5 + 7.5) + yOffset;
		
		s += "<circle cx=\"";
		s += Integer.toString(cx+xOffset) + "\" cy=\"";
		s += Integer.toString(cy) + "\" r=\"2\" stroke=\"black\" stroke-width=\"1.5\" fill=\"black\"";
		s += "/>";
		
		return s;

	}

	private String drawSmallX(int string, int fret, int xOffset, int yOffset) {

		
		String s= "";

		int cx = (int)((string * 7.5) - 2.5);
		
		int y1 = 0 + yOffset;
		int y2 = 5 + yOffset;
		
		s +=  "<line x1=\"" + Integer.toString(cx+xOffset) + "\" y1=\"" + Integer.toString(y1) + "\" x2=\"" + Integer.toString(cx+5+xOffset) + "\" y2=\"" + Integer.toString(y2) + "\" style=\"stroke:rgb(0,0,0);stroke-width:1\" />";
		s +=  "<line x1=\"" + Integer.toString(cx+5+xOffset) + "\" y1=\""+ Integer.toString(y1) + "\" x2=\"" + Integer.toString(cx+xOffset) + "\" y2=\"" + Integer.toString(y2) + "\" style=\"stroke:rgb(0,0,0);stroke-width:1\" />";
 		return s;
	
	}
	
	private String encode(String s) {
		String e =  s.replaceAll("#", "%23");
		return e;		
	}
	
	public String toSvgZoom() {
		
		String s = "";

		for (int string = 1; string <= 6; string++) {
			for (int fret = 1; fret <= 4; fret++) {
				s += drawCircle(isPressed(string, fret), string, fret);
			}
			s += drawX(isPressed(string, 0), string, 0);			
		}
		
		s += "<text x = \"200\" y = \"54\" font-size = \"30\" font-family=\"Courier\" font-weight=\"bold\">" + baseFret + "</text>\n";

		return s;
	}

	public String toSvgZoomMore() {
		
		String s = "";

		for (int string = 1; string <= 6; string++) {
			for (int fret = 1; fret <= 4; fret++) {
				s += drawCircleBig(isPressed(string, fret), string, fret);
			}
			s += drawXBig(isPressed(string, 0), string, 0);			
		}
		
		s += "<text x = \"300\" y = \"81\" font-size = \"30\" font-family=\"Courier\" font-weight=\"bold\">" + baseFret + "</text>\n";

		return s;
	}
	
	public String toSvgSmall() {
		
		int yOffset = 0;
		
		String s = "";

		for (int string = 1; string <= 6; string++) {
			for (int fret = 1; fret <= 4; fret++) {
				if (isPressed(string, fret)) {
					s += drawSmallCircle(string, fret);
				}
			}
			if (isPressed(string, 0)) {
				s += drawSmallX(string, 0);			
			}
		}
		
		int y = 13 + yOffset;

		if (!baseFret.equals("0")) {
			s += "<text x = \"50\" y = \"" + Integer.toString(y) + "\" font-size = \"10\" font-family=\"Courier\" font-weight=\"bold\">" + baseFret + "</text>\n";
		}
		

		return s;
	}

	public String toSvgSmallForImage(int chordNum) {
		
		int xOffset = chordNum % 6 * 60 + 30;
		int yOffset = chordNum / 6 * 60 + 90;

		String s = "";

		for (int string = 1; string <= 6; string++) {
			for (int fret = 1; fret <= 4; fret++) {
				if (isPressed(string, fret)) {
					s += drawSmallCircle(string, fret, xOffset, yOffset);
				}
			}
			if (isPressed(string, 0)) {
				s += drawSmallX(string, 0, xOffset, yOffset);			
			}
		}
		
		int y = 13 + yOffset;

		if (!baseFret.equals("0")) {
			s += "<text x = \"" + Integer.toString(50+xOffset) + "\" y = \"" + Integer.toString(y) + "\" font-size = \"10\" font-family=\"Courier\" font-weight=\"bold\">" + baseFret + "</text>\n";
		}
		

		return s;
	}
	
}
