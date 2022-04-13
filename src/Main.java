import Utility.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Load first view and set title of program
        Parent root = FXMLLoader.load(getClass().getResource("View/ViewLoginMenu.fxml"));
        primaryStage.setTitle("Asset Manager");
        primaryStage.setScene(new Scene(root, 400, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        JDBC.openConnection();

        //Begin the JavaFX application and lifecycle
        launch(args);

        JDBC.closeConnection();
    }
}
