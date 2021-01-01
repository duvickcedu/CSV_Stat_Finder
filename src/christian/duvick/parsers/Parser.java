package christian.duvick.parsers;

import java.util.concurrent.Callable;

/**
 * Just a generic parser class that will be used for reading lines of the file.
 * @param <T>
 */
public class Parser<T> implements Callable<T> {
    /**
     * This method is used to clean the string as I have ran into issues with unprintable characters
     * that I believe excel made when I created the file
     * @param text String to clean
     * @return String
     *
     * https://howtodoinjava.com/java/regex/java-clean-ascii-text-non-printable-chars/
     */
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

