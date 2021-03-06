package parser.ebook;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Package {

    private Metadata metadata;
    private Manifest manifest;
    private Spine spine;
    private Guide guide;

    public Package(Document content, String path) {
        this.metadata = new Metadata((Element) content.getElementsByTagName("metadata").item(0));
        this.manifest = new Manifest((Element) content.getElementsByTagName("manifest").item(0), path);
        this.spine = new Spine((Element) content.getElementsByTagName("spine").item(0));
        this.guide = new Guide((Element) content.getElementsByTagName("guide").item(0), path);
    }


    public Metadata getMetadata() {
        return metadata;
    }

    public Manifest getManifest() {
        return manifest;
    }

    public Spine getSpine() {
        return spine;
    }

    public Guide getGuide() {
        return guide;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("METADATA\n").append(metadata.toString() + "\n\n")
          .append("MANIFEST\n").append(manifest.toString() + "\n\n")
          .append("SPINE\n").append(spine.toString() + "\n\n")
          .append("GUIDE\n").append(guide.toString() + "\n\n");
        return sb.toString();
    }
}
