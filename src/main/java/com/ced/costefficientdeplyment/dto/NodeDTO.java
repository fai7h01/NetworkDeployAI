package com.ced.costefficientdeplyment.dto;

import java.util.Objects;

public class NodeDTO {

    private Long id;
    private double latitude;
    private double longitude;

    public NodeDTO(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public NodeDTO(long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        NodeDTO nodeDTO = (NodeDTO) object;
        return id == nodeDTO.id && Double.compare(latitude, nodeDTO.latitude) == 0 && Double.compare(longitude, nodeDTO.longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude);
    }

}
