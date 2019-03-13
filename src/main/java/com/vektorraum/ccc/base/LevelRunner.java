package com.vektorraum.ccc.base;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class LevelRunner {
    public static void runOnResourceFolder(Level level, String folderPath) throws IOException, URISyntaxException {
        log.debug("Running level: {} on folder: {}", level.getClass().getSimpleName(), folderPath);
        Map<String, String> results = new HashMap<>();

        for (File file : getResourceFolderFiles(folderPath)) {
            List<String> fileContents = Files.readAllLines(file.toPath());
            log.debug("Applying file {}", file.getName());
            String result = level.apply(fileContents);
            log.debug("Result from applying file: {}", result);
            results.put(file.getName(), result);
        }

        log.info("Results: {}", results);

        System.out.println("=================");
        System.out.println("==== RESULTS ====");
        System.out.println("=================");
        for (String resultKey : results.keySet()) {
            System.out.println("==== " + resultKey + " ====");
            System.out.println(results.get(resultKey));
        }

    }

    public static File[] getResourceFolderFiles (String folder) throws URISyntaxException {
        File[] files = new File(folder).listFiles();
        return files;
    }
}
