package com.vektorraum.ccc.level1;

import com.vektorraum.ccc.base.Level;
import com.vektorraum.ccc.base.LevelRunner;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Level1 extends Level {
    @Override
    public String apply(List<String> strings) {
        String[] inputs = strings.get(0).split(" ");
        double wheelBase = Double.parseDouble(inputs[0]);
        int n = Integer.parseInt(inputs[1]);

        List<SteeringCommand> commands = new ArrayList<>();

        for (int i = 2; i < 2*n + 2; ) {
            double distance = Double.parseDouble(inputs[i++]);
            double steeringAngle = Double.parseDouble(inputs[i++]);
            commands.add(new SteeringCommand(distance, steeringAngle));
        }

        Rover rover = new Rover(wheelBase);

        commands.forEach(rover::steer);

        return String.format("%.2f %.2f %.2f", rover.getPosition().getX(), rover.getPosition().getY(), rover.getPosition().getAngle()).replace(",",".");

    }

    public static void main(String[] args) throws Exception {
        LevelRunner.runOnResourceFolder(new Level1(), "data/level1/in");
    }
}
