package christian.duvick.parsers;

import java.util.LinkedList;
import java.util.concurrent.Callable;

public class Parser<T> implements Callable<T> {

    //https://howtodoinjava.com/java/regex/java-clean-ascii-text-non-printable-chars/
    public static String cleanTextContent(String text) {
        // strips off all non-ASCII characters
        text = text.replaceAll("[^\\x00-\\x7F]", "");
        // erases all the ASCII control characters
        text = text.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
        // removes non-printable characters from Unicode
        text = text.replaceAll("\\p{C}", "");
        return text.trim();
    }

    public T call() {
        return null;
    }
}

