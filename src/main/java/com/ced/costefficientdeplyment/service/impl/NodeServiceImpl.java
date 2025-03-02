package com.ced.costefficientdeplyment.service.impl;

import com.ced.costefficientdeplyment.dto.NodeDTO;
import com.ced.costefficientdeplyment.dto.PipelineDTO;
import com.ced.costefficientdeplyment.entity.Node;
import com.ced.costefficientdeplyment.repository.NodeRepository;
import com.ced.costefficientdeplyment.service.NodeService;
import com.ced.costefficientdeplyment.util.DataProcessUtil;
import com.ced.costefficientdeplyment.util.MapperUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NodeServiceImpl implements NodeService {

    private final NodeRepository nodeRepository;
    private final MapperUtil mapperUtil;

    public NodeServiceImpl(NodeRepository nodeRepository, @Lazy MapperUtil mapperUtil) {
        this.nodeRepository = nodeRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<NodeDTO> saveEmptyNodes(List<NodeDTO> nodes) {
        return nodes.stream()
                .map(nodeDTOS -> {
                    Node entity = mapperUtil.convert(nodeDTOS, new Node());
                    Node saved = nodeRepository.save(entity);
                    return mapperUtil.convert(saved, new NodeDTO());
                })
                .toList();
    }

    @Override
    public List<NodeDTO> findAll(Boolean isEmpty) {
        return nodeRepository.findAll()
                .stream()
                .filter(node -> node.getPipeline().isEmpty())
                .map(node -> mapperUtil.convert(node, new NodeDTO()))
                .toList();
    }

}
