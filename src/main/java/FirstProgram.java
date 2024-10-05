import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Vertex;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@SuppressWarnings("removal")
public class FirstProgram extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        Graph<Character, Integer> g = new GraphEdgeList<>();

        // TODO: create graph structure

        ///////////////////////////////////////////////////////////////////////////////////////
        // STUDENTS -> NOTHING TO DO BELOW THIS LINE

        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        SmartGraphPanel<Character, Integer> graphView = new SmartGraphPanel<>(g, strategy);

        Scene scene = new Scene(new SmartGraphDemoContainer(graphView), 800, 800);

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("JavaFX SmartGraph Visualization");
        stage.setScene(scene);
        stage.show();

        graphView.init();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
