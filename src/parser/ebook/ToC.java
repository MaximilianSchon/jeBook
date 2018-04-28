package parser.ebook;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class ToC {

        private ToCHead toCHead;
        private String title;
        private List<NavPoint> navMap;
        private Optional<List<NavPoint>> pages;
        private String path;
    public ToC(Element toc, String path) {
        this.path = path;
        toCHead = new ToCHead(toc.getElementsByTagName("meta"));
        title = toc.getElementsByTagName("docTitle").item(0).getTextContent();
        navMap = createNavMap((Element) toc.getElementsByTagName("navMap").item(0));
        pages = createPageList((Element) toc.getElementsByTagName("pageList").item(0));
        Collections.sort(navMap);
        }

        private List<NavPoint> createNavMap(Element navMap) {
            return getSpecificElementList(navMap, "navPoint");
        }

        private Optional<List<NavPoint>> createPageList(Element pageList) {
            if (pageList == null)
                return Optional.empty();
            return Optional.of(getSpecificElementList(pageList, "pageTarget"));
        }

    private ArrayList<NavPoint> getSpecificElementList(Element elements, String element) {
        ArrayList<NavPoint> pages = new ArrayList<>();
        NodeList items = elements.getElementsByTagName(element);
        for (int i = 0; i < items.getLength(); i++) {
            pages.add(new NavPoint(items.item(i), path));
        }
        return pages;
    }

    public String getPagePath(int nbr) {
        return navMap.get(nbr).contentLocation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Head:\n").append(toCHead).append("\n").append("Title:").append(title).append("\n");
        sb.append("NavMap:\n");
        navMap.forEach(np -> sb.append(np));
        pages.ifPresent(pages -> {
            sb.append("Pages: ");
            pages.forEach(np -> sb.append(np));
        });
        return sb.toString();
    }

    public int getBookSize() {
        return navMap.size();
    }

    class ToCHead {
        private String uid;
        private Optional<String> generator;
        private Optional<String> depth;
        private int totalPageCount;
        private int maxPageNumber;

        private ToCHead(NodeList meta) {
            uid = getItem(meta, "uid");
            generator = Optional.ofNullable(getItem(meta, "generator"));
            depth = Optional.ofNullable(getItem(meta, "depth"));
            totalPageCount = Integer.parseInt(getItem(meta, "totalPageCount"));
            maxPageNumber = Integer.parseInt(getItem(meta, "maxPageNumber"));
        }
        private String getItem(NodeList meta, String item) {
            for (int i = 0; i < meta.getLength(); i++) {
                if (meta.item(i).getAttributes().getNamedItem("name").getTextContent().equals("dtb:" + item)) {
                    return meta.item(i).getAttributes().getNamedItem("content").getTextContent();
                }
            }
            return null;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("UID: ").append(uid).append("\n");
            if (totalPageCount > 0)
                sb.append("Total pages: ").append(totalPageCount).append("\n");
            if (maxPageNumber > 0)
                sb.append("Max pages: ").append(maxPageNumber).append("\n");
            generator.ifPresent(s -> sb.append("Generator: ").append(s).append("\n"));
            depth.ifPresent(s -> sb.append("Depth: ").append(s).append("\n"));
            return sb.toString();
        }
    }

    class NavPoint implements Comparable {
        private String id;
        private String contentText;
        private String contentLocation;
        private int playOrder;
        private Optional<Integer> value;
        private Optional<String> type;

        NavPoint(Node n, String path) {
            contentText = n.getChildNodes().item(1).getTextContent();
            id = n.getAttributes().getNamedItem("id").getTextContent();
            value = getValue(n);
            type = getType(n);
            playOrder = Integer.parseInt(n.getAttributes().getNamedItem("playOrder").getTextContent());
            contentLocation = path + ((Element) n).getElementsByTagName("content").item(0).getAttributes().getNamedItem("src").getTextContent();
        }

        private Optional<String> getType(Node n) {
            if (n.getAttributes().getNamedItem("type") != null) {
                return Optional.of(n.getAttributes().getNamedItem("type").getTextContent());
            }
            return Optional.empty();
        }

        private Optional<Integer> getValue(Node n) {
           if (n.getAttributes().getNamedItem("value") != null) {
               return Optional.of(Integer.parseInt(n.getAttributes().getNamedItem("value").getTextContent()));
           }
           return Optional.empty();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\nID: ").append(id).append("\nPlayOrder: ").append(playOrder);
            value.ifPresent(i -> sb.append("\nValue: ").append(i));
            type.ifPresent(t -> sb.append("\nType: ").append(t));
            sb.append("\nContent: ").append(contentText).append("\nContent location: ").append(contentLocation);
            return sb.toString();
        }

        @Override
        public int compareTo(Object o) {
            if (o instanceof NavPoint)
                return Integer.compare(playOrder, ((NavPoint) o).playOrder);
            throw new ClassCastException();
        }
    }
}
