package com.vektorraum.ccc.level4;

import com.vektorraum.ccc.base.Level;
import com.vektorraum.ccc.base.LevelRunner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@SuppressWarnings("ALL")
public class Level4 extends Level {
    @Override
    public String apply(List<String> strings) {

        String[] fieldDef = strings.get(0).split(" ");

        int rows = Integer.parseInt(fieldDef[0]);
        int cols = Integer.parseInt(fieldDef[1]);
        int startRow = Integer.parseInt(fieldDef[2]);
        int startCol = Integer.parseInt(fieldDef[3]);
        String direction = fieldDef[4];
        String modus = fieldDef[5];
        List<Integer> rowOrder = IntStream.range(0, rows).boxed().collect(Collectors.toList());
        List<Integer> colOrder = IntStream.range(0, cols).boxed().collect(Collectors.toList());

        log.info("Input data - rows: {}, cols: {}, startRow: {}, startCol: {}, direction: {}, modus: {}",
                rows, cols, startRow, startCol, direction, modus);

        int[][] field = new int[rows][cols];

        return driveMeCrazyDave(rows, cols, startRow, startCol, direction, modus, rowOrder, colOrder, field);
    }

    private String driveMeCrazyDave(int rows, int cols, int startRow, int startCol, String direction, String modus, List<Integer> rowOrder, List<Integer> colOrder, int[][] field) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                field[row][col] = row * cols + col + 1;
            }
        }

        if (modus.equals("Z")) {
            if (direction.equals("O") || direction.equals("W")) {
                if (startCol > 1) {
                    Collections.reverse(colOrder);
                }
                if (startRow == 1) {
                    Collections.reverse(rowOrder);
                }
                rowOrder = this.circularSwapList(rowOrder);
            } else {
                if (startRow > 1) {
                    Collections.reverse(rowOrder);
                }
                if (startCol == 1) {
                    Collections.reverse(colOrder);
                }
                colOrder = this.circularSwapList(colOrder);
            }
        }

        if (modus.equals("S")) {
            if (startCol > 1) {
                Collections.reverse(colOrder);
            }

            if (startRow > 1) {
                Collections.reverse(rowOrder);
            }
        }

        log.info("Row order after swaps: {}\n Col order after swaps: {}", rowOrder, colOrder);

        String res = "";

        log.info("Input matrix: {}", field);

        if (direction.equals("O") || direction.equals("W")) {
            int i = 0;
            for (int currRow : rowOrder) {
                List<Integer> copyColOrder = new ArrayList<>(colOrder);
                if (i++ % 2 != 0) {
                    Collections.reverse(copyColOrder);
                }
                for (int currCol : copyColOrder) {
                    res += field[currRow][currCol] + " ";
                }
            }

        } else {
            int i = 0;
            for (int currCol : colOrder) {
                List<Integer> copyRowOrder = new ArrayList<>(rowOrder);
                if (i++ % 2 != 0) {
                    Collections.reverse(copyRowOrder);
                }
                log.info("Row{} in col: {}", copyRowOrder, currCol);
                for (int currRow : copyRowOrder) {
                    res += field[currRow][currCol] + " ";
                }
            }
        }

        return res;
    }

    public List<Integer> circularSwapList(List<Integer> list) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < list.size() / 2; i++) {
            result.add(list.get(list.size() - 1 - i));
            result.add(list.get(i));
        }
        return result;
    }


    public static void main(String[] args) throws Exception {
        LevelRunner.runOnResourceFolder(new Level4(), "data/level4/in");
    }
}
