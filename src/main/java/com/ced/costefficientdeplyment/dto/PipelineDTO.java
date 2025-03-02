package com.ced.costefficientdeplyment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PipelineDTO {

    private Long id;
    private List<NodeDTO> nodeDTOS;
    private int length;
    private boolean isUnderConstruction;

    public PipelineDTO(List<NodeDTO> nodeDTOS, int length, boolean isUnderConstruction) {
        this.nodeDTOS = nodeDTOS;
        this.length = length;
        this.isUnderConstruction = isUnderConstruction;
    }

}
