package com.chordsequence.song;

public class ChordChart {

	public String render(ChordPosition position, String chord) {
		
		String result = "<table><tr><td align=\"center\"><b>" + chord + "</b></td></tr>";

		result += "<tr><td><p id=\"" + encode(chord) + "\" draggable=\"true\" ondragstart=\"drag(event)\" ><svg width=\"110\" height=\"80\">";

		result += 
		  "<line x1=\"15\" y1=\"13\" x2=\"90\" y2=\"13\" style=\"stroke:rgb(0,0,0);stroke-width:6\" />" +
		  "<line x1=\"15\" y1=\"30\" x2=\"90\" y2=\"30\" style=\"stroke:rgb(0,0,0);stroke-width:1\" />" +
		  "<line x1=\"15\" y1=\"45\" x2=\"90\" y2=\"45\" style=\"stroke:rgb(0,0,0);stroke-width:1\" />" +
		  "<line x1=\"15\" y1=\"60\" x2=\"90\" y2=\"60\" style=\"stroke:rgb(0,0,0);stroke-width:1\" />" +
		  "<line x1=\"15\" y1=\"75\" x2=\"90\" y2=\"75\" style=\"stroke:rgb(0,0,0);stroke-width:1\" />" +
		  "<line x1=\"15\" y1=\"10\" x2=\"15\" y2=\"75\" style=\"stroke:rgb(0,0,0);stroke-width:1\" />" +
		  "<line x1=\"30\" y1=\"10\" x2=\"30\" y2=\"75\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"45\" y1=\"10\" x2=\"45\" y2=\"75\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"60\" y1=\"10\" x2=\"60\" y2=\"75\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"75\" y1=\"10\" x2=\"75\" y2=\"75\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"90\" y1=\"10\" x2=\"90\" y2=\"75\" style=\"stroke:rgb(0,0,0);stroke-width:1\" />";
		
		result += "<rect x=\"0\" y=\"0\" width=\"110\" height=\"80\" style=\"fill:rgb(0,0,0);stroke-width:0;stroke:rgb(0,0,0) stroke-opacity=\"0.0\" fill-opacity=\"0.0\" onclick=\"selectchord('" + encode(chord) + "')\"/>";

		if (position != null) {
			result += position.toSvg();
		}
		
		result += "</p></svg></td></tr>";
		
		result += "</table>";
		
		return result;
	}

	private String encode(String s) {
		String e =  s.replaceAll("#", "%23");
		return e;		
	}

	public String renderZoom(ChordPosition position, String chord) {
		
		String result = "<table><tr><td align=\"center\"><b>" + chord + "</b></td></tr>";

		result += "<tr><td><p id=\"" + encode(chord) + "\" draggable=\"true\" ondragstart=\"drag(event)\" onclick=\"selectchord('" + encode(chord) + "')\"><svg width=\"220\" height=\"230\">";

		result += 
		  "<line x1=\"30\" y1=\"25\" x2=\"180\" y2=\"25\" style=\"stroke:rgb(0,0,0);stroke-width:12\" />" +
		  "<line x1=\"30\" y1=\"60\" x2=\"180\" y2=\"60\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />" +
		  "<line x1=\"30\" y1=\"90\" x2=\"180\" y2=\"90\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />" +
		  "<line x1=\"30\" y1=\"120\" x2=\"180\" y2=\"120\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />" +
		  "<line x1=\"30\" y1=\"150\" x2=\"180\" y2=\"150\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />" +
		  "<line x1=\"30\" y1=\"20\" x2=\"30\" y2=\"150\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />" +
		  "<line x1=\"60\" y1=\"20\" x2=\"60\" y2=\"150\" style=\"stroke:rgb(0,0,0);stroke-width:1\" />" +
		  "<line x1=\"90\" y1=\"20\" x2=\"90\" y2=\"150\" style=\"stroke:rgb(0,0,0);stroke-width:1\" />" +
		  "<line x1=\"120\" y1=\"20\" x2=\"120\" y2=\"150\" style=\"stroke:rgb(0,0,0);stroke-width:1\" />" +
		  "<line x1=\"150\" y1=\"20\" x2=\"150\" y2=\"150\" style=\"stroke:rgb(0,0,0);stroke-width:1\" />" +
		  "<line x1=\"180\" y1=\"20\" x2=\"180\" y2=\"150\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />";
		

		if (position != null) {
			result += position.toSvgZoom();
		}

		
		result += "</svg></p></td></tr>";
		
		result += "</table>";
		
		return result;
	}
	
