package com.ced.costefficientdeplyment;

import com.ced.costefficientdeplyment.util.DataProcessUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CostEfficientDeploymentApplication {

    public CostEfficientDeploymentApplication(DataProcessUtil dataProcessUtil) {
        this.dataProcessUtil = dataProcessUtil;
    }

    public static void main(String[] args) {
        SpringApplication.run(CostEfficientDeploymentApplication.class, args);
    }

    private final DataProcessUtil dataProcessUtil;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            dataProcessUtil.processEmptyPipelineDataset();
        };
    }
}
