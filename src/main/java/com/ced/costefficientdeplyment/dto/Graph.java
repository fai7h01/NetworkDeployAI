package com.ced.costefficientdeplyment.dto;

import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Graph {

    private List<Pipeline> pipelines;
    private Map<Node, List<Edge>> adjacencyList;

    public Graph(List<Pipeline> pipelines) {
        this.pipelines = pipelines;
        this.adjacencyList = new HashMap<>();

    }

    private void buildAdjacencyList() {
        // Add zero-cost edges for under-construction pipelines
        for (Pipeline pipeline : pipelines) {
            if (pipeline.isUnderConstruction()) {
                List<Node> nodes = pipeline.getNodes();
                for (int i = 0; i < nodes.size() - 1; i++) {
                    Node from = nodes.get(i);
                    Node to = nodes.get(i + 1);
                    addEdge(from, to, 0.0);
                    addEdge(to, from, 0.0); // Undirected
                }
            }
        }

        Set<Node> allNodes = getAllNodes();
        List<Node> nodeList = new ArrayList<>(allNodes);
        for (int i = 0; i < nodeList.size(); i++) {
            Node from = nodeList.get(i);
            for (int j = i + 1; j < nodeList.size(); j++) {
                Node to = nodeList.get(j);
                if (!areConnectedByZeroCostEdge(from, to)) {
                    double distance = calculateDistance(from, to);
                    addEdge(from, to, distance);
                    addEdge(to, from, distance);
                }
            }
        }
    }

    private void addEdge(Node from, Node to, double weight) {
        adjacencyList.computeIfAbsent(from, k -> new ArrayList<>()).add(new Edge(from, to, weight));
    }

    private boolean areConnectedByZeroCostEdge(Node a, Node b) {
        List<Edge> edges = adjacencyList.getOrDefault(a, Collections.emptyList());
        for (Edge edge : edges) {
            if (edge.getTo().equals(b) && edge.getWeight() == 0.0) return true;
        }
        return false;
    }

    private double calculateDistance(Node a, Node b) {
        double dx = a.getLatitude() - b.getLongitude();
        double dy = a.getLatitude() - b.getLongitude();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private Set<Node> getAllNodes() {
        Set<Node> allNodes = new HashSet<>();
        for (Pipeline pipeline : pipelines) {
            allNodes.addAll(pipeline.getNodes());
        }
        return allNodes;
    }

    // Compute MST using Kruskal's Algorithm
    public List<Edge> computeMST() {
        List<Edge> allEdges = new ArrayList<>();
        for (Node node : adjacencyList.keySet()) {
            allEdges.addAll(adjacencyList.get(node));
        }
        // Sort edges by weight
        allEdges.sort(Comparator.comparingDouble(Edge::getWeight));

        Set<Node> allNodes = getAllNodes();
        UnionFind uf = new UnionFind(allNodes);
        List<Edge> mst = new ArrayList<>();

        for (Edge edge : allEdges) {
            Node from = edge.getFrom();
            Node to = edge.getTo();
            if (!uf.find(from).equals(uf.find(to))) {
                uf.union(from, to);
                mst.add(edge);
            }
        }
        return mst;
    }

    // For testing
    public void printMST(List<Edge> mst) {
        System.out.println("Minimal Connection Network (MST):");
        for (Edge edge : mst) {
            System.out.printf("Node %d -> Node %d (weight: %.2f)\n",
                    edge.getFrom().getId(), edge.getTo().getId(), edge.getWeight());
        }
    }

}
