package com.klab.cardchallenge.integration.deckapi;

import com.klab.cardchallenge.responses.deckapi.PileAddResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Component
public class PileIntegration {
    @Value("${deck.api.url}")
    private String urlDeckApi;

    private RestTemplate restTemplate = new RestTemplate();

    public PileAddResponse pileAdd(String deckId, String namePlayer, List<String> listCards) {
        String cardsJoin = String.join(",", listCards);

        String url = UriComponentsBuilder.fromHttpUrl(urlDeckApi)
                .path("/{deckId}/pile/{namePlayer}/add/")
                .queryParam("cards", cardsJoin)
                .buildAndExpand(deckId, namePlayer)
                .toUriString();

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<PileAddResponse> responseEntity =
                    restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                            PileAddResponse.class);

            return responseEntity.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cursos da API: " + e.getMessage());
        }
    }
}
