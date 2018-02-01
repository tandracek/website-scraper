package com.tim;

import org.jsoup.nodes.Document;

public interface Connection {
    public Document get(String link);
}