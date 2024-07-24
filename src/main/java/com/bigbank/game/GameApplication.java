package com.bigbank.game;

import com.bigbank.game.processor.GameProcessor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class GameApplication {

  public static void main(String[] args) {
    SpringApplication.run(GameApplication.class, args);
  }

  @Component
  @RequiredArgsConstructor
  public static class GameStarter implements ApplicationListener<ContextRefreshedEvent> {

    private final GameProcessor processor;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
      ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
      scheduler.schedule(this::startGame, 3, TimeUnit.SECONDS);
    }

    private void startGame() {
      processor.processGame();
    }
  }
}
