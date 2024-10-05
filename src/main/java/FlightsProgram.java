import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.FlightsView;

public class FlightsProgram extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        FlightsView view = new FlightsView();
        Scene scene = new Scene(view, 1024, 1080);

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Flights Visualization");
        stage.setScene(scene);
        stage.show();

        // Only after window is presented (need boundaries to draw the graph)
        view.takeOff();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
