package com.ced.costefficientdeplyment.controller;

import com.ced.costefficientdeplyment.dto.PipelineDTO;
import com.ced.costefficientdeplyment.dto.ResponseWrapper;
import com.ced.costefficientdeplyment.service.PipelineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/pipeline")
public class PipelineController {

    private final PipelineService pipelineService;

    public PipelineController(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getEmptyPipelines() {
        List<PipelineDTO> pipelineDTOS = pipelineService.findAllEmpty();
        return ok(ResponseWrapper.builder()
                .data(pipelineDTOS).build());
    }



}
