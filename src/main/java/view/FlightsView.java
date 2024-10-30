package view;

import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graph.*;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.Airport;
import model.Flight;

import java.util.Collection;

public class FlightsView extends BorderPane {

    /** The graph model */
    public final Graph<Airport, Flight> graph;

    /** Graph visualization panel */
    private final SmartGraphPanel<Airport, Flight> graphView;

    /** displays total number of airports */
    private Label labelNumberAirports;

    /** displays total number of flights */
    private Label labelNumberFlights;

    /** displays the airport with the most inbound/outbound flights */
    public Label labelBusiestAirport;

    /** displays the number of inbound/outbound flights of the busiest airport */
    public Label labelBusiestAirportNumberFlights;

    private ObservableList<Vertex<Airport>> listAirportsToRemove;
    private ObservableList<Vertex<Airport>> listAirportsFrom;
    private ObservableList<Vertex<Airport>> listAirportsTo;
    private ObservableList<Edge<Flight, Airport>> listFlights;

    public FlightsView() {
        this.graph = new GraphEdgeList<>();
        createInitialModel();

        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        this.graphView = new SmartGraphPanel<>(graph, strategy);

        doLayout();
    }

    private void createInitialModel() {
        // Create vertices (airports)
        Vertex<Airport> hnl = graph.insertVertex(new Airport("HNL")); // Honolulu
        Vertex<Airport> lax = graph.insertVertex(new Airport("LAX")); // Los Angeles
        Vertex<Airport> sfo = graph.insertVertex(new Airport("SFO")); // San Francisco
        Vertex<Airport> ord = graph.insertVertex(new Airport("ORD")); // Chicago O'Hare
        Vertex<Airport> dfw = graph.insertVertex(new Airport("DFW")); // Dallas/Fort Worth
        Vertex<Airport> pvd = graph.insertVertex(new Airport("PVD")); // Providence
        Vertex<Airport> lga = graph.insertVertex(new Airport("LGA")); // LaGuardia
        Vertex<Airport> mia = graph.insertVertex(new Airport("MIA")); // Miami

        // Create edges (flights)
        graph.insertEdge(hnl, lax, new Flight("F1 UN3563", 2555)); // HNL - LAX: F1
        graph.insertEdge(lax, hnl, new Flight("F2 DT1597", 2555)); // LAX - HNL: F2

        graph.insertEdge(lax, sfo, new Flight("F3 UN9375", 337));  // LAX - SFO: F3
        graph.insertEdge(sfo, lax, new Flight("F4 AM4526", 337));  // SFO - LAX: F4

        graph.insertEdge(lax, ord, new Flight("F5 UN4836", 1743)); // LAX - ORD: F5
        graph.insertEdge(ord, lax, new Flight("F6 VA2001", 1743)); // ORD - LAX: F6

        graph.insertEdge(lax, dfw, new Flight("F10 AM4582", 1233)); // LAX - DFW: F10
        graph.insertEdge(dfw, lax, new Flight("F9 SP1020", 1233)); // DFW - LAX: F9

        graph.insertEdge(sfo, ord, new Flight("F7 UN1475", 1843)); // SFO - ORD: F7
        graph.insertEdge(ord, sfo, new Flight("F8 AL7854", 1843)); // ORD - SFO: F8

        graph.insertEdge(ord, dfw, new Flight("F11 UN4568", 802));   // ORD - DFW: F11

        graph.insertEdge(ord, pvd, new Flight("F13 AM4520", 849));   // ORD - PVD: F13
        graph.insertEdge(pvd, ord, new Flight("F14 UN7812", 849));    // PVD - ORD: F14

        graph.insertEdge(dfw, lga, new Flight("F12 SP4512", 1387));   // DFW - LGA: F12
        graph.insertEdge(lga, mia, new Flight("F17 AM1026", 1099));   // DFW - MIA: F17
        graph.insertEdge(mia, lga, new Flight("F16 FT4021", 1099));   // MIA - DFW: F16

        // Add the missing flights between LGA and MIA
        graph.insertEdge(pvd, mia, new Flight("F15 FT1000", 1099));   // LGA - MIA: F17
        graph.insertEdge(dfw, mia, new Flight("F18 AM5267", 1099));   // MIA - LGA: F16
    }


