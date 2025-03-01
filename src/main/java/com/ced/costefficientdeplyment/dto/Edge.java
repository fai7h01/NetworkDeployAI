package com.ced.costefficientdeplyment.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Edge {

    private Node from;
    private Node to;
    private double weight;

}
