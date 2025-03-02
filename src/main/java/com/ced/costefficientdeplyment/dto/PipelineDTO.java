package com.ced.costefficientdeplyment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PipelineDTO that = (PipelineDTO) object;
        return length == that.length && isUnderConstruction == that.isUnderConstruction && Objects.equals(id, that.id) && Objects.equals(nodes, that.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nodes, length, isUnderConstruction);
    }
}
