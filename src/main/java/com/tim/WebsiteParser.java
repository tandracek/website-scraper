package com.tim;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class WebsiteParser {

    public static String getBaseUrl(String url) {
        Pattern basePattern = Pattern.compile("(.*\\.com).*");
        Matcher baseMatcher = basePattern.matcher(url);
        if (baseMatcher.matches()) {
            return baseMatcher.group(1);
        }
        return "";
    }

    public static String strip(Document document, String element, Function<Element, Boolean> filter) {
        return document.getElementsByTag(element).stream()
                .filter(ele -> filter.apply(ele))
                .map(Element::html)
                .collect(Collectors.joining("\n"));
    }

    public static List<String> getLinksSelector(Document document, String baseUrl, String selector) {
        return document.select(selector).stream()
            .map(element ->  element.attr("href"))
            .map(link -> baseUrl + link)
            .collect(Collectors.toList());
    }
}