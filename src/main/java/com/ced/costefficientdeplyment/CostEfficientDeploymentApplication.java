package com.ced.costefficientdeplyment;

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

    private static final String EMPTY_PIPELINES_PATH = "C:\\Users\\user\\Desktop\\cost-efficient-deplyment\\src\\main\\resources\\Canalitzacions_de_xarxes_de_telecomunicacions_de_la_Generalitat_20250228.csv";
    private static final String NON_EMPTY_PIPELINES_PATH = "C:\\Users\\user\\Desktop\\cost-efficient-deplyment\\src\\main\\resources\\part2.csv";

    private static final String SIMPLE_PATTERN = "\\(\\((.*?)\\)\\)";

    private final PipelineService pipelineService;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            pipelineService.savePipelines(NON_EMPTY_PIPELINES_PATH, SIMPLE_PATTERN, true);
            pipelineService.savePipelines(EMPTY_PIPELINES_PATH, SIMPLE_PATTERN, false);
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
