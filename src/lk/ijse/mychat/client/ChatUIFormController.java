package lk.ijse.mychat.client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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
            txtClientMessage.clear();
            pane.setPadding(new Insets(0,0,30,0)); ;// Set horizontal gap

        }
    }

    public void fileClickOnAction(MouseEvent mouseEvent) {
    }

    public void emojiOnAction(MouseEvent mouseEvent) throws IOException {

    }

    public static void addLabel(String messageFromClient, AnchorPane anchorPane) {
    }

    public static void addImage(File fileToDownload, AnchorPane anchorPane, String senderName) {
    }
}
