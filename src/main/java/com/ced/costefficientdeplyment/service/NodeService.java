package com.ced.costefficientdeplyment.service;

import com.ced.costefficientdeplyment.dto.NodeDTO;

import java.util.List;

public interface NodeService {

    List<NodeDTO> saveEmptyNodes(List<NodeDTO> nodes);
    List<NodeDTO> findAll(Boolean isEmpty);

}
