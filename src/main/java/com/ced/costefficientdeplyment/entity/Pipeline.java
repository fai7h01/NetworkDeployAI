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
    private boolean isEmpty;
    @OneToMany(mappedBy = "pipeline", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Node> nodes;

}
