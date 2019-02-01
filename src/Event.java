package sample;

public class Event {
    TimeInterval TimeInterval;
    ArrayList<String> tags;
    String name;

    public Event(TimeInterval time, ArrayList<String> tags, String name) {
        this.TimeInterval = time;
        this.tags = tags;
        this.name = name;
    }
}