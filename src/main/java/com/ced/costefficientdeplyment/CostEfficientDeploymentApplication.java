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

@Profile("prod")
@SpringBootApplication
public class CostEfficientDeploymentApplication {

    public CostEfficientDeploymentApplication(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CostEfficientDeploymentApplication.class, args);
    }

    //private static final String EMPTY_PIPELINES_PATH = "C:\\Users\\user\\Desktop\\cost-efficient-deplyment\\src\\main\\resources\\Canalitzacions_de_xarxes_de_telecomunicacions_de_la_Generalitat_20250228.csv";
    //private static final String NON_EMPTY_PIPELINES_PATH = "C:\\Users\\user\\Desktop\\cost-efficient-deplyment\\src\\main\\resources\\part2.csv";

    @Value("${file.path.empty}")
    private String path1;
    @Value("${file.path.non-empty}")
    private String path2;

    private static final String SIMPLE_PATTERN = "\\(\\((.*?)\\)\\)";

    private final PipelineService pipelineService;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            pipelineService.savePipelines(path2, SIMPLE_PATTERN, true);
            pipelineService.savePipelines(path1, SIMPLE_PATTERN, false);
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
