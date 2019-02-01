import java.util.ArrayList;

public class Event {
    private TimeInterval TimeInterval;
    private ArrayList<String> tags;
    private String name;

    public Event(TimeInterval time, ArrayList<String> tags, String name) {
        this.TimeInterval = time;
        this.tags = tags;
        this.name = name;
    }
}