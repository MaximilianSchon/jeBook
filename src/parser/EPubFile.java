package parser;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.zip.ZipFile;

import parser.ebook.NoSuchPageException;
import parser.ebook.Package;
import parser.ebook.Page;
import parser.ebook.ToC;

public class EPubFile extends ZipFile {
    private String path;
    private String contentPath;
    private Package aPackage;
    private ToC toc;
    private boolean navMapFlag = false;

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

    public String getPage(int nbr) {
        String pagePath = null;
        try {
            if (navMapFlag) {
                pagePath = toc.getPage(nbr - 1);
            } else {
                pagePath = toc.getChapter(nbr - 1);
            }
        } catch (NoSuchPageException e) {
            navMapFlag = true;
        }
        String nextId = "";
        if (nbr < getSize()) {
            if (navMapFlag) {
                try {
                    nextId = toc.getPage(nbr);
                } catch (NoSuchPageException e) {
                    e.printStackTrace();
                }
            } else {
                nextId = toc.getChapter(nbr);
            }
            if (nextId.contains("#")) {
                nextId = nextId.substring(nextId.indexOf("#")+1);
            }
        }
        Page page = null;
        try {
        int lastIndex = pagePath.lastIndexOf("#");
        String file;
        if (lastIndex != -1) {
            file = pagePath.substring(0, lastIndex);
            page = new Page(getContent(file), pagePath.substring(lastIndex + 1), nextId);
        } else {
            if (!nextId.contains(pagePath)) nextId = "";
            page = new Page(getContent(pagePath), "", nextId);
        }

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*if (fullPath.contains("#")) {
            String file = fullPath.substring(0, fullPath.lastIndexOf("#"));
            String link = fullPath.substring(fullPath.lastIndexOf("#"));
            return getContent(file);
        } else {
            return getContent(fullPath);
        } */
        return page.getContent();
    }


    public int getSize() {
        return toc.getBookSize();
    }


}
