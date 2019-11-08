package com.palfib.turingWithTwoStack.service.converter;

import com.google.common.collect.Lists;
import com.palfib.turingWithTwoStack.entity.MachineState;
import com.palfib.turingWithTwoStack.entity.enums.Direction;
import com.palfib.turingWithTwoStack.entity.turing.TuringRule;
import com.palfib.turingWithTwoStack.entity.twoStack.TwoStackRule;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static com.palfib.turingWithTwoStack.entity.Condition.EMPTY;
import static com.palfib.turingWithTwoStack.entity.twoStack.TwoStackCondition.*;
import static java.util.stream.Collectors.toSet;

@Component
public class TwoStackRuleConverter {

    public Set<TwoStackRule> fromTuringRules(final Set<TuringRule> turingRules) {
        return turingRules.stream().map(this::fromTuringRule).flatMap(List::stream).collect(toSet());
    }

    private List<TwoStackRule> fromTuringRule(final TuringRule turingRule) {
        return Lists.newArrayList(
                fromTuringRule(turingRule, true),
                fromTuringRule(turingRule, false));
    }

    private TwoStackRule fromTuringRule(final TuringRule turingRule, final boolean initialRead) {
        final boolean isForward = Direction.FORWARD.equals(turingRule.getDirection());
        return TwoStackRule.builder()
                .fromState(turingRule.getFromState())
                .readCharacter(EMPTY)
                .readLeft(initialRead ? STACK_BOTTOM : JOKER)
                .readRight(turingRule.getReadCharacter())
                .toState(turingRule.getToState())
                .writeLeft((initialRead ? STACK_BOTTOM_AS_STRING : JOKER_AS_STRING) + (isForward ? turingRule.getWriteCharacter().toString() : ""))
                .writeRight(isForward ? "" : turingRule.getWriteCharacter().toString())
                .copyLeftToRight(Direction.BACKWARD.equals(turingRule.getDirection()))
                .build();
    }

    public Set<TwoStackRule> fromTuringTapeCharachters(final MachineState startRead, final MachineState copyToRight,
                                                       final MachineState turingStart, Set<Character> tapeCharacters) {
        val rules = tapeCharacters.stream()
                .map(character -> createRulesFromTapeCharacter(startRead, copyToRight, character))
                .flatMap(List::stream)
                .collect(toSet());
        val startCopyToLeft = TwoStackRule.builder()
                .fromState(startRead)
                .readCharacter(EMPTY)
                .readLeft(JOKER)
                .readRight(STACK_BOTTOM)
                .toState(copyToRight)
                .writeLeft(JOKER_AS_STRING)
                .writeRight(STACK_BOTTOM_AS_STRING)
                .copyLeftToRight(false)
                .build();
        rules.add(startCopyToLeft);
        val startTuringRun = TwoStackRule.builder()
                .fromState(copyToRight)
                .readCharacter(EMPTY)
                .readLeft(STACK_BOTTOM)
                .readRight(JOKER)
                .toState(turingStart)
                .writeLeft(STACK_BOTTOM_AS_STRING)
                .writeRight(JOKER_AS_STRING)
                .copyLeftToRight(false)
                .build();
        rules.add(startTuringRun);
        return rules;
    }

    private List<TwoStackRule> createRulesFromTapeCharacter(final MachineState startRead,
                                                            final MachineState copyToRight,
                                                            final Character tapeCharacter) {
        return Lists.newArrayList(
                readInputToLeft(startRead, tapeCharacter, true),
                readInputToLeft(startRead, tapeCharacter, false),
                copyFromLeftToRight(copyToRight, tapeCharacter, true),
                copyFromLeftToRight(copyToRight, tapeCharacter, false)
        );
    }

    private TwoStackRule readInputToLeft(final MachineState state, final Character tapeCharacter, final boolean initialRead) {
        return TwoStackRule.builder()
                .fromState(state)
                .readCharacter(tapeCharacter)
                .readLeft(initialRead ? STACK_BOTTOM : JOKER)
                .readRight(STACK_BOTTOM)
                .toState(state)
                .writeLeft((initialRead ? STACK_BOTTOM_AS_STRING : JOKER_AS_STRING) + tapeCharacter.toString())
                .writeRight(STACK_BOTTOM_AS_STRING)
                .copyLeftToRight(false)
                .build();
    }

    private TwoStackRule copyFromLeftToRight(final MachineState state, final Character tapeCharacter, final boolean initialRead) {
        return TwoStackRule.builder()
                .fromState(state)
                .readCharacter(EMPTY)
                .readLeft(tapeCharacter)
                .readRight(initialRead ? STACK_BOTTOM : JOKER)
                .toState(state)
                .writeLeft("")
                .writeRight((initialRead ? STACK_BOTTOM_AS_STRING : JOKER_AS_STRING) + tapeCharacter.toString())
                .copyLeftToRight(false)
                .build();
    }
}
