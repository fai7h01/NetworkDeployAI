package com.ced.costefficientdeplyment.controller;

import com.ced.costefficientdeplyment.dto.PipelineDTO;
import com.ced.costefficientdeplyment.dto.ResponseWrapper;
import com.ced.costefficientdeplyment.service.PipelineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{id}")
    public ResponseEntity<ResponseWrapper> getById(@PathVariable("id") Long id) {
        PipelineDTO pipeline = pipelineService.findById(id);
        return ok(ResponseWrapper.builder()
                .data(pipeline).build());
    }

}
