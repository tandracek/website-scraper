package com.tim;

import org.junit.Test;

import org.jsoup.nodes.Document;

import java.io.File;

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
}