package com.palfib.turingWithTwoStack.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
@EqualsAndHashCode(exclude = {"created"})
@JsonDeserialize(builder = MachineStateDto.MachineStateDtoBuilder.class)
public class MachineStateDto {

    private Long id;

    private String name;

    private boolean start;

    private boolean accept;

    private boolean decline;

    private Date created;


    @JsonPOJOBuilder(withPrefix = "")
    public static class MachineStateDtoBuilder {
    }

    @Override
    public String toString() {
        return name;
    }
}