    public void addAirport(String airportCode) {
        // Check if the airport code is valid (not empty and unique)
        if (airportCode == null || airportCode.trim().isEmpty()) {
            showError("Airport code cannot be null or empty.");
            return;
        }

        Airport newAirport = new Airport(airportCode);
        Vertex<Airport> vertex = graph.insertVertex(newAirport);

        // Update the combo boxes with the new airport
        updateControls();
    }

    public void addFlight(Vertex<Airport> vertexFrom, Vertex<Airport> vertexTo, String code, String distance) {
        // Check if vertices and flight code are valid
        if (vertexFrom == null || vertexTo == null) {
            showError("Both airports must be selected to add a flight.");
            return;
        }

        if (vertexFrom.equals(vertexTo)) {
            showError("Cannot add a flight with the same airport as inbound/outbound.");
            return;
        }

        if (code == null || code.trim().isEmpty()) {
            showError("Flight code cannot be null or empty.");
            return;
        }

        double flightDistance;
        try {
            flightDistance = Double.parseDouble(distance);
        } catch (NumberFormatException e) {
            showError("Distance must be a valid number.");
            return;
        }

        Flight newFlight = new Flight(code, flightDistance);
        graph.insertEdge(vertexFrom, vertexTo, newFlight);

        // Update the combo boxes with the new flight
        updateControls();
    }

    public void removeFlight(Edge<Flight, Airport> edge) {
        if (edge == null) {
            showError("No flight selected to remove.");
            return;
        }

        graph.removeEdge(edge);

        // Update the combo boxes after removing the flight
        updateControls();
    }

    public void removeAirport(Vertex<Airport> vertex) {
        if (vertex == null) {
            showError("No airport selected to remove.");
            return;
        }

        // Check if the airport has any flights before removing
        if (!graph.incidentEdges(vertex).isEmpty()) {
            showError("Cannot remove an airport that has flights associated with it.");
            return;
        }

        graph.removeVertex(vertex);

        // Update the combo boxes after removing the airport
        updateControls();
    }


    public void updateStatistics() {
        // 1. Número total de aeroportos
        int numberOfAirports = graph.vertices().size();
        labelNumberAirports.setText(String.valueOf(numberOfAirports));

        // 2. Número total de voos
        int numberOfFlights = graph.edges().size();
        labelNumberFlights.setText(String.valueOf(numberOfFlights));

        // 3. Aeroporto com mais tráfego (vértice com maior grau)
        Vertex<Airport> busiestAirport = null;
        int maxDegree = 0;

        for (Vertex<Airport> vertex : graph.vertices()) {
            int degree = graph.incidentEdges(vertex).size();
            if (degree > maxDegree) {
                maxDegree = degree;
                busiestAirport = vertex;
            }
        }

        // 4. Atualizar informações sobre o aeroporto mais movimentado
        if (busiestAirport != null) {
            labelBusiestAirport.setText(busiestAirport.element().toString());
            labelBusiestAirportNumberFlights.setText(String.valueOf(maxDegree));
        } else {
            labelBusiestAirport.setText("N/A");
            labelBusiestAirportNumberFlights.setText("0");
        }
    }


