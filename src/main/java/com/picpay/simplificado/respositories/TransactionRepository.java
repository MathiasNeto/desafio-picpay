package com.picpay.simplificado.respositories;

import com.picpay.simplificado.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
