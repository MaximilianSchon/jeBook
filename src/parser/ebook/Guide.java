package parser.ebook;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Guide {

    private List<GuideItem> guideItems = new ArrayList<>();
    public Guide(Element guide, String path) {
        NodeList items = guide.getElementsByTagName("reference");
        for (int i = 0; i < items.getLength(); i++) {
            guideItems.add(new GuideItem(items.item(i), path));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        guideItems.forEach(g -> sb.append(g.toString() + "\n\n"));
        return sb.toString();
    }

    private class GuideItem {
        private String type;
        private Optional<String> title;
        private String location;
        private GuideItem(Node node, String path) {
            NamedNodeMap attributes = node.getAttributes();
            type = attributes.getNamedItem("type").getTextContent();
            location = path + attributes.getNamedItem("href").getTextContent();
            title = Optional.ofNullable(attributes.getNamedItem("title")).map(n -> n.getTextContent());
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            title.ifPresent(s -> sb.append("Title: " + s + "\n"));
            sb.append("Type: " + type).append("\nLocation: ").append(location);
            return sb.toString();
        }
    }
}
