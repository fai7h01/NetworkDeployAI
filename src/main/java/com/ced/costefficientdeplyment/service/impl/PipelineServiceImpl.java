package com.ced.costefficientdeplyment.service.impl;

import com.ced.costefficientdeplyment.dto.PipelineDTO;
import com.ced.costefficientdeplyment.entity.Node;
import com.ced.costefficientdeplyment.entity.Pipeline;
import com.ced.costefficientdeplyment.repository.NodeRepository;
import com.ced.costefficientdeplyment.repository.PipelineRepository;
import com.ced.costefficientdeplyment.service.PipelineService;
import com.ced.costefficientdeplyment.util.DataProcessUtil;
import com.ced.costefficientdeplyment.util.MapperUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PipelineServiceImpl implements PipelineService {

    private final PipelineRepository pipelineRepository;
    private final MapperUtil mapperUtil;
    private final NodeRepository nodeRepository;

    public PipelineServiceImpl(PipelineRepository pipelineRepository, @Lazy MapperUtil mapperUtil, NodeRepository nodeRepository) {
        this.pipelineRepository = pipelineRepository;
        this.mapperUtil = mapperUtil;
        this.nodeRepository = nodeRepository;
    }

    @Override
    public List<PipelineDTO> saveEmptyPipelines() {
        var pipelineMap = DataProcessUtil.processEmptyPipelineDataset();
        List<PipelineDTO> pipelineDTOS = pipelineMap.keySet()
                .stream()
                .toList();
        return pipelineDTOS.stream()
                .map(pipelineDTO -> {
                    Pipeline entity = mapperUtil.convert(pipelineDTO, new Pipeline());
                    List<Node> nodes = pipelineDTO.getNodes().stream().map(nodeDTO -> {
                        Node node = mapperUtil.convert(nodeDTO, new Node());
                        node.setPipeline(entity);
                        return node;
                    }).toList();
                    entity.setNodes(nodes);
                    Pipeline saved = pipelineRepository.save(entity);
                    return mapperUtil.convert(saved, new PipelineDTO());
                })
                .toList();
    }

    @Override
    public List<PipelineDTO> findAllEmpty() {
        return pipelineRepository.findAll()
                .stream()
                .filter(pipeline -> !pipeline.isEmpty())
                .map(pipeline -> mapperUtil.convert(pipeline, new PipelineDTO()))
                .toList();
    }

    @Override
    public PipelineDTO findById(Long id) {
        Pipeline pipeline = pipelineRepository.findById(id).orElseThrow();
        return mapperUtil.convert(pipeline, new PipelineDTO());
    }

}
