package com.ced.costefficientdeplyment.entity;

import com.ced.costefficientdeplyment.dto.NodeDTO;
import jakarta.persistence.Entity;
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
    private List<NodeDTO> nodeDTOS;
    private int length;
    private boolean isUnderConstruction;

}
