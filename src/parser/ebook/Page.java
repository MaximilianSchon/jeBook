package parser.ebook;


public class Page {
    private String id;
    private String nextId;
    private String content;

    public Page(String content, String id, String nextId) {
        this.content = content;
        this.id = id;
        this.nextId = nextId;
    }

    public String getContent() {
        if (!id.equals("") && !nextId.equals("")) {
            if (content.contains(id)) {
                content = content.substring(content.indexOf(id));
                content = content.substring(content.indexOf(nextId));
            }
        } else {
            if (id.equals("")) {
                content = content.substring(content.indexOf(nextId));
            }
            else if (nextId.equals("")) {
                content = content.substring(content.indexOf(id));
            }
        }
        return content;
    }
}
