package com.bigbank.game.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GameClientConfig {

  @Bean
  public WebClient gameWebClient(@Value("${game.base.url}") String baseUrl) {
    return WebClient
        .builder()
        .clientConnector(new ReactorClientHttpConnector())
        .baseUrl(baseUrl)
        .defaultHeaders(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_JSON))
        .build();
  }
}
