package parser;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.zip.ZipFile;
import parser.ebook.Package;
import parser.ebook.ToC;

public class EPubFile extends ZipFile {
    private String path;
    private String contentPath;
    private Package aPackage;
    private ToC toc;

    public EPubFile(String name) throws IOException, ParserConfigurationException, SAXException {
        super(name);
        System.out.println(name);
        contentPath = EPubParserUtil.getContentPath(getContent("META-INF/container.xml"));
        path =  EPubParserUtil.getPath(contentPath);
        aPackage = EPubParserUtil.createBook(EPubParserUtil.getDocument(getContent(contentPath)), path);
        toc = EPubParserUtil.createToC(EPubParserUtil.getDocument(getContent(aPackage.getManifest().getToc())), path);
    }

    private String getContent(String path) throws IOException {
        return EPubParserUtil.streamToString(this.getInputStream(this.getEntry(path)));
    }

    Package getPackage() {
        return aPackage;
    }
    public String getPage(int nbr) throws IOException {
        String fullPath = toc.getPagePath(nbr-1);
        if (fullPath.contains("#")) {
            String file = fullPath.substring(0, fullPath.lastIndexOf("#"));
            //String link = fullPath.substring(fullPath.lastIndexOf("#"));
            return getContent(file);
        } else {
            return getContent(fullPath);
        }
    }
    public int getSize() {
        return toc.getBookSize();
    }


}
