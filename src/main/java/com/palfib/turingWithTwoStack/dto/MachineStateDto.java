package com.palfib.turingWithTwoStack.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonDeserialize(builder = MachineStateDto.MachineStateDtoBuilder.class)
public class MachineStateDto {

    private Long id;

    private String name;

    private boolean start;

    private boolean accept;

    private boolean decline;


    @JsonPOJOBuilder(withPrefix = "")
    public static class MachineStateDtoBuilder {
    }
}
