package com.kodilla.ClothesFactoryBackend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BadWordsClientDto {

    @JsonProperty("bad_words_total")
    private int badWordsTotal;
}
