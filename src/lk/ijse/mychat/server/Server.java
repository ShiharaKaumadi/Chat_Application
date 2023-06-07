package lk.ijse.mychat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;



public class Server {
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(5000);
        Server server  = new Server(serverSocket);
        server.startServer();

    }

    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("client connected ");
                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String name = bufferedReader.readLine();
                HandlingEvents handlingEvents = new HandlingEvents(socket, name);
                Thread thread = new Thread(handlingEvents);
                thread.start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
