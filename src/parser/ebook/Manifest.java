package parser.ebook;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class Manifest {
    private List<ManifestItem> manifestItems = new ArrayList<>();
    private String toc;

    Manifest(Element manifest, String path) {
        NodeList items = manifest.getElementsByTagName("item");
        for (int i = 0; i < items.getLength(); i++) {
            ManifestItem current = new ManifestItem(items.item(i), path);
            manifestItems.add(current);
            if (current.type.equals("application/x-dtbncx+xml"))
                toc = current.location;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        manifestItems.forEach(m -> sb.append(m.toString() + "\n\n"));
        return sb.toString();
    }

    public String getToc() {
        return toc;
    }

    private class ManifestItem {

        private final String id;
        private final String location;
        private final String type;

        private ManifestItem(Node n, String path) {
            NamedNodeMap attributes = n.getAttributes();
            id = attributes.getNamedItem("id").getTextContent();
            location = path + attributes.getNamedItem("href").getTextContent();
            type = attributes.getNamedItem("media-type").getTextContent();
        }

        private String getId() {
            return id;
        }

        private String getLocation() {
            return location;
        }

        private String getType() {
            return type;
        }

        @Override
        public String toString() {
            return new StringBuilder().append("ID: " + id).append("\nLocation: " + location).append("\nType: " + type).toString();
        }
    }
}
