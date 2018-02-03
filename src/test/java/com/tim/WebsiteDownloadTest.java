package com.tim;

import java.util.List;
import java.util.Arrays;
import java.util.Date;
import java.io.File;

import org.jsoup.nodes.Document;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.junit.After;
import org.junit.Test;

import junitx.framework.FileAssert;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class WebsiteDownloadTest {
    public static final String path = "src/test/resources";

    @After
    public void teardown() throws Exception {
        FileUtils.cleanDirectory(new File(path + "/out"));
    }

    @Test
    public void getLinks() throws Exception {
        File html = new File("src/test/resources/links.html");
        Document doc = Jsoup.parse(html, "UTF-8");
        List<String> links = WebsiteDownload.getLinksByElement(doc, "table");
        assertEquals(3, links.size());
    }

    @Test
    public void writeToFile() throws Exception {
        String path = "src/test/resources/out/actual.html";
        File html = new File("src/test/resources/links.html");
        Document doc = Jsoup.parse(html, "UTF-8");
        WebsiteDownload.write(doc, path);

        File actual = new File(path);
        FileAssert.assertEquals(html, actual);
    }

    @Test
    public void downloadAll() {
        String path = "src/test/resources/out";
        List<String> links = Arrays.asList("http://www.test.com/link1.htm", 
                                           "http://www.something.com/foo/link2.html",
                                           "http://www.another.com/bar/foo/link3.html");
        WebsiteDownload download = new WebsiteDownload(new StaticConnection());
        download.downloadAll(100, links, path);
        File link1 = new File(path + "/link1");
        assertTrue(link1.exists());
        File link2 = new File(path + "/link2");
        assertTrue(link2.exists());
        File link3 = new File(path + "/link3");
        assertTrue(link3.exists());
    }

    @Test
    public void downloadWithInterval() {
        String path = "src/test/resources/out";
        List<String> links = Arrays.asList("http://www.test.com/link1.htm", 
                                           "http://www.something.com/foo/link2.html",
                                           "http://www.another.com/bar/foo/link3.html");
        StaticConnection interval = new StaticConnection(new Date().getTime(), 500);
        WebsiteDownload download = new WebsiteDownload(interval);
        download.downloadAll(500, links, path);        
    }
}
