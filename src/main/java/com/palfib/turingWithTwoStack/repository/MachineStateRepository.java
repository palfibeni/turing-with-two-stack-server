package com.palfib.turingWithTwoStack.repository;

import com.palfib.turingWithTwoStack.entity.MachineState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineStateRepository extends JpaRepository<MachineState, Long> {
}
