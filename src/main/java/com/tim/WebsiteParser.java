package com.tim;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebsiteParser {

    public static String strip(Document document, String element, Function<Element, Boolean> filter) {
        return document.getElementsByTag(element).stream()
                .filter(ele -> filter.apply(ele))
                .map(Element::html)
                .collect(Collectors.joining("\n"));
    }

    public static List<String> getLinksByElement(Document document, String tag) {
        return document.getElementsByTag(tag).stream()
            .map(element -> element.getElementsByTag("a"))
            .flatMap(Elements::stream)
            .map(element ->  element.attr("href"))
            .collect(Collectors.toList());
    }
}