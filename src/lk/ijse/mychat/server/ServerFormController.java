package lk.ijse.mychat.server;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFormController {
    public AnchorPane serverPane;
    public TextArea txtArea;
    public TextField txtMessage;
    public ImageView emoji;
    public ImageView file;
    public ImageView btnSend;
    Socket accept = null;


    public void initialize(){
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

                  /*  while (true) {
                        if (txtMessage.equals("exit")) {
                            *//*Socket socket = serverSocket.accept();
                            System.out.println("client connected ");
                            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
                            BufferedReader bfr = new BufferedReader(isr);
                            String name = bfr.readLine();
                            //System.out.println(name);
                            Handle client = new Handle(socket, name);
                            Thread thread = new Thread(client);
                            thread.start();*//*
                            System.exit(0);
                        }
                    }
*/
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void emojiOnAction(MouseEvent mouseEvent) {
    }

    public void selectFileOnAction(MouseEvent mouseEvent) {
    }

    public void sendOnAction(MouseEvent mouseEvent) throws IOException {

        PrintWriter printWriter = new PrintWriter(accept.getOutputStream());
        printWriter.println(txtMessage.getText());
        printWriter.flush();

    }
}
