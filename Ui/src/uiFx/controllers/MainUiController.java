package uiFx.controllers;

import Q1_Lexer.*;
import Q2_Parser.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import uiFx.utility.Console;
import uiFx.utility.PopUpWindow;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;

public class MainUiController {
    public TextField textFieldExpression;
    public TextArea textAreaConsole;
    public CheckBox checkboxParser;
    public Label labelLL1State;
    public Label labelLexerState;
    public CheckBox checkboxLexer;
    public Button buttonParsing;
    public Button buttonTokens;
    public Label labelParserState;
    public MenuItem menuItemQuit;
    public Button buttonUseFile;
    public Button buttonFileRefresh;
    public String tempSaveTextFile;
    public String exp;
    public Label labelExpression;
    public ImageView imageViewTop;
    public Grammar grammar = new Grammar(Grammar.grammarPathName);
    public Token[] tokens;
    public Button buttonGrammar;
    private long lastPressProcessed = 0;
    private String tempTextTF="";

    public MainUiController() throws IOException {
    }

    public void clearConsole(ActionEvent actionEvent) {
        textAreaConsole.setText("");
    }

    @FXML
    public void Exp(ActionEvent keyEvent) {
                buttonParsing.setDisable(false);
                buttonTokens.setDisable(false);
                if (labelLL1State.isDisabled())
                    textFieldExpression.setStyle("-fx-faint-focus-color: transparent;-fx-focus-color:#00dbff;");
                if (checkboxLexer.isSelected()) {
                    textAreaConsole.setText("");
                    this.exp = textFieldExpression.getText();
                    tempTextTF = textFieldExpression.getText();
                    String str = textFieldExpression.getText();
                    Grammar grammar = this.grammar;
                    System.out.print(grammar.nbrRules + " Rules :\n");
                    Rule[] rules = Arrays.copyOf(grammar.rules, grammar.rules.length);
                    Collections.reverse(Arrays.asList(rules));
                    for (Rule i : rules) {
                        Rule.print(i);
                    }
                    System.out.print("Tokens:\n");
                    Token[] tokens = Lexer.getTokens(str);
                    this.tokens = tokens;
                    int tokenLength = tokens.length;
                    for (Token item : tokens) {
                        Token.printToken(item);
                    }
                    System.out.print("The Number of tokens generated = " + tokenLength + "\n");
                    if (!Lexer.errorFound(tokens)) {
                        labelLexerState.setText("Lexical Analysis Successful");
                        labelLexerState.setTextFill(Paint.valueOf("#419a3d"));
                    } else {
                        labelLexerState.setText("Lexical Analysis Failed");
                        labelLexerState.setTextFill(Paint.valueOf("#f40c0c"));
                        labelParserState.setText("Parsing Impossible");
                        buttonParsing.setDisable(true);
                        labelParserState.setTextFill(Paint.valueOf("#000"));
                    }
                    if (!Lexer.errorFound(tokens) && checkboxParser.isSelected()) {
                        System.out.print("Parsing : \n");
                        ParsingTable parsingTable = new ParsingTable(grammar);
                        Parser parser = new Parser(tokens, parsingTable);
                        if (parser.parsable) {
                            System.out.print("input was Parsed Successfully !\n");
                            labelParserState.setText("Parsing Successful");
                            labelParserState.setTextFill(Paint.valueOf("#419a3d"));
                            labelLL1State.setText("Correct");
                            labelLL1State.setTextFill(Paint.valueOf("#419a3d"));
                            textFieldExpression.setStyle("-fx-faint-focus-color: transparent;-fx-focus-color:#41993d;");
                        } else {
                            System.out.print("Parser was unable to parse the input !\n");
                            labelParserState.setText("Parsing Failed");
                            labelLL1State.setText("False");
                            labelParserState.setTextFill(Paint.valueOf("#f40c0c"));
                            labelLL1State.setTextFill(Paint.valueOf("#f40c0c"));
                            textFieldExpression.setStyle("-fx-faint-focus-color: transparent;-fx-focus-color:#ef0b0b;");
                        }
                    } else if (Lexer.errorFound(tokens)) {
                        System.out.print("Parsing impossible some tokens are invalid !\n");
                        labelLL1State.setText("False");
                        labelLL1State.setTextFill(Paint.valueOf("#f40c0c"));
                        textFieldExpression.setStyle("-fx-faint-focus-color: transparent;-fx-focus-color:#ef0b0b;");
                    }
                }
                uiFx.utility.Console console = new uiFx.utility.Console(textAreaConsole);
                PrintStream ps = new PrintStream(console, true);
                System.setOut(ps);
                System.setErr(ps);
                textAreaConsole.setText(textAreaConsole.getText().replaceAll("\\[31m-", "Error: "));
                textAreaConsole.setText(textAreaConsole.getText().replaceAll("\\[0m", ""));

    }

