import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("calendar.fxml"));
        GridPane calendarGP = (GridPane) root.lookup("#calendarGP");
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                calendarGP.add(FXMLLoader.load(getClass().getResource("cell.fxml")), c, r);
            }
        }
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
