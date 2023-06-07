package lk.ijse.mychat.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerInitializer extends Application {

    public static void main(String[] args) throws IOException {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene
                (new Scene(FXMLLoader.load(getClass().getResource("ServerForm.fxml"))));
        primaryStage.show();



    }

}
