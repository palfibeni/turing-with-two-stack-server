package com.palfib.turingWithTwoStack.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Date;
import java.util.Set;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(exclude = {"created"})
@JsonDeserialize(builder = TuringMachineDto.TuringMachineDtoBuilder.class)
public class TuringMachineDto {

    private Long id;

    private String name;

    private Set<Character> tapeCharacters;

    private Set<MachineStateDto> states;

    private Set<TuringRuleDto> rules;

    private Date created;

    @JsonPOJOBuilder(withPrefix = "")
    public static class TuringMachineDtoBuilder {
    }

}
