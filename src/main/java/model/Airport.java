package model;

import java.util.Objects;

public class Airport {
    private String code;

    public Airport(String code) {
        if(code == null) throw new IllegalArgumentException("Code cannot be null.");

        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return code.equals(airport.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
