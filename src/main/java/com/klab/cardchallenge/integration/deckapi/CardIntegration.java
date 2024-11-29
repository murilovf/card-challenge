package com.klab.cardchallenge.integration.deckapi;

import com.klab.cardchallenge.responses.deckapi.DrawCardResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class CardIntegration {
    @Value("${deck.api.url}")
    private String urlDeckApi;

    private RestTemplate restTemplate = new RestTemplate();

    public DrawCardResponse draw(String deckId, Integer count) {
        String url = UriComponentsBuilder.fromHttpUrl(urlDeckApi)
                .path("/{deckId}/draw/")
                .queryParam("count", count)
                .buildAndExpand(deckId)
                .toUriString();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<DrawCardResponse> responseEntity =
                    restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                            DrawCardResponse.class);

            return responseEntity.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cursos da API: " + e.getMessage());
        }
    }
}
