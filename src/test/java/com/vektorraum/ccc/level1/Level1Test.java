package com.vektorraum.ccc.level1;

import com.vektorraum.ccc.base.LevelRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class Level1Test {
    private static final String ROOT_PATH = "data/level1";
    private static final String EXPECTED_OUTPUT_FOLDER = ROOT_PATH + "/out";
    private static final String INPUT_FOLDER = ROOT_PATH + "/in";

    @Test
    public void test() {
        Level1 level = new Level1();
        String result = level.apply(input);
        assertEquals(expectedOutput, result);
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() throws URISyntaxException {
        File[] files = LevelRunner.getResourceFolderFiles(EXPECTED_OUTPUT_FOLDER);
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        return Arrays.stream(files)
                .map(file -> {
                    try {
                        String expectedOutput = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                        List<String> input = Files.readAllLines(Paths.get(INPUT_FOLDER, file.getName()));

                        return new Object[]{file.getName(), input, expectedOutput};
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }

    private String filename;
    private List<String> input;
    private String expectedOutput;

    public Level1Test(String filename, List<String> input, String expectedOutput) {
        this.filename = filename;
        this.input = input;
        this.expectedOutput = expectedOutput;
    }
}