package com.ced.costefficientdeplyment.dto;

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
    private PipelineDTO pipeline;

    public NodeDTO(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }


}
