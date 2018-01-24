package parser;



import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import java.util.zip.ZipFile;

public class Parser {
        private ZipFile zipFile;

    public Parser(String file) throws IOException {
        zipFile = new ZipFile(file);
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        new Parser("test.epub").run();
        new Parser("Extreme_Programming_Pocket_Guide.epub").run();
    }

    private void run() throws IOException, ParserConfigurationException, SAXException {
        ParserUtil.createBook(ParserUtil.getDocument(getContent(getContentPath())));
    }

    private String getContentPath() throws IOException, ParserConfigurationException, SAXException {
        return ParserUtil.getContentPath(getContent("META-INF/container.xml"));
    }

    private String getContent(String path) throws IOException {
        return ParserUtil.streamToString(zipFile.getInputStream(zipFile.getEntry(path)));
    }
}
