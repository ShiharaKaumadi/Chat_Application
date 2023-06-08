package lk.ijse.mychat.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("LoginForm.fxml")));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Play Tech Chat Group");
        Image iconImage = new Image("lk/ijse/mychat/assets/images/playTech.png");
        // Set the icon on the stage
        primaryStage.getIcons().add(iconImage);
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}
