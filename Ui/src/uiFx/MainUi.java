package uiFx;

import Q2_Parser.Grammar;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import uiFx.controllers.MainUiController;

public class MainUi extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("LL1Parser");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("res/MainUiFx.fxml"));
        Parent root = loader.load();
        primaryStage.setResizable(false);
        Scene mainScene = new Scene(root);
        primaryStage.setScene(mainScene);
        primaryStage.show();

    }
}
