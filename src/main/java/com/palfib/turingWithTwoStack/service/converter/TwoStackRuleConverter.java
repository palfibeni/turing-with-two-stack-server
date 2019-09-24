package com.palfib.turingWithTwoStack.service.converter;

import com.palfib.turingWithTwoStack.entity.enums.Direction;
import com.palfib.turingWithTwoStack.entity.turing.TuringRule;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackRule;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Component
public class TwoStackRuleConverter {

    public Set<TwoStackRule> fromTuringRules(final Set<TuringRule> turingRules) {
        return turingRules.stream().map(this::fromTuringRule).collect(toSet());
    }

    private TwoStackRule fromTuringRule(final TuringRule turingRule) {
        final boolean isForward = Direction.FORWARD.equals(turingRule.getDirection());
        return TwoStackRule.builder()
                .fromState(turingRule.getFromState())
                .readCharacter(turingRule.getReadCharacter())
                .toState(turingRule.getToState())
                .writeLeft(isForward ? turingRule.getWriteCharacter().toString() : "")
                .writeRight(isForward ? "" : turingRule.getWriteCharacter().toString())
                .copyLeftToRight(Direction.BACKWARD.equals(turingRule.getDirection()))
                .build();
    }
}
