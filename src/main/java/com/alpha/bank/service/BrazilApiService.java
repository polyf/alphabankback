package com.alpha.bank.service;

import com.alpha.bank.model.dto.CityDTO;
import com.alpha.bank.model.dto.StateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class BrazilApiService {

    private static final String BRAZIL_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades";

    private final RestTemplate restTemplate;

    @Autowired
    public BrazilApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<StateDTO> getStates() {
        ResponseEntity<List<StateDTO>> responseEntity = restTemplate.exchange(
                BRAZIL_API_URL + "/estados",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StateDTO>>() {
                });

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            // Handle error, e.g., log or throw an exception
            return Collections.emptyList(); // or null, depending on your needs
        }
    }

    public List<CityDTO> getCitiesByState(String uf) {
        ResponseEntity<List<CityDTO>> responseEntity = restTemplate.exchange(
                BRAZIL_API_URL + "/estados/" + uf + "/municipios/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            return Collections.emptyList(); // or null, depending on your needs
        }
    }

    public CityDTO getCityById(String cityId) {
        return restTemplate.getForObject(BRAZIL_API_URL + "/municipios/" + cityId, CityDTO.class);
    }
}