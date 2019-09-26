package com.palfib.turingWithTwoStack.entity.turing;

import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.Rule;
import com.palfib.turingWithTwoStack.entity.enums.Direction;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackMachine;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "turingRule")
@Data
@NoArgsConstructor
public class TuringRule extends Rule {

    @Column(name = "writeCharacter")
    private Character writeCharacter;

    @Column(name = "direction")
    private Direction direction;

    @ManyToOne
    @JoinColumn(name = "turingMachine")
    @ToString.Exclude
    private TuringMachine machine;

    @Builder
    public TuringRule(final Long id, final MachineState fromState, final Character readCharacter,
                      final MachineState toState, final Character writeCharacter, final Direction direction,
                      final TuringMachine machine) {
        super(id, fromState, readCharacter, toState);
        this.writeCharacter = writeCharacter;
        this.direction = direction;
        this.machine = machine;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        val that = (TuringRule) o;
        return writeCharacter.equals(that.writeCharacter) && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(writeCharacter, direction);
    }
}
