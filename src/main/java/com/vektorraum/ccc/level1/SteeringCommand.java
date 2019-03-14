package com.vektorraum.ccc.level1;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SteeringCommand {
    double distance;
    double steeringAngle;
}
