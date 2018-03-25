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
    public void baseUrl() {
        String url = "http://wwww.something.com/whatever/sure?blah=eh";
        String baseUrl = WebsiteParser.getBaseUrl(url);
        assertEquals("http://wwww.something.com", baseUrl);
    }

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
        List<String> links = WebsiteParser.getLinksSelector(doc, "base.", "table td a");
        assertEquals(3, links.size());
        assertEquals("base.first", links.get(0));
    }
}