package com.alpha.bank.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateDTO {
    private String id;
    @JsonProperty("nome")
    private String name;
    @JsonProperty("sigla")
    private String uf;
}