package com.vektorraum.ccc.level5;

import com.vektorraum.ccc.base.Level;
import com.vektorraum.ccc.level1.Rover;
import com.vektorraum.ccc.level1.SteeringCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class Level5 extends Level {
    @Override
    public String apply(List<String> strings) {

        RestTemplate restTemplate = new RestTemplate();
        String map = "L5_MAF3401R";

        String base = "https://rover.catalysts.cc/rover/";
        String roverUuid = restTemplate.getForObject(
                String.format(base + "create?map=%s&username=fvalka&contestId=practice", map), String.class);
        log.info("Rover UUID: {}", roverUuid);

        String roverSpec = restTemplate.getForObject(base + roverUuid, String.class);
        String[] splitRoverSpec = roverSpec.split(" ");
        double wheelBase = Double.parseDouble(splitRoverSpec[0]);
        double maxSteeringAngle = Double.parseDouble(splitRoverSpec[1]);
        double targetX = Double.parseDouble(splitRoverSpec[2]);
        double targetY = Double.parseDouble(splitRoverSpec[3]);
        double targetRadius = Double.parseDouble(splitRoverSpec[4]);

        log.info("Rover spec: wheelBase {} maxSteering {} targetX {} targetY {} r {}", wheelBase, maxSteeringAngle,
                targetX, targetY, targetRadius);

/*        double radius = Math.sqrt(Math.pow(targetX, 2.0) + Math.pow(targetY, 2.0));
        //double radius = Math.pow(targetY, 2.0) / (8.0 * targetX) + targetX / 2.0;
        log.info("Calculated radius: {}", radius);
        double steerAngle = Math.toDegrees(Math.asin(wheelBase / radius));
        double alpha = Math.toDegrees(Math.asin(targetY / radius));
        double distance = radius * Math.PI * alpha / 180;*/

        double steerAngle = 5.0;
        double targetPos1Norm = targetX + targetY;
        double originalAngleToTarget = Math.atan(targetY / targetX);
        double distanceToTarget = Math.sqrt(Math.pow(targetX, 2.0) + Math.pow(targetY, 2.0));
        double distance = distanceToTarget;

        double distanceLearningRate = 0.0001;
        double angleLearningRate = 0.0001;

        Rover rover = new Rover(wheelBase);

        for (int i = 0; i < 10000; i++) {
            boolean distanceHit = false;
            boolean posHit = false;

            rover.reset();
            rover.steer(new SteeringCommand(distance, steerAngle));

            double currentPos1Norm = rover.getPosition().getX() + rover.getPosition().getY();
            if (currentPos1Norm > targetPos1Norm + targetRadius / 2.0) {
                distance = distance - (currentPos1Norm - targetPos1Norm) * distanceLearningRate;
            } else if (currentPos1Norm < targetPos1Norm - targetRadius / 2.0) {
                distance = distance + (currentPos1Norm - targetPos1Norm) * distanceLearningRate;
            } else {
                break;
            }

            double currentAngleToTarget = Math.atan((targetY - rover.getPosition().getY()) / (targetX - rover.getPosition().getX()));
            steerAngle = steerAngle - (originalAngleToTarget - Math.toDegrees(currentAngleToTarget)) * angleLearningRate/currentPos1Norm;
        }

        log.info("Rover pos after learning: {}", rover.getPosition());

        //moveRover(restTemplate, base, roverUuid, distance, steerAngle);
        return "";
    }

    private void moveRover(RestTemplate restTemplate, String base, String roverUuid, double distance, double steeringAngle) {
        String moveUrl = String.format("move/%s?distance=%.2f&steeringAngle=%.2f", roverUuid, distance, steeringAngle).replace(",",".");
        log.info("Move url: {}", moveUrl);
        String move1 = restTemplate.getForObject(base + moveUrl, String.class);
        log.info(move1);
    }

    public static void main(String[] args) throws Exception {
        new Level5().apply(null);
    }
}
