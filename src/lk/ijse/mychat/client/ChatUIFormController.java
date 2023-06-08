package lk.ijse.mychat.client;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import lk.ijse.mychat.assets.emojis.Emoji;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;

import static lk.ijse.mychat.client.LoginFormController.host;
import static lk.ijse.mychat.client.LoginFormController.username;
public class ChatUIFormController {
    public Pane paneHeader;
    public ImageView call;
    public VBox txtClientArea;
    public JFXButton btnSend;
    public TextField txtClientMessage;
    public ImageView file;
    @FXML
    private  Label lblUser;
    public JFXButton emoji;
    public ImageView emojis;
    public JFXButton close;
    public HBox hBox;
    public JFXButton btnMinimize;
    public AnchorPane mainStage;
    @FXML
    private ScrollPane scrollPane;
    Socket socket = null;
    private Client client;
    private BufferedWriter writer;


/*Change the anchor pane which holds messaages into VBox because each message lies top of the first message.*/
/*The issue of the second message HBox laying on top of the first message HBox is because the HBox instances
are added directly to the AnchorPane without any layout constraints or positioning. As a result, they overlap
and stack on top of each other.
To solve this issue, use a VBox instead of an AnchorPane to hold the message HBox instances.
The VBox will automatically arrange its child nodes in a vertical stack, ensuring that each message
is placed below the previous one.*/

    /*New Version*/
    @FXML
    public void initialize() throws IOException {
        client = new Client(new Socket(host, 5000), username);
        lblUser.setText(username);
        txtClientMessage.requestFocus();
        client.receiveMessageFromServer(txtClientArea);

      /*  addLabel("", txtClientArea, username); // Pass lblUser as the third parameter
        addImage(file, txtClientArea, username); // Pass lblUser as the third parameter*/

        // Set CSS class to the ScrollPane
        /*scrollPane.getStyleClass().add(getResource("lk/ijse/mychat/assets/css/clientuiStyles.css").toExternalForm()); // Load external CSS file*/
    }

