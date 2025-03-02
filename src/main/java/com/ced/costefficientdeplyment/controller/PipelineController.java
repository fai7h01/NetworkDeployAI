package com.ced.costefficientdeplyment.controller;

import com.ced.costefficientdeplyment.dto.PipelineDTO;
import com.ced.costefficientdeplyment.dto.ResponseWrapper;
import com.ced.costefficientdeplyment.service.PipelineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/pipeline")
public class PipelineController {

    private final PipelineService pipelineService;

    public PipelineController(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    @GetMapping(value = "/empty", produces = "application/json")
    public ResponseEntity<ResponseWrapper> getEmptyPipelines() {
        List<PipelineDTO> pipelineDTOS = pipelineService.findAllEmpty();

        return status(HttpStatus.OK)
                .header("ngrok-skip-browser-warning", "69420")
                .body(ResponseWrapper.builder().data(pipelineDTOS).build());
    }

    @GetMapping(value = "/non-empty", produces = "application/json")
    public ResponseEntity<ResponseWrapper> getNonEmpty() {
        List<PipelineDTO> pipelineDTOS = pipelineService.findAllNonEmpty();
        return status(HttpStatus.OK)
                .header("ngrok-skip-browser-warning", "69420")
                .body(ResponseWrapper.builder().data(pipelineDTOS).build());
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseWrapper> getById(@PathVariable("id") Long id) {
        PipelineDTO pipeline = pipelineService.findById(id);
        return ok(ResponseWrapper.builder()
                .data(pipeline).build());
    }

}