    private void doLayout() {
        setStyle("-fx-background-color: #FFF;");

        // Top area - user interaction
        VBox top = new VBox(30);
        top.setPadding(new Insets(30));
        HBox firstRow = new HBox(10);
        HBox secondRow = new HBox(10);
        HBox thirdRow = new HBox(10);

        Button buttonAddAirport = new Button("Add Airport");
        Button buttonRemoveAirport = new Button("Remove Airport");
        Button buttonAddFlight = new Button("Add Flight");
        Button buttonRemoveFlight = new Button("Remove Flight");

        this.listAirportsToRemove = FXCollections.observableArrayList();
        this.listAirportsFrom = FXCollections.observableArrayList();
        this.listAirportsTo = FXCollections.observableArrayList();
        this.listFlights = FXCollections.observableArrayList();

        TextField textAirportCode = new TextField();
        textAirportCode.setPromptText("Airport code");
        TextField textFlightCode = new TextField();
        textFlightCode.setPromptText("Flight code");
        TextField textFlightDistance = new TextField();
        textFlightDistance.setPromptText("Flight distance");

        ComboBox<Vertex<Airport>> comboAirportsToRemove = new ComboBox<>(listAirportsToRemove);
        ComboBox<Vertex<Airport>> comboAirportsFrom = new ComboBox<>(listAirportsFrom);
        ComboBox<Vertex<Airport>> comboAirportsTo = new ComboBox<>(listAirportsTo);
        ComboBox<Edge<Flight, Airport>> comboFlightsToRemove = new ComboBox<>(listFlights);

        comboFlightsToRemove.setConverter(new EgdeFlightConverter());
        comboAirportsToRemove.setConverter(new VertexAirportConverter());
        comboAirportsFrom.setConverter(new VertexAirportConverter());
        comboAirportsTo.setConverter(new VertexAirportConverter());

        firstRow.getChildren().addAll(new Label("Add Airport: "), textAirportCode, buttonAddAirport,
                new Separator(Orientation.VERTICAL),
                new Label("Remove Airport: "), comboAirportsToRemove, buttonRemoveAirport);

        secondRow.getChildren().addAll(new Label("Add Flight From: "),
                comboAirportsFrom, new Label("To:"), comboAirportsTo, textFlightCode, textFlightDistance, buttonAddFlight);

        thirdRow.getChildren().addAll(new Label("Remove Flight: "), comboFlightsToRemove, buttonRemoveFlight);
        top.getChildren().addAll(firstRow, secondRow, thirdRow);

        setTop(top);

        // Center area - the graph visualization (airports and flights)
        setCenter(new SmartGraphDemoContainer(this.graphView));

        // Bottom area - statistics
        HBox bottom = new HBox(10);
        bottom.setPadding(new Insets(30));

        Label airportCount = new Label("Airport count: ");
        Label flightCount = new Label("Flight count: ");
        Label busiestAirport = new Label("Busiest Airport: ");
        Label busiestCount = new Label("Busiest Airport Flight Count: ");
        this.labelNumberAirports = new Label("##");
        this.labelNumberFlights = new Label("##");
        this.labelBusiestAirport = new Label("##");
        this.labelBusiestAirportNumberFlights = new Label("##");

        bottom.getChildren().addAll(airportCount, labelNumberAirports,
                flightCount, labelNumberFlights,
                busiestAirport, labelBusiestAirport,
                busiestCount, labelBusiestAirportNumberFlights);

        setBottom(bottom);

        // Bind events
        buttonAddAirport.setOnAction(event -> {
            String airportCode = textAirportCode.getText().trim();
            addAirport(airportCode);
            textAirportCode.clear();
            graphView.update();
            updateControls();
            updateStatistics();
        });

        buttonRemoveAirport.setOnAction(event -> {
            Vertex<Airport> vertex = comboAirportsToRemove.getSelectionModel().getSelectedItem();
            removeAirport(vertex);
            graphView.update();
            updateControls();
            updateStatistics();
        });

        buttonAddFlight.setOnAction(event -> {
            Vertex<Airport> vertexFrom = comboAirportsFrom.getSelectionModel().getSelectedItem();
            Vertex<Airport> vertexTo = comboAirportsTo.getSelectionModel().getSelectedItem();
            String flightCode = textFlightCode.getText().trim();
            String flightDistance = textFlightDistance.getText().trim();
            addFlight(vertexFrom, vertexTo, flightCode, flightDistance);
            textFlightCode.clear();
            textFlightDistance.clear();
            graphView.update();
            updateControls();
            updateStatistics();
        });

        buttonRemoveFlight.setOnAction(event -> {
            Edge<Flight, Airport> edge = comboFlightsToRemove.getSelectionModel().getSelectedItem();
            removeFlight(edge);
            graphView.update();
            updateControls();
            updateStatistics();
        });
    }

    private void updateControls() {
        // Logic to update control states or lists if needed
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void takeOff() {
        this.graphView.init();
        updateStatistics();
        updateControls();
    }

    private class VertexAirportConverter extends StringConverter<Vertex<Airport>> {
        @Override
        public String toString(Vertex<Airport> airportVertex) {
            return airportVertex != null ? airportVertex.element().toString() : "";
        }

        @Override
        public Vertex<Airport> fromString(String s) {
            return null; // No reverse conversion needed.
        }
    }

    private class EgdeFlightConverter extends StringConverter<Edge<Flight, Airport>> {
        @Override
        public String toString(Edge<Flight, Airport> flightEdge) {
            return flightEdge != null ? flightEdge.element().toString() : "";
        }

        @Override
        public Edge<Flight, Airport> fromString(String s) {
            return null; // No reverse conversion needed.
        }
    }
}
