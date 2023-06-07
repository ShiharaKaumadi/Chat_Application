package lk.ijse.mychat.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
/*managing client connections and handling message communication between multiple clients*/
public class HandlingEvents implements Runnable {
    public static ArrayList<HandlingEvents> handlingEvents = new ArrayList<>();//represents connected clients.
    //handling the network connection and client information-->socket, bufferReader,bufferWriter,client
    public Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String client;

    public HandlingEvents(Socket socket, String client) {// the client's socket connection and client name
        try {
            this.socket = socket;
            // input and output streams initialized for socket connection
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.client = client;
            //add connected clients arraylist
            handlingEvents.add(this);
            //sends a message to all connected clients except the sender
            sendMessage(client + " connected!");
        } catch (IOException e) {
            closeAll(socket, bufferedWriter, bufferedReader);
        }
    }

    // Close all resources (socket, bufferedWriter, bufferedReader)
    private void closeAll(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
        removeClient();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Send message to all connected clients except the sender
    private void sendMessage(String message) {
        //iterates through the arraylist list and writes the message to the bufferedWriter of each connected client.
        for (HandlingEvents handle : handlingEvents) {
            try {
                if (!handle.client.equals(client)) {
                    handle.bufferedWriter.write(message);
                    handle.bufferedWriter.newLine();
                    handle.bufferedWriter.flush();
                }
            } catch (IOException e) {
                //If any exceptions occur during socket operations or message sending, close the socket
                closeAll(socket, bufferedWriter, bufferedReader);
            }
        }
    }

    @Override
    public void run() {
        String clientMessage;

        while (socket.isConnected()) {
            try {
                clientMessage = bufferedReader.readLine();
                if (clientMessage.split(":")[0].equals("FILE")) {
                    downloadFile(clientMessage.split(":")[1]);
                } else {
                    sendMessage(clientMessage);
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
                closeAll(socket, bufferedWriter, bufferedReader);
                break;
            }
        }

    }

    private void downloadFile(String name) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        int fileNameLength = dataInputStream.readInt();
        if (fileNameLength > 0) {
            System.out.println(fileNameLength);
            byte[] fileNameByte = new byte[fileNameLength];
            dataInputStream.readFully(fileNameByte, 0, fileNameByte.length);
            String fileName = new String(fileNameByte);
            System.out.println(fileName);

            int fileContentLength = dataInputStream.readInt();
            if (fileContentLength > 0) {
                byte[] fileContentByte = new byte[fileContentLength];
                dataInputStream.readFully(fileContentByte, 0, fileContentLength);

                File fileToDownload = new File(fileName);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(fileToDownload);
                    fileOutputStream.write(fileContentByte);
                    fileOutputStream.close();
                    System.out.println("download");

                    sendFile(fileToDownload, name);
                } catch (IOException e) {
                    System.out.println("download " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

        public void removeClient () {
            handlingEvents.remove(this);
            sendMessage(client + " left");
        }


        private void sendFile (File file, String name){
            System.out.println(name+" image from");
            for(HandlingEvents clients : handlingEvents){
                try{
                    if (!clients.client.equals(name)){
                        FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
                        DataOutputStream dataOutputStream = new DataOutputStream(clients.socket.getOutputStream());
                        PrintWriter printWriter = new PrintWriter(clients.socket.getOutputStream());
                        printWriter.println("FILE:"+name);
                        printWriter.flush();

                        String fileName = file.getName();
                        byte[] fileNameBytes = fileName.getBytes();
                        byte[] fileContentBytes = new byte[(int)file.length()];
                        fileInputStream.read(fileContentBytes);

                        dataOutputStream.writeInt(fileNameBytes.length);
                        dataOutputStream.write(fileNameBytes);
                        dataOutputStream.writeInt(fileContentBytes.length);
                        dataOutputStream.write(fileContentBytes);
                        dataOutputStream.flush();
                        System.out.println(clients.client+" to broadcast "+fileName);

                    }
                }catch (IOException e){
                    System.out.println("upload "+e.getMessage());
                    closeAll(socket,bufferedWriter,bufferedReader);
                }
            }
        }

}