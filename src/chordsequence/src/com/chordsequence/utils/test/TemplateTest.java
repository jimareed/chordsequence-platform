package com.chordsequence.utils.test;

import org.junit.* ;

import com.chordsequence.app.ViewOpenBook;
import com.chordsequence.util.Template;
import com.chordsequence.util.TemplateSectionHandler;
import com.chordsequence.util.TextFile;

import static org.junit.Assert.* ;

public class TemplateTest {

	class Handler1 implements TemplateSectionHandler {
		@Override
		public String renderSection(String section) {
			return "abcd";
		}			
	}
	
	class Handler2 implements TemplateSectionHandler {
		@Override
		public String renderSection(String section) {
			return "efgh";
		}			
	}

	class Handler3 implements TemplateSectionHandler {
		@Override
		public String renderSection(String section) {
			if (section.equals("a")) {
				return "aaa";
			}
			if (section.equals("b")) {
				return "bbb";
			}
			return "";
		}			
	}
	
	@Test
	public void test_basic() {
		Template template = new Template();
		template.addLine("hello");
		assertTrue(template.render().contains("hello")) ;
	}

	@Test
	public void test_multiLine() {
		Template template = new Template();
		template.addLine("hello");
		template.addLine("there");
		assertTrue(template.render().contains("hello") && template.render().contains("there")) ;
	}

	@Test
	public void test_basicHandler() {

		Template template = new Template();
		Handler1 handler = new Handler1();
		
		template.addSectionHandler(handler, "body");
		
		template.addLine("hello");
		template.addLine("there");
		template.addLine("{$body}");
		
		assertTrue(template.render().contains("hello")); 
		assertTrue(template.render().contains("there")); 
		assertTrue(template.render().contains("abcd")); 
	}

	@Test
	public void test_twoHandlers() {

		Template template = new Template();
		Handler1 h1 = new Handler1();
		Handler2 h2 = new Handler2();
		
		template.addSectionHandler(h1, "h1");
		template.addSectionHandler(h2, "h2");
		
		template.addLine("hello");
		template.addLine("{$h1}");
		template.addLine("{$h2}");
		
		assertTrue(template.render().contains("hello"));
		assertTrue(template.render().contains("abcd"));
		assertTrue(template.render().contains("efgh"));
	}
	
	@Test
	public void test_file() {
		Template template = new Template();

		template.addSectionHandler(new ViewOpenBook(), "main");
 		
		
		Handler1 h1 = new Handler1();
		Handler2 h2 = new Handler2();
		
		template.addSectionHandler(h1, "h1");
		template.addSectionHandler(h2, "h2");
		
		TextFile file = new TextFile();
		file.setBuffer(template);
		file.fromString("hello\n{$h1}\n{$h2}\n");
		
		assertTrue(template.render().contains("hello"));
		assertTrue(template.render().contains("abcd"));
		assertTrue(template.render().contains("efgh"));
	}

	@Test
	public void test_multisection() {
		Template template = new Template();
		Handler3 h = new Handler3();
		
		template.addSectionHandler(h, "a");
		template.addSectionHandler(h, "b");
		
		template.addLine("1{$a}2{$b}3");
		assertTrue(template.render().equals("1aaa2bbb3\n"));
	}

}
