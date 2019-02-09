import javafx.animation.AnimationTimer;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.List;

import static java.lang.Float.MAX_VALUE;
import static javafx.scene.layout.Priority.ALWAYS;

public class Controller {
    private final int[] DAYS_IN_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private final String[] MONTH_COLOR = {"9E0002", "7E5798", "7EFFD4", "FEFEFE", "2F8151", "EAE0C7", "B5273F", "74B003", "0066A4", "ABC5C4", "FFC87D", "35DDDE"};
    private int displayedMonth;
    private AnchorPane currentDayAP;
    private List<Node> dayAPs;
    private static ChoiceBox<String> monthCB = new ChoiceBox<>();
    @FXML
    private Label dateL;
    @FXML
    private GridPane calendarGP;
    @FXML
    private HBox calendarHB;
    @FXML
    private Button prevMonthB, nextMonthB;
    @FXML
    private AnchorPane NotesTab;

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
        new CSV();
        new NotesCSV();
        dayAPs = calendarGP.getChildren();

        HBox.setHgrow(monthCB, ALWAYS);
        calendarHB.getChildren().set(1, monthCB);
        monthCB.setMaxHeight(MAX_VALUE);
        monthCB.setMaxWidth(MAX_VALUE);
        monthCB.setTooltip(new Tooltip("Select a month"));
        monthCB.setItems(FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
        monthCB.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_val, Number new_val) -> {
                    displayedMonth = (int) new_val;
                    displayMonth();
                    prevMonthB.setDisable(displayedMonth == Calendar.JANUARY);
                    nextMonthB.setDisable(displayedMonth == Calendar.DECEMBER);
                }
        );
        prevMonthB.setOnAction(e -> monthCB.getSelectionModel().select(monthCB.getSelectionModel().getSelectedIndex() - 1));
        nextMonthB.setOnAction(e -> monthCB.getSelectionModel().select(monthCB.getSelectionModel().getSelectedIndex() + 1));

        ListView<String> notesLV = new ListView<>();
        NotesTab.getChildren().add(notesLV);
        notesLV.setEditable(true);
        AnchorPane.setTopAnchor(notesLV, 0.0);
        AnchorPane.setRightAnchor(notesLV, 0.0);
        AnchorPane.setBottomAnchor(notesLV, 0.0);
        AnchorPane.setLeftAnchor(notesLV, 0.0);
        notesLV.setCellFactory(NoteCell::new);
        ContextMenu cm = new ContextMenu();
        MenuItem add = new MenuItem("Add Note");
        cm.getItems().add(add);
        notesLV.setContextMenu(cm);
        add.setOnAction(e -> {
            notesLV.getItems().add("New Note");
            new Note("New Note");
            NotesCSV.saveNotesCSV();
        });

        ObservableList<String> items = FXCollections.observableArrayList();
        for (Note n : Note.getNotes())
            items.add(n.getContent());
        notesLV.setItems(items);

        new AnimationTimer() {
            Long time = System.currentTimeMillis();

            @Override
            public void handle(long now) {
                if (System.currentTimeMillis() - time > 1000) {
                    time = System.currentTimeMillis();
                    dateL.setText("Today is " + getWeekday() + ", " + getMonth() + " " + getDay() + ", " + getYear() + "." + Calendar.getInstance().getTime().toString().substring(10, 19));
                }
            }
        }.start();
    }

    static void selectMonthNow() {
        monthCB.getSelectionModel().select(Calendar.getInstance().get(Calendar.MONTH));
    }

    private void displayMonth() {
        monthCB.setStyle("-fx-background-color:#" + MONTH_COLOR[displayedMonth]);

        if (currentDayAP != null)
            currentDayAP.setStyle("-fx-background-color:none");

        int offset = 3;
        for (int m = 0; m < displayedMonth; m++)
            offset = (offset + DAYS_IN_MONTH[m]) % 7;

        for (int i = 0, day = 0; i < dayAPs.size(); i++) {
            if (dayAPs.get(i) instanceof AnchorPane) {
                AnchorPane dayAP = (AnchorPane) dayAPs.get(i);
                Label dayL = (Label) dayAP.getChildren().get(1);

                ListView<String> eventsLV = new ListView<>();
                eventsLV.setEditable(true);
                AnchorPane.setTopAnchor(eventsLV, 0.0);
                AnchorPane.setRightAnchor(eventsLV, 0.0);
                AnchorPane.setBottomAnchor(eventsLV, 0.0);
                AnchorPane.setLeftAnchor(eventsLV, 0.0);
                eventsLV.setCellFactory(EventCell::new);
                ContextMenu cm = new ContextMenu();
                MenuItem add = new MenuItem("Add Event");
                cm.getItems().add(add);
                eventsLV.setContextMenu(cm);
                final int eventDay = day;
                add.setOnAction(e -> {
                    eventsLV.getItems().add("New Event");
                    new Event(
                            new Date(
                                    displayedMonth,
                                    eventDay
                            ),
                            "New Event"
                    );
                    CSV.saveCSV();
                });

                if (i >= offset && i < DAYS_IN_MONTH[displayedMonth] + offset) {
                    dayAP.setVisible(true);
                    dayL.setText("" + (day + 1));

                    ObservableList<String> items = FXCollections.observableArrayList();
                    for (Event e : Event.getEvents()) {
                        if (e.getDate().getMonth() == displayedMonth && e.getDate().getDay() == day)
                            items.add(e.getName());
                    }
                    eventsLV.setItems(items);
                    dayAP.getChildren().set(0, eventsLV);
                    day++;
                } else
                    dayAP.setVisible(false);
            }
        }

        if (displayedMonth == Calendar.getInstance().get(Calendar.MONTH)) {
            currentDayAP = (AnchorPane) dayAPs.get(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + offset - 1);
            currentDayAP.setStyle("-fx-background-color:#BFBFFF");
        }
    }

    static class EventCell extends TextFieldListCell<String> {
        private ContextMenu contextMenu;
        private String prevText;

        EventCell(ListView lv) {
            setConverter(TextFormatter.IDENTITY_STRING_CONVERTER);
            contextMenu = new ContextMenu();
            MenuItem delete = new MenuItem("Delete");
            MenuItem edit = new MenuItem("Edit");
            contextMenu.getItems().setAll(edit, delete);
            delete.setOnAction(e -> {
                try {
                    String text = getText();
                    lv.getItems().remove((getIndex()));
                    for (Event event : Event.getEvents()) {
                        if (event.getName().equals(text)) {
                            Event.getEvents().remove(event);
                            CSV.saveCSV();
                        }
                    }
                } catch (ConcurrentModificationException ex) {
                    System.out.println();
                }
            });
            edit.setOnAction(e -> lv.edit(getIndex()));
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setContextMenu(null);
                setText(null);
                setGraphic(null);
            } else {
                setContextMenu(contextMenu);
            }
        }

        @Override
        public void startEdit() {
            prevText = getText();
            super.startEdit();
        }

        @Override
        public void commitEdit(String newValue) {
            super.commitEdit(newValue);
            for (Event event : Event.getEvents()) {
                if (event.getName().equals(prevText)) {
                    event.setName(getText());
                    CSV.saveCSV();
                }
            }
        }
    }

    static class NoteCell extends TextFieldListCell<String> {
        private ContextMenu contextMenu;
        private String prevText;

        NoteCell(ListView lv) {
            setConverter(TextFormatter.IDENTITY_STRING_CONVERTER);
            contextMenu = new ContextMenu();
            MenuItem delete = new MenuItem("Delete");
            MenuItem edit = new MenuItem("Edit");
            contextMenu.getItems().setAll(edit, delete);
            delete.setOnAction(e -> {
                try {
                    String text = getText();
                    lv.getItems().remove((getIndex()));
                    for (Note note : Note.getNotes()) {
                        if (note.getContent().equals(text)) {
                            Note.getNotes().remove(note);
                            NotesCSV.saveNotesCSV();
                        }
                    }
                } catch (ConcurrentModificationException ex) {
                    System.out.println();
                }
            });
            edit.setOnAction(e -> lv.edit(getIndex()));
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setContextMenu(null);
                setText(null);
                setGraphic(null);
            } else {
                setContextMenu(contextMenu);
            }
        }

        @Override
        public void startEdit() {
            prevText = getText();
            super.startEdit();
        }

        @Override
        public void commitEdit(String newValue) {
            super.commitEdit(newValue);
            for (Note note : Note.getNotes()) {
                if (note.getContent().equals(prevText)) {
                    note.setContent(getText());
                    NotesCSV.saveNotesCSV();
                }
            }
        }
    }
}
