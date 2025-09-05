package com.HTTPHeaders.Practise.Repository;

import com.HTTPHeaders.Practise.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}