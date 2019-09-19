package com.palfib.turingWithTwoStack.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class MachineState {

    private Long id;

    private String name;

    private boolean start;

    private boolean accept;

    private boolean decline;

}
