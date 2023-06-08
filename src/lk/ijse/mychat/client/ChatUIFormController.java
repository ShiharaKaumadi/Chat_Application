package lk.ijse.mychat.client;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    public AnchorPane txtClientArea;
    public JFXButton btnSend;
    public TextField txtClientMessage;
    public ImageView file;
    public Label lblUser;
    public JFXButton emoji;
    public ImageView emojis;
    public JFXButton close;
    public HBox hBox;
    Socket socket = null;
    private Client client;
    private BufferedWriter writer;


    public static void addLabel(String messageFromClient, AnchorPane anchorPane) {
        Platform.runLater(() -> {
            HBox hBox = new HBox();
            Scene scene = new Scene(hBox);
            scene.getStylesheets().add("lk/ijse/mychat/assets/css/clientuiStyles.css");
            hBox.getStylesheets().add(".hBox");
            hBox.setSpacing(5); // Set the horizontal spacing within the HBox
            hBox.setPadding(new Insets(10));
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5, 10, 5, 10));

            Text text = new Text(messageFromClient);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-background-color: rgb(206,203,203);" + "-fx-background-radius: 10px");
            textFlow.setPadding(new Insets(5, 10, 5, 10));
            hBox.getChildren().add(textFlow);

            anchorPane.getChildren().add(hBox);
        });
    }

    public static void addImage(File fileToDownload, AnchorPane anchorPane, String senderName) {
        Platform.runLater(() -> {
            HBox hBox = new HBox();
            Scene scene = new Scene(hBox);
            scene.getStylesheets().add("lk/ijse/mychat/assets/css/clientuiStyles.css");
            hBox.getStylesheets().add(".hBox");
            VBox vBox1 = new VBox();
            hBox.setSpacing(5); // Set the horizontal spacing within the HBox
            hBox.setPadding(new Insets(10));
            vBox1.setStyle("-fx-background-color: rgb(198,194,194);" + "-fx-background-radius: 5px");
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5, 10, 5, 10));
            Text text = new Text(senderName);
            vBox1.getChildren().add(text);
            Image image = new Image("file:" + fileToDownload.getAbsolutePath());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            vBox1.setPadding(new Insets(5, 10, 5, 10));
            vBox1.getChildren().add(imageView);
            hBox.getChildren().add(vBox1);
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

    public void initialize() throws IOException {
        client = new Client(new Socket(host, 5000), username);
        lblUser.setText(username);
        txtClientMessage.requestFocus();
        client.receiveMessageFromServer(txtClientArea);
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
                scene.getStylesheets().add("lk/ijse/mychat/assets/css/clientuiStyles.css");
                hBox.getStylesheets().add(".hBox");
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
                    scene1.getStylesheets().add("lk/ijse/mychat/assets/css/clientuiStyles.css");
                    hBox1.getStylesheets().add(".hBox1");
                    hBox1.setSpacing(5); // Set the horizontal spacing within the HBox
                    hBox1.setPadding(new Insets(10));
                    hBox1.setAlignment(Pos.BASELINE_LEFT);
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

    public void btnSendEmojiOnAction(ActionEvent actionEvent) throws IOException {
        Emoji emoji1 = new Emoji();
        String smileEmoji = emoji1.getSmileEmoji();
        txtClientMessage.setText(smileEmoji);
        encodeEmojis(smileEmoji);
        client.sendMessageToServer(username + " : " + smileEmoji);


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
    }


}
