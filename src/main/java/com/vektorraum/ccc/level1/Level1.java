package com.vektorraum.ccc.level1;

import com.vektorraum.ccc.base.Level;
import com.vektorraum.ccc.base.LevelRunner;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Level1 extends Level {
    @Override
    public String apply(List<String> strings) {

        String[] fieldDef = strings.get(0).split(" ");

        int rows = Integer.parseInt(fieldDef[0]);
        int cols = Integer.parseInt(fieldDef[1]);
        int[][] field = new int[rows][cols];

        for(int row= 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                field[row][col] = row * cols+col+1;
            }
        }

        String res = "";

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

        return res;
    }

    public static void main(String[] args) throws Exception {
        LevelRunner.runOnResourceFolder(new Level1(), "data/level1/in");
    }
}
