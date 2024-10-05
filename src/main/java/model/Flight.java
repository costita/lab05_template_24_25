package model;

import java.util.Objects;

public class Flight {
    private String code;

    private double distance;

    public Flight(String code, double distance) {
        if(code == null) throw new IllegalArgumentException("Code cannot be null.");

        this.code = code;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return String.format("%s | %.2f miles", code, distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return code.equals(flight.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
