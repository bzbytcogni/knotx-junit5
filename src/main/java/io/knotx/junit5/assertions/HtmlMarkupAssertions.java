package io.knotx.junit5.assertions;

import static java.lang.Character.isWhitespace;

import java.nio.charset.StandardCharsets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Assertions;

public final class HtmlMarkupAssertions {

  private static final Document.OutputSettings OUTPUT_SETTINGS = new Document.OutputSettings()
      .escapeMode(Entities.EscapeMode.xhtml)
      .indentAmount(2)
      .prettyPrint(true);

  private HtmlMarkupAssertions() {
    //util class
  }

  public static void assertHtmlBodyMarkupsEqual(String expectedHtml, String actualHtml) {
    assertHtmlBodyMarkupsEqual(expectedHtml, actualHtml, null);
  }

  public static void assertHtmlBodyMarkupsEqual(String expectedHtml, String actualHtml,
      String message) {
    final String expectedBodyMarkup = getFormattedBodyOfAFullPage(expectedHtml);
    final String actualBodyMarkup = getFormattedBodyOfAFullPage(actualHtml);
    Assertions.assertEquals(expectedBodyMarkup, actualBodyMarkup, message);
  }

  private static String escapeAndFormat(String expected) {
    return stripSpace(getFormattedBodyOfAFullPage(expected));
  }

  private static String getFormattedBodyOfAFullPage(String html) {
    return Jsoup.parse(html, StandardCharsets.UTF_8.name(), Parser.xmlParser())
        .outputSettings(OUTPUT_SETTINGS)
        .body()
        .html()
        .trim();
  }

  private static String stripSpace(String toBeStripped) {
    final StringBuilder result = new StringBuilder();
    boolean lastWasSpace = true;
    for (int i = 0; i < toBeStripped.length(); i++) {
      char c = toBeStripped.charAt(i);
      if (isWhitespace(c)) {
        if (!lastWasSpace) {
          result.append(' ');
        }
        lastWasSpace = true;
      } else {
        result.append(c);
        lastWasSpace = false;
      }
    }
    return result.toString().trim();
  }

}
