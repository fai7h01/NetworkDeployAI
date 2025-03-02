package com.ced.costefficientdeplyment.service;

import com.ced.costefficientdeplyment.dto.PipelineDTO;

import java.util.List;

public interface PipelineService {

    List<PipelineDTO> savePipelines(String filePath, String pattern, boolean underConstruct);

    List<PipelineDTO> findAllEmpty();
    List<PipelineDTO> findAllNonEmpty();

    PipelineDTO findById(Long id);

}
