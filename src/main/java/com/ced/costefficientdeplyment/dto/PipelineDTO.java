package com.ced.costefficientdeplyment.dto;

import lombok.NoArgsConstructor;

import java.util.List;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<NodeDTO> getNodes() {
        return nodeDTOS;
    }

    public void setNodeDTOS(List<NodeDTO> nodeDTOS) {
        this.nodeDTOS = nodeDTOS;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isUnderConstruction() {
        return isUnderConstruction;
    }

    public void setUnderConstruction(boolean underConstruction) {
        isUnderConstruction = underConstruction;
    }
}
