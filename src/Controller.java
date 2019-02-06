import javafx.animation.AnimationTimer;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.util.Calendar;
import java.util.List;

public class Controller {
    private int[] DAYS_IN_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private int currentMonth;
    private AnchorPane currentDayAP;
    private List<Node> dayAPs;
    private BackEnd events = new BackEnd("src/events.csv");

    @FXML
    private Label dateL;
    @FXML
    private GridPane calendarGP;
    @FXML
    private ChoiceBox monthCB;
    @FXML
    private Button prevMonthB, nextMonthB;
    @FXML
    private AnchorPane NotesTab;
    @FXML
    private VBox vbox;

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

    @FXML
    public void initialize() {
        dayAPs = calendarGP.getChildren();
        monthCB.setTooltip(new Tooltip("Select a month"));
        monthCB.setItems(FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
        monthCB.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_val, Number new_val) -> {
                    currentMonth = (int) new_val;
                    displayMonth();
                }
        );
        prevMonthB.setOnAction(e -> monthCB.getSelectionModel().select(monthCB.getSelectionModel().getSelectedIndex() - 1));
        nextMonthB.setOnAction(e -> monthCB.getSelectionModel().select(monthCB.getSelectionModel().getSelectedIndex() + 1));
        dateL.setText("Today is " + getWeekday() + ", " + getMonth() + " " + getDay() + ", " + getYear() + ".");
        new AnimationTimer() {
            Long time = System.currentTimeMillis();

            @Override
            public void handle(long now) {
                if (System.currentTimeMillis() - time > 0) {
                    time = System.currentTimeMillis();
                    monthCB.getSelectionModel().select(Calendar.getInstance().get(Calendar.MONTH));
                    this.stop();
                }
            }
        }.start();
    }

    private void displayMonth() {
        if (currentDayAP != null)
            currentDayAP.setStyle("-fx-background-color:none");
        int offset = 3;
        for (int i = 0; i < currentMonth; i++)
            offset = (offset + DAYS_IN_MONTH[i]) % 7;
        for (int i = 0, currentDay = 0; i < dayAPs.size(); i++) {
            if (dayAPs.get(i) instanceof AnchorPane) {
                AnchorPane anchorPane = (AnchorPane) dayAPs.get(i);
                ListView listView = (ListView) anchorPane.getChildren().get(1);
                Label label = (Label) anchorPane.getChildren().get(0);
                if (i >= offset && i < DAYS_IN_MONTH[currentMonth] + offset) {
                    label.setVisible(true);
                    label.setText("" + ++currentDay);
                    ObservableList<String> items = FXCollections.observableArrayList();
                    for (int j = 0; j < events.size(); j++) {
                        if (events.getMonths().get(j).equals(currentMonth) && events.getDays().get(j).equals(currentDay - 1)) {
                            items.add(events.getNames().get(j));
                        }
                    }
                    listView.setItems(items);
                } else
                    label.setVisible(false);
            }
        }
        if (currentMonth == Calendar.getInstance().get(Calendar.MONTH)) {
            currentDayAP = (AnchorPane) dayAPs.get(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + offset - 1);
            currentDayAP.setStyle("-fx-background-color:#BFBFFF");
        }
    }

    @FXML
    private void addNotes() {
        TextField textField = new TextField();
        textField.setPrefWidth(1000);
        textField.setTranslateY(100);
        vbox.getChildren().add(textField);
    }

    @FXML
    private void removeNotes() {
        
    }
}
