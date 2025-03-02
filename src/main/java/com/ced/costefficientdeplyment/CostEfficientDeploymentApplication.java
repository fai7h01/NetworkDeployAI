package com.ced.costefficientdeplyment;

import com.ced.costefficientdeplyment.dto.NodeDTO;
import com.ced.costefficientdeplyment.dto.PipelineDTO;
import com.ced.costefficientdeplyment.service.NodeService;
import com.ced.costefficientdeplyment.service.PipelineService;
import com.ced.costefficientdeplyment.util.DataProcessUtil;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class CostEfficientDeploymentApplication {

    public CostEfficientDeploymentApplication(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CostEfficientDeploymentApplication.class, args);
    }

    private final PipelineService pipelineService;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            pipelineService.saveEmptyPipelines();
        };
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }
}
