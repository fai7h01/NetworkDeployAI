package com.ced.costefficientdeplyment;

import com.ced.costefficientdeplyment.service.DataLoadingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CostEfficientDeploymentApplication {

    public CostEfficientDeploymentApplication(DataLoadingService dataLoadingService) {
        this.dataLoadingService = dataLoadingService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CostEfficientDeploymentApplication.class, args);
    }

    private final DataLoadingService dataLoadingService;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            dataLoadingService.processPipelinesDataset();
        };
    }
}
