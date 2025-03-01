package com.ced.costefficientdeplyment.dto;

import java.util.List;

public class Pipeline {

    private long id;
    private List<Node> nodes;
    private int length;
    private boolean isUnderConstruction;

    public Pipeline(List<Node> nodes, int length, boolean isUnderConstruction) {
        this.nodes = nodes;
        this.length = length;
        this.isUnderConstruction = isUnderConstruction;
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

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Pipeline{" +
                "id=" + id +
                ", nodes=" + nodes +
                ", isUnderConstruction=" + isUnderConstruction +
                ", length=" + length +
                '}';
    }

}
