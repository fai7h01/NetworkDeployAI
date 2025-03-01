package com.ced.costefficientdeplyment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnionFind {

    private Map<Node, Node> parent;
    private Map<Node, Integer> rank;

    public UnionFind(Set<Node> nodes) {
        parent = new HashMap<>();
        rank = new HashMap<>();
        for (Node node : nodes) {
            parent.put(node, node);
            rank.put(node, 0);
        }
    }

    public Node find(Node node) {
        if (!parent.get(node).equals(node)) {
            parent.put(node, find(parent.get(node)));
        }
        return parent.get(node);
    }

    public void union(Node a, Node b) {
        Node rootA = find(a);
        Node rootB = find(b);
        if (rootA.equals(rootB)) {
            return;
        }
        int rankA = rank.get(rootA);
        int rankB = rank.get(rootB);
        if (rankA < rankB) {
            parent.put(rootA, rootB);
        } else if (rankA > rankB) {
            parent.put(rootB, rootA);
        } else {
            parent.put(rootB, rootA);
            rank.put(rootA, rankA + 1);
        }
    }
}
