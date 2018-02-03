package com.tim;

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class StaticConnection implements Connection {
    private long interval;
    private int timeout;
    private int count;
    private String link;

    public StaticConnection() {
        this.interval = 0;
        this.timeout = 0;
    }

    public StaticConnection(long interval, int timeout) {
        this.interval = interval;
        this.timeout = timeout;
    }

    @Override
    public Connection url(URL url) {
        return null;
    }

    @Override
    public Connection url(String url) {
        this.link = url;
        return this;
    }

    @Override
    public Connection proxy(Proxy proxy) {
        return null;
    }

    @Override
    public Connection proxy(String host, int port) {
        return null;
    }

    @Override
    public Connection userAgent(String userAgent) {
        return null;
    }

    @Override
    public Connection timeout(int millis) {
        return null;
    }

    @Override
    public Connection maxBodySize(int bytes) {
        return null;
    }

    @Override
    public Connection referrer(String referrer) {
        return null;
    }

    @Override
    public Connection followRedirects(boolean followRedirects) {
        return null;
    }

    @Override
    public Connection method(Method method) {
        return null;
    }

    @Override
    public Connection ignoreHttpErrors(boolean ignoreHttpErrors) {
        return null;
    }

    @Override
    public Connection ignoreContentType(boolean ignoreContentType) {
        return null;
    }

    @Override
    public Connection validateTLSCertificates(boolean value) {
        return null;
    }

    @Override
    public Connection data(String key, String value) {
        return null;
    }

    @Override
    public Connection data(String key, String filename, InputStream inputStream) {
        return null;
    }

    @Override
    public Connection data(String key, String filename, InputStream inputStream, String contentType) {
        return null;
    }

    @Override
    public Connection data(Collection<KeyVal> data) {
        return null;
    }

    @Override
    public Connection data(Map<String, String> data) {
        return null;
    }

    @Override
    public Connection data(String... keyvals) {
        return null;
    }

    @Override
    public KeyVal data(String key) {
        return null;
    }

    @Override
    public Connection requestBody(String body) {
        return null;
    }

    @Override
    public Connection header(String name, String value) {
        return null;
    }

    @Override
    public Connection headers(Map<String, String> headers) {
        return null;
    }

    @Override
    public Connection cookie(String name, String value) {
        return null;
    }

    @Override
    public Connection cookies(Map<String, String> cookies) {
        return null;
    }

    @Override
    public Connection parser(Parser parser) {
        return null;
    }

    @Override
    public Connection postDataCharset(String charset) {
        return null;
    }

    @Override
    public Document get() throws IOException {
        if (interval > 0) {
            long curr = new Date().getTime();
            long difference = curr - interval;
            if (count++ > 0) {
                assertTrue(difference >= timeout);
            }
            interval = curr;
        }
        return Jsoup.parse(String.format("<div id='%s'>a test</div>", this.link));
    }

    @Override
    public Document post() throws IOException {
        return null;
    }

    @Override
    public Response execute() throws IOException {
        return null;
    }

    @Override
    public Request request() {
        return null;
    }

    @Override
    public Connection request(Request request) {
        return null;
    }

    @Override
    public Response response() {
        return null;
    }

    @Override
    public Connection response(Response response) {
        return null;
    }
}