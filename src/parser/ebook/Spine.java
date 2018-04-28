package parser.ebook;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Optional;

class Spine {
    ArrayList<SpineItem> spineItems = new ArrayList<>();

    Spine(Element spine) {
        NodeList items = spine.getElementsByTagName("itemref");
        for (int i = 0; i < items.getLength(); i++) {
            spineItems.add(new SpineItem(items.item(i)));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        spineItems.forEach(si -> sb.append(si.toString() + "\n\n"));
        return sb.toString();
    }

    public class SpineItem {
        private boolean linear;
        private String idref;

        SpineItem(Node node) {
            NamedNodeMap attributes = node.getAttributes();
            idref = attributes.getNamedItem("idref").getTextContent();
            linear = Optional.ofNullable(attributes.getNamedItem("linear")).map(n -> n.getTextContent()).orElse("no").equals("yes");
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("IDRef: " + idref)
                    .append("\nLinear: " + linear);
            return sb.toString();
        }
    }
}
