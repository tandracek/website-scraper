package com.tim;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.io.FileWriter;
import java.io.File;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class WebsiteDownload {
    private Connection connection;

    public WebsiteDownload(Connection connection) {
        this.connection = connection;
    }

    public void downloadLink(int timeout, String link, String path) {
        downloadAll(timeout, Arrays.asList(link), path);
    }

    public void downloadAll(int timeout, List<String> links, String path) {
        File filePath = new File(path);
        if (!filePath.isDirectory()) {
            throw new RuntimeException("Given path " + path + " should be a directory.");
        }

        Pattern htmlFilePattern = Pattern.compile("[a-z]+:.*\\.com.*\\/(.*)\\.(htm|html)");
        for (String link : links) {
            Document doc = this.connection.get(link);
            Matcher matcher = htmlFilePattern.matcher(link);
            if (!matcher.matches()) {
                throw new RuntimeException("Unable to parse the url to find a filename");
            }
            String filename = matcher.group(1);
            if (filename.isEmpty()) {                
                // TODO make up a filename
                System.out.println("filename is empty");
            }    
            System.out.println("Using filename " + filename);
            write(doc, path + "/" + filename);
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException in) {
                in.printStackTrace();
                throw new RuntimeException("Download interrupted");
            }
            
        }
    }

    public static List<String> getLinksByElement(Document document, String tag) {
        return document.getElementsByTag(tag).stream()
            .map(element -> element.getElementsByTag("a"))
            .flatMap(Elements::stream)
            .map(element ->  element.attr("href"))
            .collect(Collectors.toList());
    }

    public static void write(Document document, String path) {
        try (FileWriter writer = new FileWriter(new File(path))) {
            write(document, writer);
        } catch (IOException io) {
            throw new UncheckedIOException(io);
        }
    }

    public static void write(Document document, Writer writer) {
        try {
            writer.write(document.html());
        } catch (IOException io) {
            throw new UncheckedIOException(io);
        }
        
    }
}