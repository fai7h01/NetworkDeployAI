package com.ced.costefficientdeplyment.util;

import com.ced.costefficientdeplyment.dto.NodeDTO;
import com.ced.costefficientdeplyment.dto.PipelineDTO;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DataProcessUtil {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DataProcessUtil.class);

    public static Map<PipelineDTO, List<NodeDTO>> processPipelineDataset(String filePath, String pattern, boolean underConstruct) {
        log.info("File path in util class: {}", filePath);
        Map<PipelineDTO, List<NodeDTO>> map = new ConcurrentHashMap<>();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            Resource resource = new ClassPathResource(filePath);
            InputStream inputStream = resource.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            CSVReader csvReader = new CSVReader(inputStreamReader);

            String[] nextLine;
            List<String[]> rows = new ArrayList<>();
            int lineCounter = 0;

            while ((nextLine = csvReader.readNext()) != null && lineCounter < 50) {
                rows.add(nextLine);
                lineCounter++;
            }

            csvReader.close();

            List<Callable<Void>> tasks = new ArrayList<>();
            for (String[] row : rows) {
                tasks.add(() -> {
                    List<NodeDTO> nodeDTOS = new ArrayList<>();
                    String multilineString = row[0];
                    String totalLength = row[5];
                    Pattern p = Pattern.compile(pattern);
                    Matcher matcher = p.matcher(multilineString);

                    if (matcher.find()) {
                        String coordinatesPart = matcher.group(1);
                        log.info("\n\nCoordinates: {}", coordinatesPart);
                        for (String point : coordinatesPart.split(",\\s*")) {
                            point = point.replace("(", "").replace(")", "");
                            String[] coords = point.split("\\s+");
                            try {
                                double longitude = Double.parseDouble(coords[0].trim());
                                double latitude = Double.parseDouble(coords[1].trim());
                                nodeDTOS.add(new NodeDTO(latitude, longitude));
                            } catch (NumberFormatException e) {
                                log.error("Error parsing coordinates: {}", e.getMessage());
                            }
                        }
                    }

                    PipelineDTO pipelineDTO = new PipelineDTO(new ArrayList<>(nodeDTOS), Integer.parseInt(totalLength.trim()), underConstruct);
                    map.putIfAbsent(pipelineDTO, nodeDTOS);

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
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
        }

        return map;
    }
}
