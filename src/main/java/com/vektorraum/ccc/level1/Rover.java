package com.vektorraum.ccc.level1;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Rover {
    private Position position = new Position();
    private double wheelBase;

    public Rover(double wheelBase) {
        this.wheelBase = wheelBase;
    }

    public void reset() {
        this.position = new Position();
    }

    public void steer(SteeringCommand cmd) {
        log.info("Executing steer command {}", cmd);
        Position steerResult = calculateOneSteer(cmd);
        log.info("Steer command position result: {}", steerResult);
        this.position = this.position.add(steerResult);
        log.info("New rover position {}", this.position);
    }

    private Position calculateOneSteer(SteeringCommand cmd) {
        if (cmd.getSteeringAngle() == 0.0) {
            return new Position(0.0, cmd.getDistance(), 0.0);
        }

        double radius = wheelBase / Math.sin(Math.toRadians(cmd.getSteeringAngle()));
        log.info("Radius: {}", radius);

        double finalAngle = 180.0 * (cmd.getDistance()) / (radius * Math.PI);

        double dx = radius * (1 - Math.cos(Math.toRadians(finalAngle)));
        double dy = Math.sin(Math.toRadians(finalAngle)) * radius;

        while (finalAngle < 0) {
            finalAngle = 360.00 + finalAngle;
        }

        return new Position(dx, dy, finalAngle);
    }


}
