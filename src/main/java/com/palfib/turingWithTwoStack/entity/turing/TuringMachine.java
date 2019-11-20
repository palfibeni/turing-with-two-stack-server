package com.palfib.turingWithTwoStack.entity.turing;

import com.palfib.turingWithTwoStack.entity.Machine;
import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackRule;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Entity
@Table(name = "turingMachine")
@NoArgsConstructor
@Data
public class TuringMachine extends Machine {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TuringRule> rules;

    @OneToMany(mappedBy = "turingMachine", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MachineState> states;

    @Builder
    public TuringMachine(final Long id, final String name, final String description, final Set<Character> inputCharacters,
                         final Set<MachineState> states, final Set<TuringRule> rules, final Date created) {
        super(id, inputCharacters, created);
        this.name = name;
        this.description = description;
        this.rules = rules;
        this.states = states;
    }

    public MachineState getStartState() {
        // TODO validálni kéne
        return states.stream().filter(MachineState::isStart).findFirst().orElse(null);
    }

    public Set<MachineState> getAcceptStates() {
        return states.stream().filter(MachineState::isAccept).collect(toSet());
    }

    public Set<MachineState> getDeclineStates() {
        return states.stream().filter(MachineState::isDecline).collect(toSet());
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        val that = (TuringMachine) o;
        if (id != null || that.id != null) {
            return Objects.equals(id, that.id);
        }
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(id, name);
    }
}
