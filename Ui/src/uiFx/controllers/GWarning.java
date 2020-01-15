package uiFx.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GWarning {
    public Button buttonQuit;

    public void quit(ActionEvent actionEvent) {
        Stage stage = (Stage) buttonQuit.getScene().getWindow();
        stage.close();
    }
}
