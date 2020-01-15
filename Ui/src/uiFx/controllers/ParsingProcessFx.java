package uiFx.controllers;

import Q1_Lexer.Token;
import Q2_Parser.FirstFollow;
import Q2_Parser.Grammar;
import Q2_Parser.ParsingTable;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import uiFx.utility.TODO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

import static Q2_Parser.Parser.inputOfToken;

public class ParsingProcessFx {
    public Grammar grammar;
    public Token[] tokens;
    public VBox vBoxInputStack;
    public TextArea textArea;
    public Button buttonStart;
    public VBox vBoxParsingStack;

    public void start(ActionEvent actionEvent){
        buttonStart.setText("Restart");
        buttonStart.setDisable(true);
        TODO.toDO(textArea,vBoxParsingStack,vBoxInputStack,buttonStart,tokens,new ParsingTable(grammar));
    }

}
