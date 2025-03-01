package com.ced.costefficientdeplyment.dto;

import lombok.*;

import java.util.List;

public class Pipeline {

    private long id;
    private List<Node> nodes;
    private boolean isUnderConstruction;


    public Pipeline() {

    }

    public Pipeline(List<Node> nodes, boolean isUnderConstruction) {
        this.nodes = nodes;
        this.isUnderConstruction = isUnderConstruction;
    }

    @Override
    public String toString() {
        return "Pipeline{" +
                "id=" + id +
                ", nodes=" + nodes +
                ", isUnderConstruction=" + isUnderConstruction +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public boolean isUnderConstruction() {
        return isUnderConstruction;
    }

    public void setUnderConstruction(boolean underConstruction) {
        isUnderConstruction = underConstruction;
    }
}
