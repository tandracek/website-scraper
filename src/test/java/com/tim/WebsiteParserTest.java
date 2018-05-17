package com.tim;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

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
        String stripped = WebsiteParser.strip(html, "p", element -> {
            return element.children().isEmpty() && element.hasText();
        });
        String expected = "paragraph 1\nparagraph 2\nparagraph 3";
        assertEquals(expected, stripped);
    }

    @Test
    public void getLinks() throws Exception {
        String html = FileUtils.readFileToString(new File("src/test/resources/links.html"), "UTF8");
        List<String> links = WebsiteParser.getLinksSelector(html, "base.", "table td a");
        assertEquals(3, links.size());
        assertEquals("base.first", links.get(0));
    }
}