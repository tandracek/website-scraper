package com.tim;

import org.junit.Test;

import org.jsoup.nodes.Document;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;

import static org.junit.Assert.assertEquals;

public class WebsiteParserTest {

    @Test
    public void strip() throws Exception {
        String html = FileUtils.readFileToString(new File("src/test/resources/strip.html"), "UTF8");
        Document document = Jsoup.parse(html);
        String stripped = WebsiteParser.strip(document, "p", element -> {
            return element.children().isEmpty() && element.hasText();
        });
        String expected = "paragraph 1\nparagraph 2\nparagraph 3";
        assertEquals(expected, stripped);
    }

    @Test
    public void getLinks() throws Exception {
        File html = new File("src/test/resources/links.html");
        Document doc = Jsoup.parse(html, "UTF-8");
        List<String> links = WebsiteParser.getLinksByElement(doc, "table");
        assertEquals(3, links.size());
    }

    //TODO finish this test
    @Test
    public void breaks() throws Exception { 
        File html = new File("src/test/resources/breaks.html");
        Document doc = Jsoup.parse(html, "UTF-8");
        String stripped = WebsiteParser.strip(doc, "p", element -> {
            return element.children().isEmpty() && element.hasText();
        });
        System.out.println(stripped);
        String expected = "line1\nline2\nline3\nline4";
        assertEquals(expected, stripped);
    }
}