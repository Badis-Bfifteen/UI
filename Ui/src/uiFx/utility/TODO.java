package uiFx.utility;

import Q1_Lexer.Token;
import Q2_Parser.ParsingTable;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

import static Q2_Parser.Parser.inputOfToken;

public class TODO {

    public static void toDO(TextArea textArea, VBox vBoxparse, VBox vBoxinput, Button button, Token[] input, ParsingTable parsingTable){
        // long running operation runs on different thread
        Thread thread = new Thread(new Runnable() {
            String textAreaText;
            Label labelVBoxInput;
            Label labelVBoxParse;
            boolean buttonOn =false;
            boolean addInput =true;
            boolean addParser =true;
            public void update(String textAreaText,Label labelVBoxInput,Label labelVBoxParse,boolean buttonOn,boolean addInput,boolean addParser) {
                if(addInput)vBoxinput.getChildren().add(labelVBoxInput);
                else vBoxinput.getChildren().remove(labelVBoxInput);
                if(addParser)vBoxparse.getChildren().add(labelVBoxParse);
                else vBoxparse.getChildren().remove(labelVBoxParse);
                textArea.setText(textAreaText);
                button.setDisable(!buttonOn);
            }

            @Override
            public void run() {
                Runnable updater = new Runnable() {


                    @Override
                    public void run() {
                        update(textAreaText,labelVBoxInput,labelVBoxParse,buttonOn,addInput,addParser);
                    }
                };
                //here code
                Platform.runLater(updater);
                textAreaText ="Filling inputStack !";
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                // UI update is run on the Application thread
                Platform.runLater(updater);
                Token[]tokens= input.clone();
                ArrayList<Label> inputArray = new ArrayList<>();
                for (Token token: tokens){
                    Label labelFor=new Label();
                    labelFor.setStyle("-fx-border-insets:1;-fx-border-width:2;-fx-border-radius: 5;-fx-border-style: solid;-fx-font-size: 13px;-fx-font-weight: bold;");
                    labelFor.setPrefSize(84,25);
                    labelFor.setAlignment(Pos.CENTER);
                    labelFor.setText(inputOfToken(token));
                    inputArray.add(labelFor);
                    labelVBoxInput=labelFor;
                    Platform.runLater(updater);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }
                    Platform.runLater(updater);
                }
                textAreaText ="Filling ParsingStack !";
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                Platform.runLater(updater);
                buttonOn=true;
                textAreaText ="Remove Child !";
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                Platform.runLater(updater);
                addInput=false;
                labelVBoxInput=inputArray.get(0);
                Platform.runLater(updater);
            }

        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();
    }

}
