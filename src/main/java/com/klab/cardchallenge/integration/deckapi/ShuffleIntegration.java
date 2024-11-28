package com.klab.cardchallenge.integration.deckapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.klab.cardchallenge.responses.NewDeckResponse;

@Component
public class ShuffleIntegration {
    @Value("${deck.api.url}")
    private String urlDeckApi;

    private RestTemplate restTemplate = new RestTemplate();

    public NewDeckResponse newDeck(Integer deckCount) {
        String url = UriComponentsBuilder.fromHttpUrl(urlDeckApi + "/new/shuffle")
                .queryParam("deck_count", deckCount)
                .toUriString();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<NewDeckResponse> responseEntity =
                    restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                            NewDeckResponse.class);

            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Erro ao buscar cursos da API: " + e.getMessage());
        }
    }
}
