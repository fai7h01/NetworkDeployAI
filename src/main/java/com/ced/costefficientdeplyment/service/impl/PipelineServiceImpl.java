package com.ced.costefficientdeplyment.service.impl;

import com.ced.costefficientdeplyment.dto.PipelineDTO;
import com.ced.costefficientdeplyment.entity.Pipeline;
import com.ced.costefficientdeplyment.repository.PipelineRepository;
import com.ced.costefficientdeplyment.service.PipelineService;
import com.ced.costefficientdeplyment.util.DataProcessUtil;
import com.ced.costefficientdeplyment.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PipelineServiceImpl implements PipelineService {

    private final PipelineRepository pipelineRepository;
    private final MapperUtil mapperUtil;

    public PipelineServiceImpl(PipelineRepository pipelineRepository, MapperUtil mapperUtil) {
        this.pipelineRepository = pipelineRepository;
        this.mapperUtil = mapperUtil;
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
                    Pipeline saved = pipelineRepository.save(entity);
                    return mapperUtil.convert(saved, new PipelineDTO());
                })
                .toList();
    }

    @Override
    public List<PipelineDTO> findAll() {
        return pipelineRepository.findAll()
                .stream()
                .map(pipeline -> mapperUtil.convert(pipeline, new PipelineDTO()))
                .toList();
    }
}
