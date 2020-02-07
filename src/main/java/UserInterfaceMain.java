import java.util.*;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserInterfaceMain extends Application {
    private String type;
    private String headerA;
    private String headerB;
    private Label notFound = new Label();

    public static void main(String[] args) {launch(args);}
    @Override
    public void start(Stage stage) {
        TextField searchTerm = new TextField();
        Label searchLabel = new Label("Please enter your search term.");
        RadioButton timeSort = new RadioButton();
        RadioButton frequencySort = new RadioButton();
        Button search = new Button("Search!");

        final ToggleGroup sortGroup = new ToggleGroup();
        timeSort.setToggleGroup(sortGroup);
        timeSort.setText("Sort by date.");
        frequencySort.setToggleGroup(sortGroup);
        frequencySort.setText("Sort by Frequency.");

        search.setOnAction(actionEvent -> {
            notFound.setText("");
            if (timeSort.isSelected()) {
                headerA = ("Date/Time");
                headerB = "User";
                type = "time";
            }
            else if (frequencySort.isSelected()) {
                headerA = "User";
                headerB = "Number of Edits";
                type = "frequency";
            }
            RevisionMapGenerator initialTermMap = new RevisionMapGenerator();
            Map<String, String> sortedTermMap = null;
            try {
                sortedTermMap = initialTermMap.revisionMapGenerator(searchTerm.getText(), type);
            } catch (Exception e) {
                notFound.setText("Page Not Found! Try Again.");
            }
            ObservableList<Map.Entry<String, String>> revisedMapItems = FXCollections.observableArrayList(sortedTermMap.entrySet());
            TableColumn<Map.Entry<String, String>, String> column1 = new TableColumn<>(headerA);
            column1.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
            TableColumn<Map.Entry<String, String>, String> column2 = new TableColumn<>(headerB);
            column2.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getValue()));

            String redirect = initialTermMap.getRedirect();
            if (redirect != null) {
                redirect = ("Redirected to page: " + redirect);
            }
            else {
                redirect = "Page Found!";
            }
            Label redirectNotify = new Label(redirect);
            VBox vBox = new VBox();
            TableView<Map.Entry<String, String>> table = new TableView<>(revisedMapItems);
            table.getColumns().setAll(column1, column2);
            table.setMinSize(400, 800);
            vBox.getChildren().addAll(searchTerm, searchLabel, search, timeSort, frequencySort, notFound,redirectNotify, table);
            Scene scene2 = new Scene(vBox, 400, 900);
            stage.setScene(scene2);
        });
        VBox vBox = new VBox();
        vBox.getChildren().addAll(searchTerm, searchLabel, search, timeSort, frequencySort, notFound);
        stage.setTitle("Wikipedia Editor Information");
        Scene scene1 = new Scene(vBox, 400, 900);
        stage.setScene(scene1);
        stage.show();
    }
}