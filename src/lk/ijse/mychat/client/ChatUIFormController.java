package lk.ijse.mychat.client;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatUIFormController {
    public Pane paneHeader;
    public ImageView call;
    public TextArea txtClientArea;
    public ImageView btnSend;
    public TextField txtClientMessage;
    public ImageView emoji;
    public ImageView file;
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
        printWriter.write(txtClientArea.getSelectedText());
        printWriter.flush();
    }

    public void fileClickOnAction(MouseEvent mouseEvent) {
    }

    public void emojiOnAction(MouseEvent mouseEvent) throws IOException {

    }
}
