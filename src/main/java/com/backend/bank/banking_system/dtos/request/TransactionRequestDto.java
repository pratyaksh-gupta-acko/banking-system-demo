package com.backend.bank.banking_system.dtos.request;

import com.backend.bank.banking_system.annotations.TransactionConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TransactionConstraint
public class TransactionRequestDto {
    private int creditTo;
    private int debitFrom;
    @NotNull(message = "Amount field can't be NULL")
//    @Min(value = 0, message = "Transaction amount can't be negative")
    private int amount;
}