	public String renderZoomMore(ChordPosition position, String chord) {
		
		String result = "<table>";

		result += "<tr><td><p id=\"" + encode(chord) + "\" draggable=\"true\" ondragstart=\"drag(event)\" onclick=\"selectchord('" + encode(chord) + "')\"><svg width=\"330\" height=\"345\">";

		result += 
		  "<line x1=\"45\" y1=\"37\" x2=\"270\" y2=\"37\" style=\"stroke:rgb(0,0,0);stroke-width:18\" />" +
		  "<line x1=\"45\" y1=\"90\" x2=\"270\" y2=\"90\" style=\"stroke:rgb(0,0,0);stroke-width:4\" />" +
		  "<line x1=\"45\" y1=\"135\" x2=\"270\" y2=\"135\" style=\"stroke:rgb(0,0,0);stroke-width:4\" />" +
		  "<line x1=\"45\" y1=\"180\" x2=\"270\" y2=\"180\" style=\"stroke:rgb(0,0,0);stroke-width:4\" />" +
		  "<line x1=\"45\" y1=\"225\" x2=\"270\" y2=\"225\" style=\"stroke:rgb(0,0,0);stroke-width:4\" />" +
		  "<line x1=\"45\" y1=\"30\" x2=\"45\" y2=\"225\" style=\"stroke:rgb(0,0,0);stroke-width:4\" />" +
		  "<line x1=\"90\" y1=\"30\" x2=\"90\" y2=\"225\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />" +
		  "<line x1=\"135\" y1=\"30\" x2=\"135\" y2=\"225\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />" +
		  "<line x1=\"180\" y1=\"30\" x2=\"180\" y2=\"225\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />" +
		  "<line x1=\"225\" y1=\"30\" x2=\"225\" y2=\"225\" style=\"stroke:rgb(0,0,0);stroke-width:2\" />" +
		  "<line x1=\"270\" y1=\"30\" x2=\"270\" y2=\"225\" style=\"stroke:rgb(0,0,0);stroke-width:4\" />";
		

		if (position != null) {
			result += position.toSvgZoomMore();
		}

		
		result += "</svg></p></td></tr>";
		
		result += "</table>";
		
		return result;
	}

