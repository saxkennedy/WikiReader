import java.util.*;

import com.google.gson.JsonObject;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;

public class TableViewSample extends Application {
    private TextField searchTerm = new TextField();
    private Label searchLabel = new Label("Please enter your search term.");
    private Button searchByNumEdits = new Button("Search by Edit Frequency");
    private String headerA;
    private String headerB;
    private Button searchByRecency = new Button("Search by Edit Recency");
    private String type;
    private String term;


    @Override
    public void start(Stage stage) throws Exception {
        //BUTTON HANDLING GOES HERE I THINK

        Map<String, String> map = revisionMapGenerator("trump", "frequency");//THESE NEED TO TAKE BUTTON STUFF
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


        ObservableList<Map.Entry<String, String>> revisedMapItems = FXCollections.observableArrayList(map.entrySet());
        TableView<Map.Entry<String, String>> table = new TableView<>(revisedMapItems);
        table.getColumns().setAll(column1, column2);
        table.setMinSize(400, 800);


        VBox vBox = new VBox();
        vBox.getChildren().addAll(searchTerm, searchLabel, searchByNumEdits, searchByRecency, table);
        stage.setTitle("Wikipedia Editor Information");
        Scene scene = new Scene(vBox, 400, 900);
        stage.setScene(scene);
        stage.show();

    }
    public LinkedHashMap<String, String> revisionMapGenerator(String term,String type) throws Exception {
        WikiMediaReader reader = new WikiMediaReader();
        JsonObject wikiPage = reader.getJSONfromURL(term);
        RevisionListManager parser = new RevisionListManager(wikiPage);
        LinkedHashMap<String, String> revisionMap;
        if (type=="frequency")
            revisionMap=parser.getFrequencySortedRevisionMap();

        else
            revisionMap=parser.getTimeSortedRevisionMap();
            return revisionMap;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
