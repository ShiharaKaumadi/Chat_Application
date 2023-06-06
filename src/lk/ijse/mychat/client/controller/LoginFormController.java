package lk.ijse.mychat.client.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public ImageView logoImg;
    public Text loginTitle;
    public TextField txtUsername;
    public TextField txtHost;
    public JFXButton btnLogin;
    public AnchorPane loginPage;
    public Pane content;
    private String host;
    private String username;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        if (!txtUsername.getText().equals("")){
            username = txtUsername.getText();
            if (!txtHost.getText().equals("")){
                host=txtHost.getText();
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("views/ChatUIForm.fxml"))));
                stage.centerOnScreen();
                stage.show();
            }
        }
    }
}
