package com.vektorraum.ccc.level5;

import com.vektorraum.ccc.base.Level;
import com.vektorraum.ccc.level1.Position;
import com.vektorraum.ccc.level1.Rover;
import com.vektorraum.ccc.level1.SteeringCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class Level5 extends Level {
    @Override
    public String apply(List<String> strings) {
        log.info("Running level5");

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
        double targetPos2Norm = Math.sqrt(Math.pow(targetX, 2.0) + Math.pow(targetY, 2.0));
        double originalAngleToTarget = Math.atan(targetY / targetX);
        double distanceToTarget = Math.sqrt(Math.pow(targetX, 2.0) + Math.pow(targetY, 2.0));
        double distance = distanceToTarget;

        double distanceLearningRate = 0.0001;
        double angleLearningRate = 0.0001;

        Rover rover = new Rover(wheelBase);

        double bestDistanceToTarget = Double.MAX_VALUE;
        SteeringCommand bestSteer = null;
        Position bestPos = null;
        for (double steer = 0.0; steer < maxSteeringAngle; steer += maxSteeringAngle / 80.0) {
            for (double dist = 0.0; dist < 100.0; dist += targetPos2Norm / 20.0) {
                rover.reset();
                SteeringCommand currentSteer = new SteeringCommand(dist, steer);
                rover.steer(currentSteer);

                double currentDistanceToTarget = Math.sqrt(Math.pow(targetX - rover.getPosition().getX(), 2.0) + Math.pow(targetY - rover.getPosition().getY(), 2.0));

                if (currentDistanceToTarget < bestDistanceToTarget) {
                    bestSteer = currentSteer;
                    bestDistanceToTarget = currentDistanceToTarget;
                    bestPos = rover.getPosition();
                }

            }
        }
        log.info("Best position: {}", bestPos);
        log.info("Distance to target: {}", Math.sqrt(Math.pow(targetX - bestPos.getX(), 2.0) + Math.pow(targetY - bestPos.getY(), 2.0)));

        log.info("Rover pos after learning: {}", rover.getPosition());
        log.info("Best steer: {}", bestSteer);

        moveRover(restTemplate, base, roverUuid, bestSteer.getDistance(), bestSteer.getSteeringAngle());

        /*Position bestPos2 = null;
        for (double steer = 0.0; steer < maxSteeringAngle; steer += maxSteeringAngle / 80.0) {
            for (double dist = 0.0; dist < 100.0; dist += targetPos2Norm / 20.0) {
                rover.setPosition(bestPos);
                SteeringCommand currentSteer = new SteeringCommand(dist, steer);
                rover.steer(currentSteer);

                double currentDistanceToTarget = Math.sqrt(Math.pow(targetX - rover.getPosition().getX(), 2.0) + Math.pow(targetY - rover.getPosition().getY(), 2.0));

                if (currentDistanceToTarget < bestDistanceToTarget) {
                    bestSteer = currentSteer;
                    bestDistanceToTarget = currentDistanceToTarget;
                    bestPos2 = rover.getPosition();
                }

            }
        }
        log.info("Best position: {}", bestPos);
        log.info("Distance to target: {}", Math.sqrt(Math.pow(targetX - bestPos.getX(), 2.0) + Math.pow(targetY - bestPos.getY(), 2.0)));

        log.info("Rover pos after learning: {}", rover.getPosition());
        log.info("Best steer: {}", bestSteer);

        moveRover(restTemplate, base, roverUuid, bestSteer.getDistance(), bestSteer.getSteeringAngle());*/

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
