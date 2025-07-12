package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
      
        Parent root = FXMLLoader.load(getClass().getResource("/application/hangman.fxml"));
        Scene scene = new Scene(root, 600, 600);

        
        Image icon = new Image(getClass().getResource("/images/pokeball.png").toExternalForm());
        stage.getIcons().add(icon);

        stage.setTitle("Hangman Game");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
