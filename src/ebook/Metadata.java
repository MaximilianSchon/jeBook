package ebook;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Optional;

public class Metadata {
    private String title;
    private String language;
    private String identifier;

    private Optional<String> creator = Optional.empty();
    private Optional<String> contributor = Optional.empty();
    private Optional<String> publisher = Optional.empty();
    private Optional<String[]> subject = Optional.empty();
    private Optional<String> description = Optional.empty();
    private Optional<String> date = Optional.empty();
    private Optional<String> type = Optional.empty();
    private Optional<String> format = Optional.empty();
    private Optional<String> source = Optional.empty();
    private Optional<String> relation = Optional.empty();
    private Optional<String> coverage = Optional.empty();
    private Optional<String> rights = Optional.empty();
    private Optional<String> coverImageId = Optional.empty();


    public Metadata(Element metadata) {
        title = getItem(metadata, "title");
        language = getItem(metadata, "language");
        identifier = getItem(metadata, "identifier");
        creator = getOptionalItem(metadata, "creator");
        contributor = getOptionalItem(metadata, "contributor");
        publisher = getOptionalItem(metadata, "publisher");
        description = getOptionalItem(metadata, "description");
        date = getOptionalItem(metadata, "date");
        type = getOptionalItem(metadata, "type");
        format = getOptionalItem(metadata, "format");
        source = getOptionalItem(metadata, "source");
        relation = getOptionalItem(metadata, "source");
        coverage = getOptionalItem(metadata, "coverage");
        rights = getOptionalItem(metadata, "rights");
        coverImageId = getCoverImage(metadata);
    }

    private String getItem(Element metadata, String item) {
        Node n = metadata.getElementsByTagName("dc:" + item).item(0);
        if (n == null)
            return null;
        return n.getTextContent();
    }

    private Optional<String> getOptionalItem(Element metadata, String item) {
        return Optional.ofNullable(getItem(metadata, item));
    }

    private Optional<String> getCoverImage(Element metadata) {
        NodeList nl = metadata.getElementsByTagName("meta");
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getAttributes().getNamedItem("name").getTextContent().equals("cover"))
                return Optional.of(nl.item(i).getAttributes().getNamedItem("content").getTextContent());
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: " + title).append("\nLanguage: " + language).append("\nID: " + identifier);
        creator.ifPresent(s -> sb.append("Creator: " + s + "\n"));
        contributor.ifPresent(s -> sb.append("Contributor " + s + "\n"));
        publisher.ifPresent(s -> sb.append("Publisher " + s + "\n"));
        description.ifPresent(s -> sb.append("Description " + s + "\n"));
        date.ifPresent(s -> sb.append("Date " + s + "\n"));
        type.ifPresent(s -> sb.append("Type " + s + "\n"));
        format.ifPresent(s -> sb.append("Format " + s + "\n"));
        source.ifPresent(s -> sb.append("Source " + s + "\n"));
        relation.ifPresent(s -> sb.append("Relation " + s + "\n"));
        coverage.ifPresent(s -> sb.append("Coverage " + s + "\n"));
        rights.ifPresent(s -> sb.append("Rights " + s + "\n"));
        subject.ifPresent(strings -> {
            sb.append("Subject(s): ");
            for (String s : strings) {
                sb.append(s + ", ");
            }
        });
        return sb.toString();
    }
}
