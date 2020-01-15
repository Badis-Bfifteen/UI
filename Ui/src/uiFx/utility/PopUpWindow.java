package uiFx.utility;

import Q1_Lexer.Lexer;
import Q1_Lexer.Token;
import Q1_Lexer.TokenType;
import Q2_Parser.FirstFollow;
import Q2_Parser.Grammar;
import Q2_Parser.ParsingTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import uiFx.controllers.GrammarFx;
import uiFx.controllers.ParsingProcessFx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import static Q2_Parser.Parser.inputOfToken;

public class PopUpWindow {

    public PopUpWindow() {
    }

    public Grammar displayGrammar(String title, String fxml_path, Grammar grammar) throws IOException {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml_path));
        Parent root = loader.load();
        window.setTitle(title);
        window.setResizable(false);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(root);
        window.setScene(scene);
        GrammarFx controller = loader.getController();
        String lines = "";
        for (String s : grammar.grammarLines) {
            lines += s + "\n";
        }
        controller.textAreaRules.setText(lines);
        window.showAndWait();
        return controller.grammar;
    }

    public void displayTokens(String title, String exp) throws IOException {
        Stage window = new Stage();
        Label labelExpression = new Label();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);

        Parent root = tokenToTableView(labelExpression, exp);
        window.setTitle(title);
        window.setResizable(false);
        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(root);
        window.setScene(scene);

        window.showAndWait();

    }

    public void displayParsingProcess(String title, String fxml_path, Grammar grammar, Token[] tokens) throws IOException {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml_path));
        Parent root = loader.load();
        window.setTitle(title);
        window.setResizable(false);
        ParsingProcessFx controller = loader.getController();
        controller.grammar = grammar;
        controller.tokens = tokens;
        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(root);
        window.setScene(scene);

        controller.grammar = grammar;
        controller.tokens = tokens;

        window.showAndWait();
    }

    public void displayParsingTable(String title, Grammar grammar,boolean appModal) throws IOException {
        Stage window = new Stage();
        Label labelExpression = new Label();

        //Block events to other windows
        if(appModal) window.initModality(Modality.APPLICATION_MODAL);

        HBox root = matrixToTableView(grammar);
        window.setTitle(title);
        window.setResizable(false);
        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(root);
        window.setScene(scene);

        window.showAndWait();
    }
    public void displayParsingTable(String title, Grammar grammar) throws IOException {
        Stage window = new Stage();
        Label labelExpression = new Label();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);

        HBox root = matrixToTableView(grammar);
        window.setTitle(title);
        window.setResizable(false);
        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(root);
        window.setScene(scene);

        window.showAndWait();
    }

    public VBox tokenToTableView(Label exp, String str) {
        TableColumn<Token, Integer> tableColumnIndex;
        TableColumn<Token, TokenType> tableColumnType;
        TableColumn<Token, String> tableColumnValue;
        VBox parent = new VBox();
        TableView<Token> tableViewTokens;


        ObservableList<Token> observableListTokens = FXCollections.observableArrayList();

        observableListTokens.addAll(Arrays.asList(Lexer.getTokens(str)));

        exp.setText("Expression : " + str);
        exp.setStyle("-fx-font-size:18px");

        tableColumnType = new TableColumn<>("Type");
        tableColumnType.setCellValueFactory(new PropertyValueFactory<Token, TokenType>("type"));
        tableColumnType.setEditable(false);

        tableColumnValue = new TableColumn<>("Value");
        tableColumnValue.setCellValueFactory(new PropertyValueFactory<Token, String>("value"));
        tableColumnValue.setEditable(false);

        tableColumnIndex = new TableColumn<>("Index");
        tableColumnIndex.setCellValueFactory(new PropertyValueFactory<Token, Integer>("index"));
        tableColumnIndex.setEditable(false);

        tableViewTokens = new TableView<Token>();
        tableViewTokens.setItems(observableListTokens);
        tableViewTokens.getColumns().add(tableColumnType);
        tableViewTokens.getColumns().add(tableColumnValue);
        tableViewTokens.getColumns().add(tableColumnIndex);
        tableViewTokens.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        parent.getChildren().add(exp);
        parent.getChildren().add(tableViewTokens);
        return parent;
    }

    public HBox matrixToTableView(Grammar grammar) {
        VBox nonTerminals = new VBox();
        String[][][] matrix = ParsingTable.getParsingTable(grammar.nonTerminals, grammar.terminals, grammar);
        Label label1 = new Label();
        label1.setPrefSize(166,48);
        label1.setAlignment(Pos.CENTER);
        label1.setText("\\    Terminals\nNon Terminals");
        label1.setStyle("-fx-border-color: #384145;-fx-text-fill: #384145;-fx-font-size: 11pt;-fx-font-weight: bold;");
        nonTerminals.getChildren().add(label1);
        for (String n : grammar.nonTerminals) {
            Label label = new Label();
            label.setPrefSize(166,48);
            label.setAlignment(Pos.CENTER);
            label.setText(n);
            label.setStyle("-fx-border-color: #384145;-fx-text-fill: #384145;-fx-font-size: 18pt;-fx-font-weight: bold;");
            nonTerminals.getChildren().add(label);
        }
        HBox hBox = new HBox();
        hBox.getChildren().add(nonTerminals);
        for (int column = 0; column < grammar.terminals.length; column++) {
            VBox vBox = new VBox();
            Label label2 = new Label();
            label2.setPrefSize(166,48);
            label2.setAlignment(Pos.CENTER);
            label2.setText(grammar.terminals[column]);
            label2.setStyle("-fx-border-color: #384145;-fx-text-fill: #384145;-fx-font-size: 18pt;-fx-font-weight: bold;");
            vBox.getChildren().add(label2);
            for (int row = 0; row < grammar.nonTerminals.length; row++) {
                Label label = new Label();
                label.setPrefSize(166,48);
                label.setAlignment(Pos.CENTER);
                label.setStyle("-fx-border-color: #F5F5F1;-fx-text-fill: #F5F5F1;-fx-font-size: 18pt;-fx-font-family: \"Segoe UI Light\";-fx-background-color:#384145;");
                label.setText(Arrays.toString(matrix[row][column]).replaceAll("\\["," ").replaceAll("]"," ").replaceAll("#","Ɛ").replaceAll("null","").replaceAll(",",""));
                vBox.getChildren().add(label);
            }
            hBox.getChildren().add(vBox);
        }

        return hBox;
    }
    public void grammarModWarning(String title,String fxml_path) throws IOException {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.UTILITY);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml_path));
        Parent root = loader.load();
        window.setTitle(title);
        window.setResizable(false);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();
    }
    public void seeParsingProcess(Token[] input, ParsingTable parsingTable, TextArea textArea,VBox vBoxInputStack,VBox vBoxParsingStack) {
        textArea.setText("Filling inputStack !");
        Token[]tokens= input.clone();
        ArrayList<Label> inputArray = new ArrayList<>();
        for (Token token: tokens){
            Label labelFor=new Label();
            labelFor.setStyle("-fx-border-insets:1;-fx-border-width:2;-fx-border-radius: 5;-fx-border-style: solid;-fx-font-size: 13px;-fx-font-weight: bold;");
            labelFor.setPrefSize(84,25);
            labelFor.setAlignment(Pos.CENTER);
            labelFor.setText(inputOfToken(token));
            inputArray.add(labelFor);
            vBoxInputStack.getChildren().add(labelFor);
        }
        textArea.setText("Filling ParsingStack !");
        String expected,found = "";

        Stack<String> inputStack = new Stack<>();
        for (int i = input.length - 1; i >= 0; i--) {
            inputStack.add(inputOfToken(input[i]));
        }
        String[] terminals = parsingTable.terminals;
        String[] nonTerminals = parsingTable.nonTerminals;
        Stack<String> parsingStack = new Stack<>();
        parsingStack.push(terminals[terminals.length - 1]);
        if (!inputStack.peek().equals("$"))
            parsingStack.push(nonTerminals[0]);
        String[] parsingMatrixExtract;
        while (!inputStack.empty()) {

            expected=parsingStack.peek();
            found=inputStack.peek();
            System.out.println("Matching \"" +found+ "\" with \"" + expected + "\"");
            if (inputStack.peek().equals(parsingStack.peek())) {
                System.out.println("Matching Successful !");
                System.out.println("Removing terminal match ! ");
                inputStack.pop();
                parsingStack.pop();
            } else if (FirstFollow.isNonTerminal(parsingStack.peek(), parsingTable.grammar)) {
                System.out.println("Matching Failed !");
                parsingMatrixExtract = parsingTable.parsingMatrix[Arrays.asList(nonTerminals).indexOf(parsingStack.peek())][Arrays.asList(terminals).indexOf(inputStack.peek())];
                System.out.println("Replacing \"" + parsingStack.peek() + "\" Respectively with  ");
                if (parsingMatrixExtract[0] != null && !parsingMatrixExtract[0].equals("#")) {
                    parsingStack.pop();
                    for (int i =parsingMatrixExtract.length-1;i>=0;i--) {
                        parsingStack.add(parsingMatrixExtract[i]);
                        System.out.println("-> " + parsingMatrixExtract[i]);
                    }
                } else if (parsingMatrixExtract[0] != null) {
                    System.out.println("-> Epsilon \nResulting only in the Removal of \"" + parsingStack.peek() + "\"");
                    parsingStack.pop();
                } else {
                    System.out.println("-> Null ");
                    System.out.println("Error Parsing Failed ! ");
                    break;
                }
            } else if(parsingStack.peek().equals("$")) {
                System.out.println("Error Parsing Failed ! ");
                System.out.println("Matching terminals Failed !\nClosing parenthesis found but was never opened !");
                break;
            }else if(parsingStack.peek().equals(")")){
                System.out.println("Matching terminals Failed !\nOpening parenthesis found but was never closed !");
                break;
            }else {
                System.out.println("Matching terminals Failed ! (check Grammar)");
                break;
            }
        }
    }

}
