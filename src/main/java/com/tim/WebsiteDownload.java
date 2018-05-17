package com.tim;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

public class WebsiteDownload {

    public static void getAndDownloadAll(int timeout, String link, String selector, String path) {
        String doc = get(link);
        String baseUrl = WebsiteParser.getBaseUrl(link);
        List<String> links = WebsiteParser.getLinksSelector(doc, baseUrl, selector);
        downloadAll(timeout, links, path);
    }

    public static void downloadLink(int timeout, String link, String path) {
        downloadAll(timeout, Arrays.asList(link), path);
    }

    public static void downloadAll(int timeout, List<String> links, String path) {
        File filePath = new File(path);
        if (!filePath.isDirectory()) {
            throw new RuntimeException("Given path " + path + " should be a directory.");
        }

        Pattern htmlFilePattern = Pattern.compile("[a-z]+:.*\\.com.*\\/(.*)\\.(htm|html|shtml)");
        for (String link : links) {
            System.out.println(String.format("Getting link %s", link));
            String site = get(link);
            
            Matcher matcher = htmlFilePattern.matcher(link);
            if (!matcher.matches()) {
                throw new RuntimeException("Unable to parse the url to find a filename");
            }
            String filename = matcher.group(1);
            if (filename == null || filename.isEmpty()) {                
                // TODO make up a filename
                System.out.println("filename is empty");
            }    
            System.out.println("Using filename " + filename);
            write(site, path + "/" + filename);
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException in) {
                in.printStackTrace();
                throw new RuntimeException("Download interrupted");
            }
            
        }
    }


    public String parse(InputStream stream, String baseUri) {
        try {
            return Jsoup.parse(stream, "UTG-8", baseUri).toString();
        } catch (IOException io) {
            throw new UncheckedIOException(io);
        }
    }

    public static void write(String content, String path) {
        try (FileWriter writer = new FileWriter(new File(path))) {
            write(content, writer);
        } catch (IOException io) {
            throw new UncheckedIOException(io);
        }
    }

    public static void write(String content, Writer writer) {
        try {
            writer.write(content);
        } catch (IOException io) {
            throw new UncheckedIOException(io);
        }   
    }

    private static String get(String url) {
        try {
            return Jsoup.connect(url).get().html();
        } catch (IOException io) {
            throw new UncheckedIOException(io);
        }
    }
}