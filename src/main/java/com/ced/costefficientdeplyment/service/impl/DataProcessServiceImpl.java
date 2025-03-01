package com.ced.costefficientdeplyment.service.impl;

import com.ced.costefficientdeplyment.dto.Node;
import com.ced.costefficientdeplyment.dto.Pipeline;
import com.ced.costefficientdeplyment.service.DataLoadingService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DataProcessServiceImpl implements DataLoadingService {

    private static final String EMPTY_PIPELINES_PATH = "C:\\Users\\user\\Desktop\\cost-efficient-deplyment\\src\\main\\resources\\Canalitzacions_de_xarxes_de_telecomunicacions_de_la_Generalitat_20250228.csv";
    private static final String PIPELINES_PATH = "C:\\Users\\user\\Desktop\\cost-efficient-deplyment\\src\\main\\resources\\TELCO_FIBRAPIU_CANALITZA_V.csv";
    private static final Logger log = LoggerFactory.getLogger(DataProcessServiceImpl.class);


    @Override
    public void processEmptyPipelineDataset() {

        List<Pipeline> pipelines = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try (CSVReader reader = new CSVReader(new FileReader(EMPTY_PIPELINES_PATH))) {
            String[] nextLine;
            List<String[]> rows = new ArrayList<>();
            while ((nextLine = reader.readNext()) != null ) {
                rows.add(nextLine);
            }

            List<Callable<Void>> tasks = new ArrayList<>();
            for (String[] row : rows) {
                tasks.add(() -> {
                    Set<Node> nodes = new HashSet<>();
                    String multilineString = row[0];
                    System.out.println("Multiline string: " + multilineString);
                    // Extract coordinates using regex
                    Pattern pattern = Pattern.compile("\\(\\((.*?)\\)\\)");
                    Matcher matcher = pattern.matcher(multilineString);

                    if (matcher.find()) {
                        String coordinatesPart = matcher.group(1);

                        for (String point : coordinatesPart.split(",\\s*")) {
                            point = point.replace("(", "").replace(")", "");
                            String[] coords = point.split("\\s+");
                            double longitude = Double.parseDouble(coords[0].trim());
                            double latitude = Double.parseDouble(coords[1].trim());
                            nodes.add(new Node(latitude, longitude));
                        }
                    }
                    // Create a new pipeline and set its nodes
                    Pipeline pipeline = new Pipeline(new ArrayList<>(nodes), false);
                    pipelines.add(pipeline);

                    return null;
                });
            }

            // Execute tasks in parallel
            executorService.invokeAll(tasks);  // Blocks until all tasks are finished

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();  //shut down the executor
        }

        // check results
        System.out.println(pipelines);

    }


}
