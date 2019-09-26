package com.palfib.turingWithTwoStack.repository;

import com.palfib.turingWithTwoStack.entity.turing.TuringMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TuringMachineRepository extends JpaRepository<TuringMachine, Long>, QueryByExampleExecutor<TuringMachine> {


}