    public void lexerIfUnchecked(ActionEvent actionEvent) {
        if (!checkboxLexer.isSelected()) {
            checkboxParser.setDisable(true);
            labelLexerState.setDisable(true);
            labelLL1State.setDisable(true);
            labelParserState.setDisable(true);
            buttonParsing.setDisable(true);
            buttonTokens.setDisable(true);
        } else {
            checkboxParser.setDisable(false);
            labelLexerState.setDisable(false);
            labelLL1State.setDisable(false);
            labelParserState.setDisable(false);
            buttonParsing.setDisable(false);
            buttonTokens.setDisable(false);
        }
    }

    public void parserIfUnchecked(ActionEvent actionEvent) {
        if (!checkboxParser.isSelected()) {
            labelParserState.setDisable(true);
            buttonParsing.setDisable(true);
            labelLL1State.setDisable(true);
        } else {
            labelParserState.setDisable(false);
            buttonParsing.setDisable(false);
            labelLL1State.setDisable(false);
        }
    }

    public void menuClearConsole(ActionEvent actionEvent) {
        textAreaConsole.setText("");
    }

    public void quit(ActionEvent actionEvent) {
        Stage stage = (Stage) buttonTokens.getScene().getWindow();
        stage.close();
    }

    public void fileChooser(ActionEvent actionEvent) throws IOException {
        buttonParsing.setDisable(false);
        buttonTokens.setDisable(false);
        if (buttonUseFile.getText().equals("_Use File")) {
            FileChooser fileChooser = new FileChooser();
            Stage stage = (Stage) buttonUseFile.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            String lines = "";
            String line;
            FileReader fileReader = new FileReader(selectedFile);
            BufferedReader bufferReader = new BufferedReader(fileReader);
            while ((line = bufferReader.readLine()) != null) {
                lines += line.replaceAll("\n", "");
            }
            tempSaveTextFile = selectedFile.getAbsolutePath();
            textFieldExpression.setDisable(true);
            buttonFileRefresh.setVisible(true);
            labelExpression.setDisable(true);
            buttonUseFile.setText("Remove");
            if (checkboxLexer.isSelected()) {
                textAreaConsole.setText("");
                Grammar grammar = this.grammar;
                System.out.print(grammar.nbrRules + " Rules :\n");
                Rule[] rules = Arrays.copyOf(grammar.rules, grammar.rules.length);
                Collections.reverse(Arrays.asList(rules));
                for (Rule i : rules) {
                    Rule.print(i);
                }
                System.out.print("Tokens:\n");
                Token[] tokens = Lexer.getTokens(lines);
                this.exp = lines;
                this.tokens = tokens;
                int tokenLength = tokens.length;
                for (Token item : tokens) {
                    Token.printToken(item);
                }
                System.out.print("The Number of tokens generated = " + tokenLength + "\n");
                if (!Lexer.errorFound(tokens)) {
                    labelLexerState.setText("Lexical Analysis Successful");
                    labelLexerState.setTextFill(Paint.valueOf("#419a3d"));
                } else {
                    labelLexerState.setText("Lexical Analysis Failed");
                    labelLexerState.setTextFill(Paint.valueOf("#f40c0c"));
                    labelParserState.setText("Parsing Impossible");
                    labelParserState.setTextFill(Paint.valueOf("#000"));
                }
                if (!Lexer.errorFound(tokens) && checkboxParser.isSelected()) {
                    System.out.print("Parsing : \n");
                    ParsingTable parsingTable = new ParsingTable(grammar);
                    Parser parser = new Parser(tokens, parsingTable);
                    if (parser.parsable) {
                        System.out.print("input was Parsed Successfully !\n");
                        labelParserState.setText("Parsing Successful");
                        labelParserState.setTextFill(Paint.valueOf("#419a3d"));
                        labelLL1State.setText("Correct");
                        labelLL1State.setTextFill(Paint.valueOf("#419a3d"));
                    } else {
                        System.out.print("Parser was unable to parse the input !\n");
                        labelParserState.setText("Parsing Failed");
                        labelLL1State.setText("False");
                        labelParserState.setTextFill(Paint.valueOf("#f40c0c"));
                        labelLL1State.setTextFill(Paint.valueOf("#f40c0c"));
                    }
                } else if (Lexer.errorFound(tokens)) {
                    System.out.print("Parsing impossible some tokens are invalid !\n");
                    labelLL1State.setText("False");
                    labelLL1State.setTextFill(Paint.valueOf("#f40c0c"));
                }
            }
            uiFx.utility.Console console = new uiFx.utility.Console(textAreaConsole);
            PrintStream ps = new PrintStream(console, true);
            System.setOut(ps);
            System.setErr(ps);
            textAreaConsole.setText(textAreaConsole.getText().replaceAll("\\[31m-", "Error: "));
            textAreaConsole.setText(textAreaConsole.getText().replaceAll("\\[0m", ""));
        } else {
            buttonFileRefresh.setVisible(false);
            textFieldExpression.setDisable(false);
            buttonUseFile.setText("_Use File");
            labelExpression.setDisable(false);
        }


    }

