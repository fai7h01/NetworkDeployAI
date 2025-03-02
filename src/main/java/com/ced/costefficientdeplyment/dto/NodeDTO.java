package com.ced.costefficientdeplyment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NodeDTO {

    private Long id;
    private Double latitude;
    private Double longitude;
    @JsonIgnore
    private PipelineDTO pipeline;

    public NodeDTO(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        NodeDTO nodeDTO = (NodeDTO) object;
        return Objects.equals(id, nodeDTO.id) && Objects.equals(latitude, nodeDTO.latitude) && Objects.equals(longitude, nodeDTO.longitude) && Objects.equals(pipeline, nodeDTO.pipeline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude, pipeline);
    }
}
