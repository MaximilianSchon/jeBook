package parser.ebook;

public class NoSuchPageException extends Exception {
    NoSuchPageException(String message) {
        super(message);
    }
}
