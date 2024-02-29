package com.picpay.simplificado.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private LocalDateTime transactionMoment;
    @ManyToOne
    @JoinColumn(name = "received_id")
    private User received;
    @ManyToOne
    @JoinColumn(name="sender_id")
    private User sender;

}