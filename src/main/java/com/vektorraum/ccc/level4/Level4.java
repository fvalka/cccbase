package com.vektorraum.ccc.level4;

import com.vektorraum.ccc.base.Level;
import com.vektorraum.ccc.base.LevelRunner;
import com.vektorraum.ccc.level1.Level1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class Level4 extends Level {
    @Override
    public String apply(List<String> strings) {

        RestTemplate restTemplate = new RestTemplate();
        String map = "L4_MFJS3487";

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

        moveRover(restTemplate, base, roverUuid);

        String move2 = restTemplate.getForObject(base + String.format("move/%s?distance=%d&steeringAngle=%d", roverUuid, -100, 0), String.class);
        log.info(move2);


        return "";
    }

    private void moveRover(RestTemplate restTemplate, String base, String roverUuid) {
        String moveUrl = String.format("move/%s?distance=%.2f&steeringAngle=%.2f", roverUuid, 100.0, 0.0).replace(",",".");
        log.info("Move url: {}", moveUrl);
        String move1 = restTemplate.getForObject(base + moveUrl, String.class);
        log.info(move1);
    }

    public static void main(String[] args) throws Exception {
        new Level4().apply(null);
    }
}
