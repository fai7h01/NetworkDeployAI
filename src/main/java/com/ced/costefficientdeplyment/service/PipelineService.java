package com.ced.costefficientdeplyment.service;

import com.ced.costefficientdeplyment.dto.PipelineDTO;

import java.util.List;

public interface PipelineService {

    List<PipelineDTO> saveEmptyPipelines();

    List<PipelineDTO> findAllEmpty();

}
