import javafx.scene.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;


public class View extends Application{
    @Override
    public void start(Stage mainStage) throws Exception{
        mainStage.setTitle("Wikipedia Revision Viewer");
        mainStage.show();

        //test.timestamp=("10/04/17");
        //test.user=("Jethro");
        TableView tableView = new TableView();
        TableColumn<String, Revision> column1=new TableColumn<>("User");
        column1.setCellValueFactory(new PropertyValueFactory<>("user"));

        TableColumn<String, Revision> column2=new TableColumn<>("Date/Time");
        column2.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);

        //List<Revision> list = usedList.getRevisionList();
        //for (Revision i : list)
          //  tableView.getItems().add(new Revision(i.user, i.timestamp));

        VBox vbox = new VBox(tableView);
        Scene scene = new Scene(vbox);
        mainStage.setScene(scene);
        mainStage.show();





    }
    public static void generateWindow(String[]args){
        Application.launch(args);
    }

}
