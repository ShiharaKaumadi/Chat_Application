package lk.ijse.mychat.client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatUIFormController {
    public Pane paneHeader;
    public ImageView call;
    public Pane txtClientArea;
    public ImageView btnSend;
    public TextField txtClientMessage;
    public ImageView emoji;
    public ImageView file;
    public Label lblUser;
    Socket socket =null;


    public  void initialize() throws IOException {
        socket = new Socket("localhost",5000);
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String record = bufferedReader.readLine();
        System.out.println(record);
    }

    public void callOnAction(MouseEvent mouseEvent) {
    }

    public void sendClientMsgOnAction(MouseEvent mouseEvent) throws IOException {
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        printWriter.println(txtClientMessage.getText());
        printWriter.flush();
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
}
