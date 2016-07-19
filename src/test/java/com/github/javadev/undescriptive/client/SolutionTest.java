package com.github.javadev.undescriptive.client;

import ch.qos.logback.classic.Level;
import com.github.javadev.undescriptive.protocol.request.SolutionRequest;
import com.github.javadev.undescriptive.protocol.response.GameResponse;
import com.github.javadev.undescriptive.protocol.response.WeatherResponse;
import com.github.javadev.undescriptive.protocol.response.SolutionResponse;
import org.junit.Test;

import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolutionTest {
    protected final static AsyncClient client = AsyncClient.createDefault();

    @BeforeClass
    public static void beforeClass() {
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME)).setLevel(Level.INFO);
    }

    @Test
    public void testRequest() throws Exception {
        final SolutionRequest request = SolutionRequest.builder()
            .scale(5)
            .claw(5)
            .wing(5)
            .fire(5)
            .build();
        request.getScale();
        request.getClaw();
        request.getWing();
        request.getFire();
    }

    @Test
    public void testSolution() throws Exception {
        int victoryCount = 0;
        for (int gameIndex = 0; gameIndex < 100; gameIndex += 1) {
            final GameResponse game = client.getGame().get();
            game.getGameResponseItem();
            game.getGameResponseItem().getName();
            game.getGameResponseItem().getAttack();
            game.getGameResponseItem().getArmor();
            game.getGameResponseItem().getAgility();
            game.getGameResponseItem().getEndurance();
            System.out.println(game);
            WeatherResponse weatherResponse = client.getWeather(game.getGameId()).get();
            weatherResponse.getTime();
            weatherResponse.getCode();
            weatherResponse.getMessage();
            System.out.println(weatherResponse);
            final SolutionRequest request = client.generateGameSolution(game.getGameResponseItem(), weatherResponse);
            System.out.println(request);
            SolutionResponse response = client.sendSolution(game.getGameId(), request).get();
            response.getStatus();
            response.getMessage();
            if ("Victory".equals(response.getStatus())) {
                victoryCount += 1;
            }
            System.out.println(response);
        }
        System.out.println("victoryCount - " + victoryCount);
    }
}