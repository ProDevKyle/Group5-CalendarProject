import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.Calendar;

import java.awt.event.ActionEvent;

public class Controller {
    private int[] DAYS_IN_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    @FXML
    private Label datetime;

    @FXML
    public void initialize() {
        new AnimationTimer() {
            Long time = System.currentTimeMillis();
            @Override
            public void handle(long now) {
                if (System.currentTimeMillis() - time > 1000) {
                    time = System.currentTimeMillis();
                    datetime.setText(Calendar.getInstance().getTime().toString());
                }
            }
        }.start();
    }

    public void showNotes(ActionEvent actionEvent) {

    }
}
