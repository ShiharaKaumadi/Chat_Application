package lk.ijse.mychat.client;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
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
    public ImageView close;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        if (!txtUsername.getText().equals("")) {
            String username = txtUsername.getText();

            if (!txtHost.getText().equals("")) {
               Stage log = (Stage) loginPage.getScene().getWindow();
                log.close();
                String host = txtHost.getText();
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("ChatUIForm.fxml"))));
                stage.setResizable(false);
                stage.centerOnScreen();
                stage.show();
            } else {
               Stage log = (Stage) loginPage.getScene().getWindow();
                log.close();

                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("ChatUIForm.fxml"))));
                stage.setResizable(false);
                stage.centerOnScreen();
                stage.show();
            }
        } else {

        }
    }
    public void closeOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }
}



