package com.tim;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebsiteParser {

    public static String strip(Document document, String element, Function<Element, Boolean> filter) {
        return document.getElementsByTag(element).stream()
                .filter(ele -> filter.apply(ele))
                .map(Element::html)
                .collect(Collectors.joining("\n"));
    }
}