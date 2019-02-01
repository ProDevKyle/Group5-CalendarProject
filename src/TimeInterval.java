import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Date;

public class TimeInterval {
    private Date start, end;

    public TimeInterval(Date start, Date end) {
        this.start = start;
        this.end = end;
    }
}