	public String renderSmall(ChordPosition position, String chord, String format, boolean highlight) {
		
		int yOffset = 0;
		
		String result = "";
		
		if (format.equals("html")) {
			result += "<table><tr><td>";
			result += "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"55\" height=\"20\">";
		}
		

		int xText = 22 - (chord.length()-1) * 6;
		
		if (xText < 0) {
			xText = 0;
		}

		if (format.equals("html")) {
			result += "<rect x=\"0\" y=\"" + Integer.toString(0) + "\" width=\"55\" height=\"20\" style=\"fill:rgb(0,0,0);stroke-width:0;stroke:rgb(0,0,0) stroke-opacity=\"0.0\" fill-opacity=\"0.0\" onclick=\"selectchord('" + encode(chord) + "')\"/>";
			
			result += "<text x = \"" + Integer.toString(xText) + "\" y = \"16\" font-size = \"16\" font-weight=\"bold\" onclick=\"selectchord('" + encode(chord) + "')\">" + chord + "</text>\n";

			result += "</svg>";

			result += "<div id=\"" + encode(chord) + "\" style=\"height: 50%\" draggable=\"true\" ondragstart=\"drag(event)\" >";
		}

		result += "<svg width=\"55\" height=\"" + Integer.toString(40+yOffset) + "\">";

		String rectStrokeOpacity = "0.0";
		if (highlight) {
			rectStrokeOpacity = "1.0";
		}
		
		result += "<rect x=\"0\" y=\"" + Integer.toString(0) + "\" width=\"55\" height=\"" + Integer.toString(40+yOffset) + "\" style=\"fill:rgb(0,0,0);stroke-width:2;stroke:rgb(255,0,0)\" stroke-opacity=\""+ rectStrokeOpacity +"\" fill-opacity=\"0.0\" onclick=\"selectchord('" + encode(chord) + "')\"/>";
		
		result += 
		  "<line x1=\"7\" y1=\"" + Integer.toString(6+yOffset) + "\" x2=\"45\" y2=\"" + Integer.toString(6+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:3\" />" +
		  "<line x1=\"7\" y1=\"" + Integer.toString(15+yOffset) + "\" x2=\"45\" y2=\"" + Integer.toString(15+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"7\" y1=\"" + Integer.toString(22+yOffset) + "\" x2=\"45\" y2=\"" + Integer.toString(22+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"7\" y1=\"" + Integer.toString(30+yOffset) + "\" x2=\"45\" y2=\"" + Integer.toString(30+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"7\" y1=\"" + Integer.toString(37+yOffset) + "\" x2=\"45\" y2=\"" + Integer.toString(37+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"7\" y1=\"" + Integer.toString(5+yOffset) + "\" x2=\"7\" y2=\"" + Integer.toString(37+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"15\" y1=\"" + Integer.toString(5+yOffset) + "\" x2=\"15\" y2=\"" + Integer.toString(37+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.25\" />" +
		  "<line x1=\"22\" y1=\"" + Integer.toString(5+yOffset) + "\" x2=\"22\" y2=\"" + Integer.toString(37+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.25\" />" +
		  "<line x1=\"30\" y1=\"" + Integer.toString(5+yOffset) + "\" x2=\"30\" y2=\"" + Integer.toString(37+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.25\" />" +
		  "<line x1=\"37\" y1=\"" + Integer.toString(5+yOffset) + "\" x2=\"37\" y2=\"" + Integer.toString(37+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.25\" />" +
		  "<line x1=\"45\" y1=\"" + Integer.toString(5+yOffset) + "\" x2=\"45\" y2=\"" + Integer.toString(37+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />";

		if (position != null) {
			result += position.toSvgSmall();
		}

		result += "</svg>";

		if (format.equals("html")) {
			result += "</div></td></tr></table>";
		}		

		return result;
	}

	public String renderSmallForImage(ChordPosition position, String chord, int chordNum) {
		
		int xOffset = chordNum % 6 * 60 + 30;
		int yOffset = chordNum / 6 * 60 + 90;
		
		String result = "";
		
		int xText = 22 - (chord.length()-1) * 6;
		
		if (xText < 0) {
			xText = 0;
		}

		result += "<text x = \"" + Integer.toString(7+xOffset) + "\" y = \"" + Integer.toString(yOffset-2) + "\" font-size = \"12\" font-weight=\"bold\">" + chord + "</text>";
		

		result += 
		  "<line x1=\"" + Integer.toString(7+xOffset) + "\" y1=\"" + Integer.toString(6+yOffset) + "\" x2=\"" + Integer.toString(45+xOffset) + "\" y2=\"" + Integer.toString(6+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:3\" />" +
		  "<line x1=\"" + Integer.toString(7+xOffset) + "\" y1=\"" + Integer.toString(15+yOffset) + "\" x2=\"" + Integer.toString(45+xOffset) + "\" y2=\"" + Integer.toString(15+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"" + Integer.toString(7+xOffset) + "\" y1=\"" + Integer.toString(22+yOffset) + "\" x2=\"" + Integer.toString(45+xOffset) + "\" y2=\"" + Integer.toString(22+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"" + Integer.toString(7+xOffset) + "\" y1=\"" + Integer.toString(30+yOffset) + "\" x2=\"" + Integer.toString(45+xOffset) + "\" y2=\"" + Integer.toString(30+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"" + Integer.toString(7+xOffset) + "\" y1=\"" + Integer.toString(37+yOffset) + "\" x2=\"" + Integer.toString(45+xOffset) + "\" y2=\"" + Integer.toString(37+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"" + Integer.toString(7+xOffset) + "\" y1=\"" + Integer.toString(5+yOffset) + "\" x2=\"" + Integer.toString(7+xOffset) + "\" y2=\"" + Integer.toString(37+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />" +
		  "<line x1=\"" + Integer.toString(15+xOffset) + "\" y1=\"" + Integer.toString(5+yOffset) + "\" x2=\"" + Integer.toString(15+xOffset) + "\" y2=\"" + Integer.toString(37+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.25\" />" +
		  "<line x1=\"" + Integer.toString(22+xOffset) + "\" y1=\"" + Integer.toString(5+yOffset) + "\" x2=\"" + Integer.toString(22+xOffset) + "\" y2=\"" + Integer.toString(37+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.25\" />" +
		  "<line x1=\"" + Integer.toString(30+xOffset) + "\" y1=\"" + Integer.toString(5+yOffset) + "\" x2=\"" + Integer.toString(30+xOffset) + "\" y2=\"" + Integer.toString(37+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.25\" />" +
		  "<line x1=\"" + Integer.toString(37+xOffset) + "\" y1=\"" + Integer.toString(5+yOffset) + "\" x2=\"" + Integer.toString(37+xOffset) + "\" y2=\"" + Integer.toString(37+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.25\" />" +
		  "<line x1=\"" + Integer.toString(45+xOffset) + "\" y1=\"" + Integer.toString(5+yOffset) + "\" x2=\"" + Integer.toString(45+xOffset) + "\" y2=\"" + Integer.toString(37+yOffset) + "\" style=\"stroke:rgb(0,0,0);stroke-width:.5\" />";

		if (position != null) {
			result += position.toSvgSmallForImage(chordNum);
		}

		return result;
	}
	
}
