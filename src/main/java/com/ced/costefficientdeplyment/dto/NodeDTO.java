package com.ced.costefficientdeplyment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
}