    public static void addLabel(String messageFromClient, VBox vBox, String sender) {
        Platform.runLater(() -> {
            HBox hBox = new HBox();
            Scene scene = new Scene(hBox);
            hBox.setSpacing(10); // Set the horizontal spacing within the HBox
            hBox.setPadding(new Insets(10));
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5, 10, 5, 5));
            Text text = new Text(messageFromClient);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-background-color: rgb(206,203,203);" + "-fx-background-radius: 10px");
            textFlow.setPadding(new Insets(5, 10, 5, 10));
            hBox.getChildren().add(textFlow);


            if (username != null && username.equals(sender)) {
                // Sender's message, align to the right
                hBox.setAlignment(Pos.CENTER_LEFT);
            } else {
                // Receiver's message, align to the left
                hBox.setAlignment(Pos.CENTER_RIGHT);
            }
            vBox.getChildren().add(hBox);
        });
    }

    public static void addImage(ImageView fileToDownload, VBox vBox, String senderName) {
        Platform.runLater(() -> {
            HBox hBox = new HBox();
            Scene scene = new Scene(hBox);

            VBox vBox1 = new VBox();
            hBox.setSpacing(5); // Set the horizontal spacing within the HBox
            hBox.setPadding(new Insets(10));
            vBox1.setStyle("-fx-background-color: rgb(198,194,194);" + "-fx-background-radius: 5px");
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5, 10, 5, 10));
            Text text = new Text(senderName);
            vBox1.getChildren().add(text);
            Image image = new Image("file:" + fileToDownload.getImage());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            vBox1.setPadding(new Insets(5, 10, 5, 10));
            vBox1.getChildren().add(imageView);
            hBox.getChildren().add(vBox1);

            if (senderName != null && senderName.equals(username)) {
                // Sender's message, align to the right
                hBox.setAlignment(Pos.CENTER_LEFT);
            } else {
                // Receiver's message, align to the left
                hBox.setAlignment(Pos.CENTER_RIGHT);
            }

            vBox.getChildren().add(hBox);
        });
    }





    private static String getFileExtension(Path filePath) {
        String fileName = filePath.getFileName().toString();
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }


    public void callOnAction(MouseEvent mouseEvent) {
    }

    public void fileClickOnAction(MouseEvent mouseEvent) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        Platform.runLater(() -> {
            if (file != null) {
                HBox hBox = new HBox();
                Scene scene = new Scene(hBox);

                hBox.setSpacing(5); // Set the horizontal spacing within the HBox
                hBox.setPadding(new Insets(10));
                hBox.setAlignment(Pos.BASELINE_LEFT);
                hBox.setPadding(new Insets(5, 10, 5, 10));
                Image image = new Image("file:" + file.getAbsolutePath());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                hBox.getChildren().add(imageView);
                txtClientArea.getChildren().add(hBox);
                client.sendFileToServer(file, username);

                System.out.println("send " + file.getName());

                if (file != null) {
                    Path filePath = file.toPath();
                    String fileExtension = getFileExtension(filePath);
                    Text text = new Text(file + fileExtension);
                    System.out.println("File Extension: " + fileExtension);
                    HBox hBox1 = new HBox();
                    Scene scene1 = new Scene(hBox1);

                    hBox1.setSpacing(5); // Set the horizontal spacing within the HBox
                    hBox1.setPadding(new Insets(10));
                    hBox1.setAlignment(Pos.CENTER_RIGHT);
                    hBox1.setPadding(new Insets(5, 10, 5, 10));
                    hBox1.getChildren().add(text);
                    txtClientArea.getChildren().add(hBox1);
                    client.sendDocumentsToServer(file, username);

                }
            }
        });
    }

    public void btnSendOnAction(ActionEvent actionEvent) {
        String clientMessage = txtClientMessage.getText();
        Platform.runLater(() -> {
            if (!clientMessage.isEmpty()) {
                HBox pane = new HBox();
                pane.setPadding(new Insets(5, 10, 5, 10));
                pane.setLayoutX(320);
                Text text = new Text(clientMessage);
                TextFlow textFlow = new TextFlow(text);
                textFlow.setStyle("-fx-background-color: #7E308E;" + "-fx-background-radius: 10px");
                textFlow.setPadding(new Insets(5, 10, 5, 10));
                text.setFill(Color.color(1, 1, 1));
                pane.getChildren().add(textFlow);
                txtClientArea.getChildren().add(pane);
                client.sendMessageToServer(username + " : " + clientMessage);
                txtClientMessage.clear();
                pane.setSpacing(20); // Set the horizontal spacing within the HBox
                pane.setPadding(new Insets(10));
                pane.setStyle(" -fx-vgap: 10px;");
                // Set horizontal gap

            }
        });
    }


    private String encodeEmojis(String message) {
        // Replace Unicode representations with actual Unicode characters
        message = message.replace("U+1F600", "\uD83D\uDE00");
        // Add more emoji replacements as needed

        return message;
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void emojiOnAction(MouseEvent mouseEvent) {
        // Assuming an ImageView called emojiImageView and a TextField called textField
        emojis.setOnMouseClicked(event -> {
            // Get the selected emoji (unicode representation) from the Emoji class or any other source
            Emoji emoji1 = new Emoji();
            // Replace with your actual way of getting the selected emoji
            String smileEmoji = emoji1.getSmileEmoji();

            // Set the selected emoji in the text field
            txtClientMessage.setText(smileEmoji);
        });

        // Assuming you have a Button called sendButton
        try {
            btnSend.setOnAction(event -> {
                // Get the emoji text from the text field
                String emojiText = txtClientMessage.getText();

                // Perform the necessary actions to send the emoji to the server or receiver
                // For example, you can call a method on your client object to send the emoji
                client.sendMessageToServer(username + ": " + emojiText);
                client.receiveMessageFromServer(txtClientArea); // Update the receiver's UI
            });
        }catch (NullPointerException e){
            System.out.println("Please Click inside the Image Correctly");
        }

    }




    public void btnMinimizeOnAction(ActionEvent actionEvent) {
        btnMinimize.setOnAction(event -> {
            Window window = mainStage.getScene().getWindow();
            System.out.println("Caught Window: " + window);
            if (window instanceof Stage) {
                Stage stage = (Stage) window;
                stage.setIconified(true); // Minimize the window
            }
        });
    }

}
