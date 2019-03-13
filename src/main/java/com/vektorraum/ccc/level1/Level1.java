package com.vektorraum.ccc.level1;

import com.vektorraum.ccc.base.Level;
import com.vektorraum.ccc.base.LevelRunner;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Level1 implements Level {
    @Override
    public String apply(List<String> strings) {
        return strings.get(0);
    }

    public static void main(String[] args) throws Exception {
        LevelRunner.runOnResourceFolder(new Level1(), "data/level1/in");
    }
}
