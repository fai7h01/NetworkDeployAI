package com.ced.costefficientdeplyment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "nodes")
@SQLRestriction("is_deleted = false")
public class Node extends BaseEntity{

    private Long id;
    private Double latitude;
    private Double longitude;
    @ManyToOne
    @JoinColumn(name = "pipeline_id")
    private Pipeline pipeline;

}
