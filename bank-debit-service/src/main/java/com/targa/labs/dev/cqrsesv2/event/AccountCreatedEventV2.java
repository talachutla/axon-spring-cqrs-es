package com.targa.labs.dev.cqrsesv2.event;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AccountCreatedEventV2 {

    private final UUID id;
    private final BigDecimal initialBalance;
    private final String owner;
}
