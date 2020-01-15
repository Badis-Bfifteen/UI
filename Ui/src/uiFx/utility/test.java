package uiFx.utility;

import Q1_Lexer.Lexer;
import Q1_Lexer.Token;
import Q1_Lexer.TokenType;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uiFx.MainUi;

import java.util.Arrays;

public class test extends Application {
    public TableColumn<Token, Integer> tableColumnIndex;
    public TableColumn<Token, TokenType> tableColumnType;
    public TableColumn<Token,String> tableColumnValue;
    public TableView<Token> tableViewTokens;
    //optional - method runs before start method is called
    @Override
    public void init() throws Exception {
        System.out.println("Before!!!!");
    }

    //the start method is abstract and MUST be overided when making a new javafx app
    @Override
    public void start(Stage stage) {
        stage.setTitle("Table height example");

        TableView<String> table = new TableView<>(FXCollections.observableArrayList(
                "Stacey", "Kristy", "Mary Anne", "Claudia"
        ));

        TableColumn<String, Integer> indexColumn = new TableColumn<>();
        indexColumn.setCellFactory(col -> {
            TableCell<String, Integer> indexCell = new TableCell<>();
            ReadOnlyObjectProperty<TableRow<String>> rowProperty = indexCell.tableRowProperty();
            ObjectBinding<String> rowBinding = Bindings.createObjectBinding(() -> {
                TableRow<String> row = rowProperty.get();
                if (row != null) {
                    int rowIndex = row.getIndex();
                    if (rowIndex < row.getTableView().getItems().size()) {
                        return Integer.toString(rowIndex);
                    }
                }
                return null;
            }, rowProperty);
            indexCell.textProperty().bind(rowBinding);
            return indexCell;
        });

        TableColumn<String, String> nameColumn = new TableColumn<>("name");
        nameColumn.setCellValueFactory(f -> new ReadOnlyObjectWrapper<>(f.getValue()));

        ObservableList<TableColumn<String, ?>> columns = table.getColumns();
        columns.add(indexColumn);
        columns.add(nameColumn);

        Group root = new Group();
        root.getChildren().add(table);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        stage.sizeToScene();
    }


    //optional - method runs when application stops
    @Override
    public void stop() throws Exception {
        System.out.println("After!!!");
    }




}
