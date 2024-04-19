package com.targa.labs.dev.cqrsesv2.event;

import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class MoneyDebitedEventV2 {

    private final UUID id;
    private final BigDecimal debitAmount;
}
