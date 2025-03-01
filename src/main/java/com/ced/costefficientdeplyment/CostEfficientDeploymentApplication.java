package com.ced.costefficientdeplyment;

import com.ced.costefficientdeplyment.dto.Pipeline;
import com.ced.costefficientdeplyment.service.DataLoadingService;
import com.ced.costefficientdeplyment.util.DataProcessUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class CostEfficientDeploymentApplication {

    public CostEfficientDeploymentApplication(DataProcessUtil dataProcessUtil, @Lazy DataLoadingService dataLoadingService) {
        this.dataProcessUtil = dataProcessUtil;
        this.dataLoadingService = dataLoadingService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CostEfficientDeploymentApplication.class, args);
    }

    private final DataProcessUtil dataProcessUtil;
    private final DataLoadingService dataLoadingService;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            //List<Pipeline> pipelines = dataProcessUtil.processEmptyPipelineDataset();
            //dataProcessUtil.savePipelinesAsJson(pipelines);
            //dataLoadingService.load();
        };
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }
}
