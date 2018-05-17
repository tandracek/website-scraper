package com.tim;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Jsoup.class)
public class WebsiteDownloadTest {
    public static final String path = "src/test/resources";
    private static Connection mockedConnection;

    @BeforeClass
    public static void beforeClass() {
        mockedConnection = mock(Connection.class);
    }

    @After
    public void teardown() throws Exception {
        FileUtils.cleanDirectory(new File(path + "/out"));
    }

    @Test
    public void downloadAll() throws Exception {
        String path = "src/test/resources/out";
        List<String> links = Arrays.asList("http://www.test.com/link1.htm", 
                                           "http://www.something.com/foo/link2.html",
                                           "http://www.another.com/bar/foo/link3.html",
                                           "http://www.whatever.com/scripts/script-01.shtml");

        Document doc = Jsoup.parse("<a>test</a>");
        mockStatic(Jsoup.class);
        PowerMockito.when(Jsoup.connect(anyString())).thenReturn(mockedConnection);
        when(mockedConnection.get()).thenReturn(doc);

        WebsiteDownload.downloadAll(100, links, path);
        File link1 = new File(path + "/link1");
        assertTrue(link1.exists());
        File link2 = new File(path + "/link2");
        assertTrue(link2.exists());
        File link3 = new File(path + "/link3");
        assertTrue(link3.exists());
        File script1 = new File(path + "/script-01");
        assertTrue(script1.exists());
    }

    @Test
    public void downloadWithInterval() throws Exception {
        String path = "src/test/resources/out";
        List<String> links = Arrays.asList("http://www.test.com/link1.htm", 
                                           "http://www.something.com/foo/link2.html",
                                           "http://www.another.com/bar/foo/link3.html");

        Document doc = Jsoup.parse("<a>test</a>");
        mockStatic(Jsoup.class);
        PowerMockito.when(Jsoup.connect(anyString())).thenReturn(mockedConnection);
        when(mockedConnection.get()).thenReturn(doc);

        StupidTime time = new StupidTime(System.currentTimeMillis() - 500);
        when(mockedConnection.get()).then(something -> {
            long elapsed = System.currentTimeMillis() - time.getTime();
            assertTrue(elapsed >= 500);
            time.setTime(System.currentTimeMillis());
            return doc;
        });
        WebsiteDownload.downloadAll(500, links, path);
    }

    private class StupidTime {
        private long time;

        public StupidTime(long time) {
            this.time = time;
        }

        public long getTime() {
            return this.time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }
}
