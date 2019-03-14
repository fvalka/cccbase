package com.vektorraum.ccc.level1;

import com.vektorraum.ccc.base.Level;
import com.vektorraum.ccc.base.LevelRunner;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Level1 extends Level {
    @Override
    public String apply(List<String> strings) {
        String[] inputs = strings.get(0).split(" ");
        double wheelBase = Double.parseDouble(inputs[0]);
        double distance = Double.parseDouble(inputs[1]);
        double steeringAngle = Double.parseDouble(inputs[2]);

        double radius = wheelBase / Math.sin(Math.toRadians(steeringAngle));
        log.info("Radius: {}", radius);

        double finalAngle = 180.0 * (distance) / (radius * Math.PI);

        double dx = radius * (1 - Math.cos(Math.toRadians(finalAngle)));
        double dy = Math.sin(Math.toRadians(finalAngle)) * radius;
/*
        if (steeringAngle < 0) {
            dx = -1.0 * dx;
        }

        if (distance < 0) {
            dy = -1.0 * dy;
        }*/

        if (finalAngle < 0) {
            finalAngle = 360.00 + finalAngle;
        }

        return String.format("%.2f %.2f %.2f", dx, dy, finalAngle).replace(",",".");
    }

    public static void main(String[] args) throws Exception {
        LevelRunner.runOnResourceFolder(new Level1(), "data/level1/in");
    }
}
