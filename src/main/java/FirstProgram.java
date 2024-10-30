import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Vertex;
import com.brunomnsilva.smartgraph.graph.Edge;
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

        // Adicionando os vértices
        Vertex<Character> vertexA = g.insertVertex('a');
        Vertex<Character> vertexB = g.insertVertex('b');
        Vertex<Character> vertexC = g.insertVertex('c');
        Vertex<Character> vertexD = g.insertVertex('d');
        Vertex<Character> vertexE = g.insertVertex('e');
        Vertex<Character> vertexF = g.insertVertex('f');
        Vertex<Character> vertexG = g.insertVertex('g');

        // Adicionando as arestas
        g.insertEdge(vertexA, vertexB, 6);
        g.insertEdge(vertexB, vertexC, 2);
        g.insertEdge(vertexC, vertexD, 30);
        g.insertEdge(vertexD, vertexE, 10);
        g.insertEdge(vertexD, vertexF, 22);
        g.insertEdge(vertexF, vertexE, 15);
        g.insertEdge(vertexF, vertexG, 8);
        g.insertEdge(vertexE, vertexG, 50);
        g.insertEdge(vertexG, vertexA, 11);

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
