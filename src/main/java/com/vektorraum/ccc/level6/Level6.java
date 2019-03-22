package com.vektorraum.ccc.level6;

import com.vektorraum.ccc.base.Level;
import com.vektorraum.ccc.base.LevelRunner;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@SuppressWarnings("ALL")
public class Level6 extends Level {
    @Override
    public String apply(List<String> strings) {

        String[] fieldDef = strings.get(0).split(" ");

        int rows = Integer.parseInt(fieldDef[0]);
        int cols = Integer.parseInt(fieldDef[1]);
        int startRow = Integer.parseInt(fieldDef[2]);
        int startCol = Integer.parseInt(fieldDef[3]);
        String direction = fieldDef[4];
        String modus = fieldDef[5];
        int width = Integer.parseInt(fieldDef[6]);
        List<Integer> rowOrder = IntStream.range(0, rows).boxed().collect(Collectors.toList());
        List<Integer> colOrder = IntStream.range(0, cols).boxed().collect(Collectors.toList());

        log.info("Input data - rows: {}, cols: {}, startRow: {}, startCol: {}, direction: {}, modus: {}",
                rows, cols, startRow, startCol, direction, modus);

        int[][] field = new int[rows][cols];

        if (width > 1) {
            List<Integer> rowOrder0 = new ArrayList<>(rowOrder);
            List<Integer> rowOrder1 = new ArrayList<>(rowOrder);
            List<Integer> colOrder0 = new ArrayList<>(colOrder);
            List<Integer> colOrder1 = new ArrayList<>(colOrder);

            if (direction.equals("O") || direction.equals("W")) {
                Pair<List<Integer>, List<Integer>> listPair;
                if (modus.equals("Z")) {
                    listPair = splitList(rowOrder);
                } else {
                    listPair = splitList(rowOrder);
                }
                rowOrder0 = listPair.getValue0();
                rowOrder1 = listPair.getValue1();
            } else {
                Pair<List<Integer>, List<Integer>> listPair;
                if (modus.equals("Z")) {
                    listPair = splitList(colOrder);
                } else {
                    listPair = splitList(colOrder);
                }
                colOrder0 = listPair.getValue0();
                colOrder1 = listPair.getValue1();
            }

            log.info("Split Rows: {} and {}", rowOrder0, rowOrder1);
            log.info("Split Cols: {} and {}", colOrder0, colOrder1);

            String[] left = driveMeCrazyDave(rows, cols, startRow, startCol, direction, modus, rowOrder0, colOrder0, field).split(" ");
            log.info("Columns: {} and {}", colOrder0, colOrder1);
            String[] right = driveMeCrazyDave(rows, cols, startRow, startCol, direction, modus, rowOrder1, colOrder1, field).split(" ");

            log.info("Left result: {}", Arrays.asList(left));
            log.info("Right result: {}", Arrays.asList(right));

            String res = "";

            int leftIndex = 0;
            int rightIndex = 0;
            for (int i = 0; i < left.length + right.length;) {

                if (leftIndex < left.length) {
                    res += left[leftIndex];
                    i++;
                    res += " ";
                    leftIndex++;
                } else {
                    res += "0 ";
                }

                if (rightIndex < right.length) {
                    res += right[rightIndex];
                    i++;
                    res += " ";
                    rightIndex++;
                } else {
                    res += "0 ";
                }
            }

            return res;

        } else {
            return driveMeCrazyDave(rows, cols, startRow, startCol, direction, modus, rowOrder, colOrder, field);
        }
    }

    private Pair<List<Integer>, List<Integer>> splitListWidthOne(List<Integer> list) {
        log.info("Splitting list into two with width=1: {}", list);
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0) {
                list1.add(list.get(i));
            } else {
                list2.add(list.get(i));
            }

            if (list.size() % 2 == 0 && i - 1 == list.size() / 2) {
                list2.add(list.get(++i));
            }
        }

        return new Pair<List<Integer>, List<Integer>>(list1, list2);
    }

    private Pair<List<Integer>, List<Integer>> splitList(List<Integer> list) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        list1.add(list.get(0));

        for (int i = 1; i < list.size();) {
            if (i/2 % 2 != 0) {
                list1.add(list.get(i++));

                if (i < list.size()) {
                    list1.add(list.get(i++));
                }
            } else {
                list2.add(list.get(i++));

                if (i < list.size()) {
                    list2.add(list.get(i++));
                }
            }
        }

        return new Pair<List<Integer>, List<Integer>>(list1, list2);
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
                log.info("Row order before circular swap: {}", rowOrder);
                rowOrder = this.circularSwapList(rowOrder);
                log.info("Row order after circular swap: {}", rowOrder);
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
        if (list.size() % 2 != 0) {
            result.add(list.get(list.size() % 2 ));
        }
        return result;
    }


    public static void main(String[] args) throws Exception {
        LevelRunner.runOnResourceFolder(new Level6(), "data/level5/in");
    }
}
