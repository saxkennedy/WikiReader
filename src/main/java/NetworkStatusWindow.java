import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NetworkStatusWindow {
    private String title = "Network Connection";
    private String getNetworkMessage = "Connecting to Wikipedia";
    private String getNetworkFailMessage = "Cannot connect to Wikipedia";
    private Stage window = new Stage();
    private Label label = new Label();

    public void open() {

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(140);

        label.setText(getNetworkMessage);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label);
        layout.setAlignment((Pos.CENTER));

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }

    public void displayConnectionError() {
        label.setText(getNetworkFailMessage);
    }

    public void close() {
        window.close();
    }
}
