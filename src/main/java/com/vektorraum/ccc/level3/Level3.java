package com.vektorraum.ccc.level3;

import com.vektorraum.ccc.base.Level;
import com.vektorraum.ccc.base.LevelRunner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

@Slf4j
public class Level3 extends Level {
    @Override
    public String apply(List<String> strings) {

        String[] fieldDef = strings.get(0).split(" ");

        int rows = Integer.parseInt(fieldDef[0]);
        int cols = Integer.parseInt(fieldDef[1]);
        int startRow = Integer.parseInt(fieldDef[2]);
        int startCol = Integer.parseInt(fieldDef[3]);
        String direction = fieldDef[4];

        int[][] field = new int[rows][cols];

        for(int row= 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                field[row][col] = row * cols+col+1;
            }
        }

        if (startRow > 1) {
            ArrayUtils.reverse(field);
        }

        if (startCol > 1) {
            for (int i = 0; i < rows; i++) {
                ArrayUtils.reverse(field[i]);
            }
        }

        String res = "";

        if (direction.equals("O") || direction.equals("W")) {
            for(int currRow = 0; currRow < rows; currRow++){
                if(currRow % 2 == 0){
                    for(int i = 0; i < cols; i++){
                        res += field[currRow][i] +" ";
                    }
                }else{
                    for (int i= cols-1; i >= 0; i--){
                        res += field[currRow][i] + " ";
                    }
                }
            }
        } else {
            for(int currCol = 0; currCol < cols; currCol++){
                if(currCol % 2 == 0){
                    for(int currRow = 0; currRow < rows; currRow++){
                        res += field[currRow][currCol] +" ";
                    }
                }else{
                    for (int currRow= rows-1; currRow >= 0; currRow--){
                        res += field[currRow][currCol] + " ";
                    }
                }
            }
        }

        return res;
    }

    public static void main(String[] args) throws Exception {
        LevelRunner.runOnResourceFolder(new Level3(), "data/level3/in");
    }
}
