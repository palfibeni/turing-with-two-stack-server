package com.palfib.turingWithTwoStack.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @ElementCollection
    @Column(name = "inputCharacters")
    protected Set<Character> inputCharacters;

    @Column(name = "created")
    private Date created;

    public abstract MachineState getStartState();

    public abstract Set<MachineState> getAcceptStates();

    public abstract Set<MachineState> getDeclineStates();

    public abstract Set<? extends Rule> getRules();
}
