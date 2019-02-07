import java.util.ArrayList;

class Notes {
    private static ArrayList<Notes> notes = new ArrayList<>();
    private String content;

    Notes(String content) {
        this.content = content;
        notes.add(this);
    }

    static ArrayList<Notes> getNotes() {
        return notes;
    }

    String getContent() {
        return content;
    }

    void setContent(String content) {
        this.content = content;
    }
}