    public void fileRefresh(ActionEvent actionEvent) throws IOException {
        buttonParsing.setDisable(false);
        buttonTokens.setDisable(false);
        String lines = "";
        String line = "";
        File selectedFile = new File(tempSaveTextFile.replaceAll("/", "//"));
        FileReader fileReader = new FileReader(selectedFile);
        BufferedReader bufferReader = new BufferedReader(fileReader);
        while ((line = bufferReader.readLine()) != null) {
            lines += line.replaceAll("\n", "");
        }
        if (checkboxLexer.isSelected()) {
            textAreaConsole.setText("");
            Grammar grammar = this.grammar;
            System.out.print(grammar.nbrRules + " Rules :\n");
            Rule[] rules = Arrays.copyOf(grammar.rules, grammar.rules.length);
            Collections.reverse(Arrays.asList(rules));
            for (Rule i : rules) {
                Rule.print(i);
            }
            System.out.print("Tokens:\n");
            Token[] tokens = Lexer.getTokens(lines);
            this.exp = lines;
            this.tokens = tokens;
            int tokenLength = tokens.length;
            for (Token item : tokens) {
                Token.printToken(item);
            }
            System.out.print("The Number of tokens generated = " + tokenLength + "\n");
            if (!Lexer.errorFound(tokens)) {
                labelLexerState.setText("Lexical Analysis Successful");
                labelLexerState.setTextFill(Paint.valueOf("#419a3d"));
            } else {
                labelLexerState.setText("Lexical Analysis Failed");
                labelLexerState.setTextFill(Paint.valueOf("#f40c0c"));
                labelParserState.setText("Parsing Impossible");
                labelParserState.setTextFill(Paint.valueOf("#000"));
            }
            if (!Lexer.errorFound(tokens) && checkboxParser.isSelected()) {
                System.out.print("Parsing : \n");
                ParsingTable parsingTable = new ParsingTable(grammar);
                Parser parser = new Parser(tokens, parsingTable);
                if (parser.parsable) {
                    System.out.print("input was Parsed Successfully !\n");
                    labelParserState.setText("Parsing Successful");
                    labelParserState.setTextFill(Paint.valueOf("#419a3d"));
                    labelLL1State.setText("Correct");
                    labelLL1State.setTextFill(Paint.valueOf("#419a3d"));
                } else {
                    System.out.print("Parser was unable to parse the input !\n");
                    labelParserState.setText("Parsing Failed");
                    labelLL1State.setText("False");
                    labelParserState.setTextFill(Paint.valueOf("#f40c0c"));
                    labelLL1State.setTextFill(Paint.valueOf("#f40c0c"));
                }
            } else if (Lexer.errorFound(tokens)) {
                System.out.print("Parsing impossible some tokens are invalid !\n");
                labelLL1State.setText("False");
                labelLL1State.setTextFill(Paint.valueOf("#f40c0c"));
            }
        }
        uiFx.utility.Console console = new Console(textAreaConsole);
        PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);
        System.setErr(ps);
        textAreaConsole.setText(textAreaConsole.getText().replaceAll("\\[31m-", "Error: "));
        textAreaConsole.setText(textAreaConsole.getText().replaceAll("\\[0m", ""));
    }

    public void OnActionGrammar(ActionEvent actionEvent) throws IOException {
        PopUpWindow popUpWindow = new PopUpWindow();
        textAreaConsole.setVisible(false);
        this.grammar = popUpWindow.displayGrammar("Grammar", "../res/GrammarFx.fxml", this.grammar);
        textAreaConsole.setVisible(true);
        textAreaConsole.setText("Grammar Rules Loaded Successfully !");
    }

    public void onActionAccessTokens(ActionEvent actionEvent) throws IOException {
        if (this.exp == null) {
        } else {
            PopUpWindow popUpWindow = new PopUpWindow();
            textAreaConsole.setVisible(false);
            popUpWindow.displayTokens("Tokens", this.exp);
            textAreaConsole.setVisible(true);
            textAreaConsole.setText("You saw the tokens !");
        }
    }

    public void onActionParsingProcess(ActionEvent actionEvent) throws IOException {
        if (textFieldExpression.getText().equals("") && tempSaveTextFile.equals(""))
            buttonParsing.setDisable(true);
        else {
            PopUpWindow popUpWindow = new PopUpWindow();
            textAreaConsole.setVisible(false);
            popUpWindow.displayParsingProcess("Parsing Process", "../res/ParsingProcessFx.fxml", this.grammar, this.tokens);
            textAreaConsole.setVisible(true);
            textAreaConsole.setText("You saw the Parsing Process !");
        }
    }

    public void onActionParsingTable(ActionEvent actionEvent) throws IOException {
        PopUpWindow popUpWindow = new PopUpWindow();
        buttonGrammar.setDisable(true);
        popUpWindow.displayParsingTable("Parsing Table", this.grammar, false);
        buttonGrammar.setDisable(false);

    }
}
