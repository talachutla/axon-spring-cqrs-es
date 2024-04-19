package com.targa.labs.dev.cqrses.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AccountCreationDTO {
    private final BigDecimal initialBalance;
    private final String owner;
}
