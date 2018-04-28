package parser;



import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Parser {
        private EPubFile ePubFile;

    private Parser(String file) throws IOException, ParserConfigurationException, SAXException {
        ePubFile = new EPubFile(file);
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        new Parser("test.epub").run();
        new Parser("Extreme_Programming_Pocket_Guide.epub").run();
    }

    private void run() {
        ePubFile.getPackage();
    }
}
