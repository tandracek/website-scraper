package com.tim;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
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

    public static String strip(String html, String selector, Function<Element, Boolean> filter) {
        return toDocument(html).select(selector).stream()
                .filter(ele -> filter.apply(ele))
                .map(Element::html)
                .collect(Collectors.joining("\n"));
    }

    public static List<String> getLinksSelector(String html, String baseUrl, String selector) {
        return toDocument(html).select(selector).stream()
            .map(element ->  element.attr("href"))
            .map(link -> baseUrl + link)
            .collect(Collectors.toList());
    }

    private static Document toDocument(String html) {
        return Jsoup.parse(html);
    }
}