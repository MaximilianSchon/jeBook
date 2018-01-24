package ebook;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ManifestItem {

    private final String id;
    private final String location;
    private final String type;

    public ManifestItem(Node n) {
        NamedNodeMap attributes = n.getAttributes();
        id = attributes.getNamedItem("id").getTextContent();
        location = attributes.getNamedItem("href").getTextContent();
        type = attributes.getNamedItem("media-type").getTextContent();
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("ID:" + id).append("\nLocation:" + location).append("\nType: " + type + "\n\n").toString();
    }
}

