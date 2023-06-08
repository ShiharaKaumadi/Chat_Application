package lk.ijse.mychat.server;

import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFormController {
    public AnchorPane serverPane;
    public Pane txtArea;
    public TextField txtMessage;
    public ImageView emoji;
    public ImageView file;
    public ImageView btnSend;
    Socket accept = null;


/*    public void initialize(){
        new Thread(()->{
            try {
                ServerSocket serverSocket = new ServerSocket(5000);
                System.out.println("Server Started");
                accept = serverSocket.accept();
                System.out.println("Client Connected");
                InputStreamReader inputStreamReader =
                        new InputStreamReader(accept.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String record = bufferedReader.readLine();
                System.out.println(record);

                    while (true) {
                        if (txtMessage.equals("exit")) {
                            Socket socket = serverSocket.accept();
                            System.out.println("client connected ");
                            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                            BufferedReader bfr = new BufferedReader(isr);
                            String name = bfr.readLine();
                            //System.out.println(name);
                            HandlingEvents client = new HandlingEvents(socket, name);
                            Thread thread = new Thread((Runnable) client);
                            thread.start();
                            System.exit(0);
                        }
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }*/

    public void emojiOnAction(MouseEvent mouseEvent) {
    }

    public void selectFileOnAction(MouseEvent mouseEvent) {
    }

    public void sendOnAction(MouseEvent mouseEvent) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4000);
        System.out.println("Server Started");
        accept = serverSocket.accept();
        PrintWriter printWriter = new PrintWriter(accept.getOutputStream());
        printWriter.println(txtMessage.getText());
        printWriter.flush();
        String clientMessage = txtMessage.getText();
        if (!clientMessage.isEmpty()) {
            Pane pane = new Pane();
            pane.setPadding(new Insets(10, 5, 5, 10));
            pane.setLayoutX(10);
            Text text = new Text(clientMessage);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-background-color: #0c78b2;" + "-fx-background-radius: 10px");
            textFlow.setPadding(new Insets(10, 10, 10, 10));
            text.setFill(Color.color(1, 1, 1));
            pane.getChildren().add(textFlow);
            txtArea.getChildren().add(pane);
            txtMessage.clear();
            pane.setPadding(new Insets(0, 0, 30, 0));
            // Set horizontal gap


        }

    }
}
