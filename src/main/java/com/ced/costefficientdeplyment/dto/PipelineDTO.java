package com.ced.costefficientdeplyment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PipelineDTO {

    private Long id;
    private List<NodeDTO> nodes;
    private int length;
    private boolean isUnderConstruction;

    public PipelineDTO(List<NodeDTO> nodes, int length, boolean isUnderConstruction) {
        this.nodes = nodes;
        this.length = length;
        this.isUnderConstruction = isUnderConstruction;
    }

}
