package com.palfib.turingWithTwoStack.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public abstract class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fromState")
    private MachineState fromState;

    @Column(name = "readCharacter")
    private Character readCharacter;

    @ManyToOne
    @JoinColumn(name = "toState")
    private MachineState toState;

    @Column(name = "created")
    private Date created;

}
