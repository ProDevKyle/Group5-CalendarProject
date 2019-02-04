import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.Calendar;
import java.util.List;

public class Controller {
    private int[] DAYS_IN_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    @FXML
    private Label date;

    @FXML
    private GridPane calendarPane;

    @FXML
    public void initialize() {
        date.setText("Today is " + getWeekday() + ", " + getMonth() + " " + getDay() + ", " + getYear() + ".");
        List<Node> nodes = calendarPane.getChildren();
        int d = 0;
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            if (n instanceof AnchorPane)
                ((Label) ((AnchorPane) n).getChildren().get(0)).setText(Integer.toString(++d));
        }
        new AnimationTimer() {
            Long time = System.currentTimeMillis();
            @Override
            public void handle(long now) {
                if (System.currentTimeMillis() - time > 1000) {
                    time = System.currentTimeMillis();
                }
            }
        }.start();
    }

    private static void displayYear() {
        int[] monthStarts = new int[12];
        int day = getDay();
        int dayWeekday = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        for (int i = 0; i < monthStarts.length; i++) {

        }
    }

    private static String getWeekday() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            default:
                return "Saturday";
        }
    }

    private static String getMonth() {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
                return "January";
            case Calendar.FEBRUARY:
                return "February";
            case Calendar.MARCH:
                return "March";
            case Calendar.APRIL:
                return "April";
            case Calendar.MAY:
                return "May";
            case Calendar.JUNE:
                return "June";
            case Calendar.JULY:
                return "July";
            case Calendar.AUGUST:
                return "August";
            case Calendar.SEPTEMBER:
                return "September";
            case Calendar.OCTOBER:
                return "October";
            case Calendar.NOVEMBER:
                return "November";
            default:
                return "December";
        }
    }

    private static int getDay() {
       return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    private static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    private static void addNotes() {

    }
}
