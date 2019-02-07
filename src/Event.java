import java.util.ArrayList;

class Event {
    private static ArrayList<Event> events = new ArrayList<>();
    private Date date;
    private String name;

    Event(Date date, String name) {
        this.date = date;
        this.name = name;
        events.add(this);
    }

    static ArrayList<Event> getEvents() {
        return events;
    }

    Date getDate() {
        return date;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}