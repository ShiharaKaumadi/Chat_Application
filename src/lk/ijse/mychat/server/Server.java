package lk.ijse.mychat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private final ServerSocket serverSocket;//listens incomming client connections

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);// Create a new serversocket on port 5000
        Server server = new Server(serverSocket);// Create an object of the Server class with the serversocket
        server.startServer();//call startServer() method

    }

    /*server that listens for incoming client connections on port 5000.
     When a client connects, the server accepts the connection, reads
     the client's name from the input stream, and creates a new thread
     to handle the client's events. The server continues running and
     accepting new client connections until the server socket is closed.*/
    public void startServer() {
        try {
            // loop until the server as long as the serversocket is not closed
            while (!serverSocket.isClosed()) {
                /*accept()-->ServerSocket method listens for a connection to be made to this socket and accepts it.
                 The method blocks until a connection is made.
                 */
                Socket socket = serverSocket.accept();// Wait for a client to connect and accept the connection
                System.out.println("client connected !! accept the connection");
                /*Create a reader to read data from the client socket*/
                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());

                /*BufferedReader class-->Reads text from a character-input stream, buffering characters so as to provide for
                 the efficient reading of characters, arrays, and lines.*/
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader); /*Buffer the input reader for efficient reading*/
                String name = bufferedReader.readLine();// Read the client's name from the input stream

                // Create a new thread to handle the client's events
                HandlingEvents handlingEvents = new HandlingEvents(socket, name);
                Thread thread = new Thread(handlingEvents);// Create a new thread to handle the client's events
                thread.start();// Start the thread, which will execute the run method of the handlingEvents instance
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
