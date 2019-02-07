import java.util.ArrayList;

class Note {
    private static ArrayList<Note> notes = new ArrayList<>();
    private String content;

    Note(String content) {
        this.content = content;
        notes.add(this);
    }

    static ArrayList<Note> getNotes() {
        return notes;
    }

    String getContent() {
        return content;
    }

    void setContent(String content) {
        this.content = content;
    }
}