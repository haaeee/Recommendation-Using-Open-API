package com.example.recommendationservice.domain.api.dto;

import static lombok.AccessLevel.PROTECTED;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter @ToString
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(staticName = "of")
public class MetaDto {

    @JsonProperty("total_count")
    private Integer totalCount;
}
