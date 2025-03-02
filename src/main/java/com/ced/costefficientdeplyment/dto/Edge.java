package com.ced.costefficientdeplyment.dto;

public class Edge {

    private NodeDTO from;
    private NodeDTO to;
    private double weight;

    public Edge(NodeDTO from, NodeDTO to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public NodeDTO getFrom() {
        return from;
    }

    public void setFrom(NodeDTO from) {
        this.from = from;
    }

    public NodeDTO getTo() {
        return to;
    }

    public void setTo(NodeDTO to) {
        this.to = to;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
