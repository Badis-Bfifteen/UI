package uiFx.controllers;

import Q2_Parser.FirstFollow;
import Q2_Parser.Grammar;
import Q2_Parser.Rule;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import uiFx.utility.PopUpWindow;

import java.io.IOException;
import java.util.Arrays;

public class GrammarFx {
    public Grammar grammar = new Grammar(Grammar.grammarPathName);
    public CheckBox checkBoxEdit;
    public TextArea textAreaRules;
    public Button buttonModify;
    public Button buttonDefault;
    public Button buttonShowMatrix;
    public Pane paneFirstFollow;
    public Button buttonFirstFollow;
    public VBox vBoxGrammarRules;
    public VBox vBoxFirstFollow;
    public Button buttonSave;

    public GrammarFx() throws IOException {
    }


    public void editableRules(ActionEvent actionEvent) throws IOException {
        if (checkBoxEdit.isSelected())
            textAreaRules.setEditable(true);
        else {
            textAreaRules.setEditable(false);
        }

        if (!checkBoxEdit.isSelected() && Arrays.toString(textAreaRules.getText().split("\n")).equals(Arrays.toString((new Grammar(Grammar.grammarPathName)).grammarLines))) {
            buttonDefault.setDisable(true);
            buttonModify.setDisable(true);
        } else {
            buttonDefault.setDisable(false);
            buttonModify.setDisable(false);
        }
    }

    public void onActionParsingTable(ActionEvent actionEvent) throws IOException {
        PopUpWindow popUpWindow = new PopUpWindow();
        popUpWindow.displayParsingTable("Parsing Table",this.grammar);
    }

    public void onActionDefault(ActionEvent actionEvent) throws IOException {
        buttonDefault.setDisable(true);
        buttonModify.setDisable(true);
        textAreaRules.setEditable(false);
        checkBoxEdit.setSelected(false);
        textAreaRules.setText("");
        this.grammar=new Grammar(Grammar.grammarPathName);
        for(String s:this.grammar.grammarLines){
            textAreaRules.appendText(s+"\n");
        }
        buttonSave.setDisable(true);
    }

    public void onActionModify(ActionEvent actionEvent) throws IOException {
        PopUpWindow popUpWindow = new PopUpWindow();
        popUpWindow.grammarModWarning("Warning!","../res/GWarning.fxml");
        buttonDefault.setDisable(true);
        buttonModify.setDisable(true);
        textAreaRules.setEditable(false);
        checkBoxEdit.setSelected(false);
        String[] grammarLines =textAreaRules.getText().split("\n");
        this.grammar=new Grammar(grammarLines);
        if(!Arrays.toString(this.grammar.grammarLines).equals(Arrays.toString(new Grammar(Grammar.grammarPathName).grammarLines))){
            buttonSave.setDisable(false);
        }else buttonSave.setDisable(true);
    }

    public void showFirstFollow(ActionEvent actionEvent) {
        paneFirstFollow.setVisible(true);
        buttonFirstFollow.setVisible(false);
        fillRuleFirstFollow();
    }
    public void fillRuleFirstFollow(){
        vBoxFirstFollow.getChildren().removeAll(vBoxFirstFollow.getChildren());
        vBoxGrammarRules.getChildren().removeAll(vBoxGrammarRules.getChildren());
        Rule[] Rules = grammar.rules;
        for (Rule rule : Rules) {
            Label labelRule=new Label();
            labelRule.setText(Rule.print(rule).replaceAll("#","Ɛ"));
            labelRule.setStyle("-fx-font-size: 18pt;-fx-font-family: \"Segoe UI Light\";");
            labelRule.setWrapText(true);
            vBoxGrammarRules.getChildren().add(labelRule);
        }
        for (String nonTerminal : grammar.nonTerminals) {
            Label labelFirst=new Label();
            Label labelFollow=new Label();
            String s="First( " + nonTerminal + " ) = " + FirstFollow.First(nonTerminal,grammar);
            s=s.replaceAll("\\[","{ ").replaceAll("]"," }");
            labelFirst.setText(s.replaceAll("#","Ɛ"));
            labelFirst.setStyle("-fx-font-size: 18pt;-fx-font-family: \"Segoe UI Light\";");
            labelFirst.setWrapText(true);
            vBoxFirstFollow.getChildren().add(labelFirst);
            s="Follow(" + nonTerminal + ") = " + FirstFollow.Follow(nonTerminal,grammar);
            s=s.replaceAll("\\[","{ ").replaceAll("]"," }");
            labelFollow.setText(s.replaceAll("#","Ɛ"));
            labelFollow.setStyle("-fx-font-size:18pt;-fx-font-family: \"Segoe UI Light\";");
            labelFollow.setWrapText(true);
            vBoxFirstFollow.getChildren().add(labelFollow);
        }
    }

    public void buttonRefresh(ActionEvent actionEvent) {
        fillRuleFirstFollow();
    }

    public void onActionSave(ActionEvent actionEvent) {
    }
}
