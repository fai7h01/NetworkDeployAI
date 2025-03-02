package com.ced.costefficientdeplyment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pipelines")
@SQLRestriction("is_deleted = false")
public class Pipeline extends BaseEntity{

    private Long id;
    private int length;
    private boolean isUnderConstruction;
    @OneToMany(mappedBy = "pipeline")
    private List<Node> nodeDTOS;

}
