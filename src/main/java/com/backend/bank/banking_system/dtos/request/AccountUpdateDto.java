package com.backend.bank.banking_system.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountUpdateDto {
    @NotNull(message = "Name field can't be NULL")
    private String name;
}
