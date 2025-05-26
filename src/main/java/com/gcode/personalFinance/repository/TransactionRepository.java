package com.gcode.personalFinance.repository;

import com.gcode.personalFinance.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
