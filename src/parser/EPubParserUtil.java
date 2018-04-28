package parser;

import parser.ebook.Package;
import parser.ebook.ToC;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.UnknownHostException;
import java.util.stream.Collectors;

public class EPubParserUtil {


    static String streamToString(InputStream stream) {
        return new BufferedReader(new InputStreamReader(stream))
                .lines().collect(Collectors.joining("\n"));
    }

    static String getContentPath(String xml) throws ParserConfigurationException, IOException, SAXException {
        NodeList nList = getDocument(xml).getElementsByTagName("rootfiles");
        Element eElement = (Element) nList.item(0);
        return eElement.getElementsByTagName("rootfile").
                item(0).getAttributes()
                .getNamedItem("full-path").getTextContent();
    }

    static String getPath(String contentPath) {
        return contentPath.substring(0, contentPath.lastIndexOf("/") + 1);
    }

    static Document getDocument(String xml) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc;
        try {
            doc =  dbf
                    .newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xml)));
        } catch (UnknownHostException e) { //If the domain is offline or the computer is offline
            dbf.setValidating(false);
            dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            doc =  dbf
                    .newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xml)));
        }
         return doc;
    }

    static Package createBook(Document content, String path) {
            return new Package(content, path);
    }

    static ToC createToC(Document toc, String path) {
        return new ToC((Element) toc.getElementsByTagName("ncx").item(0), path);
    }
}
