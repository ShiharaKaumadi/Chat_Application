package lk.ijse.mychat.client;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

import static lk.ijse.mychat.client.LoginFormController.host;
import static lk.ijse.mychat.client.LoginFormController.username;

public class ChatUIFormController {
    public Pane paneHeader;
    public ImageView call;
    public AnchorPane txtClientArea;
    public ImageView btnSend;
    public TextField txtClientMessage;
    public ImageView emoji;
    public ImageView file;
    public Label lblUser;
    Socket socket =null;
    private Client client;

    public  void initialize() throws IOException {
        client = new Client(new Socket(host,5000),username);
        lblUser.setText(username);
        txtClientMessage.requestFocus();
        client.receiveMessageFromServer(txtClientArea);
    }

    public void callOnAction(MouseEvent mouseEvent) {
    }

    public void sendClientMsgOnAction(MouseEvent mouseEvent) throws IOException {
        String clientMessage = txtClientMessage.getText();
        if(!clientMessage.isEmpty()){
           Pane pane = new Pane();
            pane.setPadding(new Insets(10,5,5,10));
            pane.setLayoutX(320);
            Text text = new Text(clientMessage);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-background-color: #7E308E;"+"-fx-background-radius: 10px");
            textFlow.setPadding(new Insets(10,10,10,10));
            text.setFill(Color.color(1,1,1));
            pane.getChildren().add(textFlow);
            txtClientArea.getChildren().add(pane);
            client.sendMessageToServer(username+" : "+clientMessage);
            txtClientMessage.clear();
            pane.setPadding(new Insets(0,0,30,0)); ;// Set horizontal gap

        }
    }

    public void fileClickOnAction(MouseEvent mouseEvent) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {

            HBox hBox =  new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT );
            hBox.setPadding(new Insets(5,10,5,10));
            Image image = new Image("file:"+file.getAbsolutePath());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            hBox.getChildren().add(imageView);
            txtClientArea.getChildren().add(hBox);
            client.sendFileToServer(file, username);
            System.out.println("send "+file.getName());
        }
    }

    public void emojiOnAction(MouseEvent mouseEvent) throws IOException {

    }

    public static void addLabel(String messageFromClient, AnchorPane anchorPane) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,10,5,10));

        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(198,194,194);"+"-fx-background-radius: 10px");
        textFlow.setPadding(new Insets(5,10,5,10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                anchorPane.getChildren().add(hBox);
            }
        });
    }

    public static void addImage(File fileToDownload, AnchorPane anchorPane, String senderName) {
        HBox hBox = new HBox();
        VBox vBox1 = new VBox();
        vBox1.setStyle("-fx-background-color: rgb(198,194,194);"+"-fx-background-radius: 5px");
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,10,5,10));
        Text text = new Text(senderName);
        vBox1.getChildren().add(text);
        Image image = new Image("file:"+fileToDownload.getAbsolutePath());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        vBox1.setPadding(new Insets(5,10,5,10));
        vBox1.getChildren().add(imageView);
        hBox.getChildren().add(vBox1);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                anchorPane.getChildren().add(hBox);
            }
        });
    }

}
