package com.palfib.turingWithTwoStack.repository;

import com.palfib.turingWithTwoStack.entity.turing.TuringRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TuringRuleRepository extends JpaRepository<TuringRule, Long> {
}
