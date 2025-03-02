package com.ced.costefficientdeplyment.dto;

import lombok.*;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
public class Graph {

    private List<PipelineDTO> pipelineDTOS;
    private Map<NodeDTO, List<Edge>> adjacencyList;

    public Graph(List<PipelineDTO> pipelineDTOS) {
        this.pipelineDTOS = pipelineDTOS;
        this.adjacencyList = new HashMap<>();

    }

    public void buildAdjacencyList() {
        // Add zero-cost edges for under-construction pipelines
        for (PipelineDTO pipelineDTO : pipelineDTOS) {
            if (pipelineDTO.isUnderConstruction()) {
                List<NodeDTO> nodeDTOS = pipelineDTO.getNodes();
                for (int i = 0; i < nodeDTOS.size() - 1; i++) {
                    NodeDTO from = nodeDTOS.get(i);
                    NodeDTO to = nodeDTOS.get(i + 1);
                    addEdge(from, to, 0.0);
                    addEdge(to, from, 0.0);
                }
            }
        }

        Set<NodeDTO> allNodeDTOS = getAllNodes();
        List<NodeDTO> nodeDTOList = new ArrayList<>(allNodeDTOS);
        for (int i = 0; i < nodeDTOList.size(); i++) {
            NodeDTO from = nodeDTOList.get(i);
            for (int j = i + 1; j < nodeDTOList.size(); j++) {
                NodeDTO to = nodeDTOList.get(j);
                if (!areConnectedByZeroCostEdge(from, to)) {
                   double distance = distanceAlongLatitude(from, to);
                    addEdge(from, to, distance);
                    addEdge(to, from, distance);
                }
            }
        }
    }

    private void addEdge(NodeDTO from, NodeDTO to, double weight) {
        adjacencyList.computeIfAbsent(from, k -> new ArrayList<>()).add(new Edge(from, to, weight));
    }

    private boolean areConnectedByZeroCostEdge(NodeDTO a, NodeDTO b) {
        List<Edge> edges = adjacencyList.getOrDefault(a, Collections.emptyList());
        for (Edge edge : edges) {
            if (edge.getTo().equals(b) && edge.getWeight() == 0.0) return true;
        }
        return false;
    }

    // Function to calculate distance along a parallel of latitude
    public double distanceAlongLatitude(NodeDTO from, NodeDTO to) {
        double earthRadius = 6371;
        // Convert latitude and longitude differences to radians
        double latRad = Math.toRadians(from.getLatitude());
        double deltaLonRad = Math.toRadians(Math.abs(to.getLongitude() - from.getLongitude()));

        // Calculate the distance
        return earthRadius * Math.cos(latRad) * deltaLonRad;
    }

    private Set<NodeDTO> getAllNodes() {
        Set<NodeDTO> allNodeDTOS = new HashSet<>();
        for (PipelineDTO pipelineDTO : pipelineDTOS) {
            allNodeDTOS.addAll(pipelineDTO.getNodes());
        }
        return allNodeDTOS;
    }

    // Compute MST using Kruskal's Algorithm
    public List<Edge> computeMST() {
        List<Edge> allEdges = new ArrayList<>();
        for (NodeDTO nodeDTO : adjacencyList.keySet()) {
            allEdges.addAll(adjacencyList.get(nodeDTO));
        }
        // Sort edges by weight
        allEdges.sort(Comparator.comparingDouble(Edge::getWeight));

        Set<NodeDTO> allNodeDTOS = getAllNodes();
        UnionFind uf = new UnionFind(allNodeDTOS);
        List<Edge> mst = new ArrayList<>();

        for (Edge edge : allEdges) {
            NodeDTO from = edge.getFrom();
            NodeDTO to = edge.getTo();
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
