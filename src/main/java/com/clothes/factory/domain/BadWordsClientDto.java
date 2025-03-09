package com.clothes.factory.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BadWordsClientDto {

    @JsonProperty("bad_words_total")
    private int badWordsTotal;

}
