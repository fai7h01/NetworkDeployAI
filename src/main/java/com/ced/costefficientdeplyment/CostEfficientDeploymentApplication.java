package com.ced.costefficientdeplyment;

import com.ced.costefficientdeplyment.service.PipelineService;
import com.ced.costefficientdeplyment.util.DataProcessUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Profile("dev")
@SpringBootApplication
public class CostEfficientDeploymentApplication {

    public CostEfficientDeploymentApplication(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CostEfficientDeploymentApplication.class, args);
    }

    @Value("${file.path.empty}")
    private String emptyPipesPath;
    @Value("${file.path.non-empty}")
    private String nonEmptyPipesPath;

    private static final String SIMPLE_PATTERN = "\\(\\((.*?)\\)\\)";

    private final PipelineService pipelineService;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            pipelineService.savePipelines(nonEmptyPipesPath, SIMPLE_PATTERN, false);
            pipelineService.savePipelines(emptyPipesPath, SIMPLE_PATTERN, true);
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
