package com.quizer.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class WebClientConfiguration {

    @Value("${api.openTDB.url}")
    private String openTDB;


    @Bean
    public WebClient webClient(final ObjectMapper objectMapper) {
        final var httpClient = HttpClient.create();
        final var exchangeStrategies = ExchangeStrategies
.builder()
                .codecs(configurer -> {
                    configurer
                            .defaultCodecs()
                            .jackson2JsonEncoder(
                                    new Jackson2JsonEncoder(
                                            objectMapper,
                                            MediaType.APPLICATION_JSON));
                    configurer
                            .defaultCodecs()
                            .jackson2JsonDecoder(
                                    new Jackson2JsonDecoder(
                                            objectMapper,
                                            MediaType.APPLICATION_JSON));
                }).build();

        return WebClient.builder()
                .baseUrl(openTDB)
                .exchangeStrategies(exchangeStrategies)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

}
