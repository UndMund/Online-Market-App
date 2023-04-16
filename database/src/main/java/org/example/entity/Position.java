package org.example.entity;

import java.util.Objects;

public class Position {
    private Integer id;

    private String positionName;

    public Position() {
    }

    public Position(Integer id, String positionName) {
        this.id = id;
        this.positionName = positionName;
    }

    @Override
    public String toString() {
        return "Position{" +
               "id=" + id +
               ", positionName='" + positionName + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(id, position.id) && Objects.equals(positionName, position.positionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, positionName);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
