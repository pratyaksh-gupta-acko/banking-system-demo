package com.backend.bank.banking_system.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountRequestDto {
    @NotNull(message = "Name Required")
    private String name;
    @NotNull(message = "Starting balance Required")
    @Min(value = 1, message = "Negative balance not allowed")
    private Integer balance;
}
