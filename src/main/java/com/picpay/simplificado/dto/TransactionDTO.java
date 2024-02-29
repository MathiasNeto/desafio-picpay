package com.picpay.simplificado.dto;

import com.picpay.simplificado.entities.Transaction;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class TransactionDTO {
    @DecimalMin(value = "1", message = "Forneça um valor maior ou igual a 1")
    private BigDecimal amount;
    @NotBlank
    private long received_id;
    @NotBlank
    private long sender_id;

    public TransactionDTO(Transaction transaction){
        this.amount = transaction.getAmount();
        this.received_id = transaction.getReceived().getId();
        this.sender_id = transaction.getSender().getId();
    }
}
