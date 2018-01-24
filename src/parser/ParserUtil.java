package parser;

import ebook.Book;
import ebook.Metadata;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.stream.Collectors;

public class ParserUtil {


    public static String streamToString(InputStream stream) {
        return new BufferedReader(new InputStreamReader(stream))
                .lines().collect(Collectors.joining("\n"));
    }

    public static String getContentPath(String xml) throws ParserConfigurationException, IOException, SAXException {
        NodeList nList = getDocument(xml).getElementsByTagName("rootfiles");
        Element eElement = (Element) nList.item(0);
        return eElement.getElementsByTagName("rootfile")
                .item(0).getAttributes()
                .getNamedItem("full-path")
                .toString()
                .replace("full-path=", "")
                .replaceAll("\"", "");
    }

    public static Document getDocument(String xml) throws ParserConfigurationException, IOException, SAXException {
        return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)));
    }

    public static Book createBook(Document content) {
       Book book = new Book(content);
        return book;
    }
}
