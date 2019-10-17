package com.palfib.turingWithTwoStack.entity;

import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "machineState")
@Data
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MachineState {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start")
    private boolean start;

    @Column(name = "accept")
    private boolean accept;

    @Column(name = "decline")
    private boolean decline;

    /**
     * Only used for foreign key match
     */
    @ManyToOne
    @JoinColumn(name = "turingMachine", referencedColumnName="id")
    @ToString.Exclude
    private TuringMachine turingMachine;

    @Column(name = "created")
    private Date created;

}
