package ebook;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Book {

    private Metadata metadata;
    private Manifest manifest;
    private Spine spine;
    private Guide guide;

    public Book(Document content) {
        this.metadata = new Metadata((Element) content.getElementsByTagName("metadata").item(0));
        this.manifest = new Manifest((Element) content.getElementsByTagName("manifest").item(0));
        this.spine = new Spine((Element) content.getElementsByTagName("spine").item(0));
        this.guide = new Guide((Element) content.getElementsByTagName("guide").item(0));
    }


}
