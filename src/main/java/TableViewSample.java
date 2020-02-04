import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TableViewSample extends Application {
    private String type;
    private String headerA;
    private String headerB;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        TextField searchTerm = new TextField();
        Label searchLabel = new Label("Please enter your search term.");

        final ToggleGroup sortGroup = new ToggleGroup();

        RadioButton timeSort = new RadioButton();
        timeSort.setToggleGroup(sortGroup);
        timeSort.setText("Sort by date.");

        RadioButton frequencySort = new RadioButton();
        frequencySort.setToggleGroup(sortGroup);
        frequencySort.setText("Sort by Frequency.");

        Button search = new Button("Search!");
        search.setOnAction(actionEvent -> {
            if (timeSort.isSelected()) {
                headerA = ("Date/Time");
                headerB = "User";
                type = "time";
            } else if (frequencySort.isSelected()) {
                headerA = "User";
                headerB = "Number of Edits";
                type = "frequency";
            }
            RevisionMapGenerator rev = new RevisionMapGenerator();
            Map<String, String> map = null;
            try {
                map = rev.revisionMapGenerator(searchTerm.getText(), type);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ObservableList<Map.Entry<String, String>> revisedMapItems = FXCollections.observableArrayList(map.entrySet());
            TableColumn<Map.Entry<String, String>, String> column1 = new TableColumn<>(headerA);
            column1.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> p) {
                    return new SimpleStringProperty(p.getValue().getKey());
                }
            });

            TableColumn<Map.Entry<String, String>, String> column2 = new TableColumn<>(headerB);
            column2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<String, String>, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<String, String>, String> e) {
                    return new SimpleStringProperty(e.getValue().getValue());
                }

            });
            VBox vBox = new VBox();
            TableView<Map.Entry<String, String>> table = new TableView<>(revisedMapItems);
            table.getColumns().setAll(column1, column2);
            table.setMinSize(400, 800);
            vBox.getChildren().addAll(searchTerm, searchLabel, search, timeSort, frequencySort, table);
            Scene scene2 = new Scene(vBox, 400, 900);
            stage.setScene(scene2);
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(searchTerm, searchLabel, search, timeSort, frequencySort);
        stage.setTitle("Wikipedia Editor Information");
        Scene scene1 = new Scene(vBox, 400, 900);
        stage.setScene(scene1);
        stage.show();
    }


}
