package sample;

public class Event {
    TimeInterval TimeInterval;
    ArrayList<String> tags;

    public Event(TimeInterval time, ArrayList<String> tags) {
        this.TimeInterval = time;
        this.tags = tags;
    }
}