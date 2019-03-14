package com.vektorraum.ccc.level1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Position {
    private double x = 0.0;
    private double y = 0.0;
    private double angle = 0.0;

    public Position add(Position otherPos) {
        double newX = this.x + this.rotateVecX(otherPos.x, otherPos.y, angle);
        double newY = this.y + this.rotateVecY(otherPos.x, otherPos.y, angle);

        double newAngle = this.angle + otherPos.angle;

        while (newAngle >= 360) {
            newAngle = newAngle - 360;
        }

        return new Position(newX, newY, newAngle);
    }

    private double rotateVecX(double x, double y, double alpha) {
        return Math.cos(Math.toRadians(alpha)) * x + Math.sin(Math.toRadians(alpha)) * y;
    }

    private double rotateVecY(double x, double y, double alpha) {
        return -1.0 * Math.sin(Math.toRadians(alpha)) * x + Math.cos(Math.toRadians(alpha)) * y;
    }
}
