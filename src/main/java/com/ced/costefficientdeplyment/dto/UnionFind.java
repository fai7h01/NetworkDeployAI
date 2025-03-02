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

    private Map<NodeDTO, NodeDTO> parent;
    private Map<NodeDTO, Integer> rank;

    public UnionFind(Set<NodeDTO> nodeDTOS) {
        parent = new HashMap<>();
        rank = new HashMap<>();
        for (NodeDTO nodeDTO : nodeDTOS) {
            parent.put(nodeDTO, nodeDTO);
            rank.put(nodeDTO, 0);
        }
    }

    public NodeDTO find(NodeDTO nodeDTO) {
        if (!parent.get(nodeDTO).equals(nodeDTO)) {
            parent.put(nodeDTO, find(parent.get(nodeDTO)));
        }
        return parent.get(nodeDTO);
    }

    public void union(NodeDTO a, NodeDTO b) {
        NodeDTO rootA = find(a);
        NodeDTO rootB = find(b);
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
