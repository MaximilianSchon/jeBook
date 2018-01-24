package ebook;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class Manifest {
    private List<ManifestItem> manifestItems = new ArrayList<>();
    public Manifest(Element manifest) {
        NodeList items = manifest.getElementsByTagName("item");
        for (int i = 0; i < items.getLength(); i++) {
           manifestItems.add(new ManifestItem(items.item(i)));
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        manifestItems.forEach(m -> sb.append(m.toString()));
        return sb.toString();
    }
}
