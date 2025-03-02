package com.ced.costefficientdeplyment.util;

import com.ced.costefficientdeplyment.dto.NodeDTO;
import com.ced.costefficientdeplyment.dto.PipelineDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DataProcessUtil {

    private static final String EMPTY_PIPELINES_PATH = "C:\\Users\\user\\Desktop\\cost-efficient-deplyment\\src\\main\\resources\\Canalitzacions_de_xarxes_de_telecomunicacions_de_la_Generalitat_20250228.csv";
    private static final String PIPELINES_PATH = "C:\\Users\\user\\Desktop\\cost-efficient-deplyment\\src\\main\\resources\\TELCO_FIBRAPIU_CANALITZA_V.csv";
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DataProcessUtil.class);

    public static List<PipelineDTO> processEmptyPipelineDataset() {

        List<PipelineDTO> pipelineDTOS = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try (CSVReader reader = new CSVReader(new FileReader(EMPTY_PIPELINES_PATH))) {
            String[] nextLine;
            List<String[]> rows = new ArrayList<>();
            int lineCounter = 0;
            while ((nextLine = reader.readNext()) != null && lineCounter < 100) {
                rows.add(nextLine);
                lineCounter++;
            }

            List<Callable<Void>> tasks = new ArrayList<>();
            for (String[] row : rows) {
                tasks.add(() -> {
                    List<NodeDTO> nodeDTOS = new ArrayList<>();
                    String multilineString = row[0];
                    String totalLength = row[5];
                    Pattern pattern = Pattern.compile("\\(\\((.*?)\\)\\)");
                    Matcher matcher = pattern.matcher(multilineString);

                    if (matcher.find()) {
                        String coordinatesPart = matcher.group(1);

                        for (String point : coordinatesPart.split(",\\s*")) {
                            point = point.replace("(", "").replace(")", "");
                            String[] coords = point.split("\\s+");
                            double longitude = Double.parseDouble(coords[0].trim());
                            double latitude = Double.parseDouble(coords[1].trim());
                            nodeDTOS.add(new NodeDTO(latitude, longitude));
                        }
                    }

                    PipelineDTO pipelineDTO = new PipelineDTO(new ArrayList<>(nodeDTOS), Integer.parseInt(totalLength.trim()), false);
                    pipelineDTOS.add(pipelineDTO);

                    return null;
                });
            }

            executorService.invokeAll(tasks);

        } catch (IOException | InterruptedException e) {
            log.error("Error occurred while parsing file: {}", e.getMessage());
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
        return pipelineDTOS;
    }

    public void savePipelinesAsJson(List<PipelineDTO> pipelineDTOS) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File("pipelines.json"), pipelineDTOS);
    }

}
