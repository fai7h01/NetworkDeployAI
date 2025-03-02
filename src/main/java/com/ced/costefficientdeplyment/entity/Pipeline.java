package com.ced.costefficientdeplyment.entity;

import jakarta.persistence.*;
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
    @OneToMany(mappedBy = "pipeline", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Node> nodes;

}
