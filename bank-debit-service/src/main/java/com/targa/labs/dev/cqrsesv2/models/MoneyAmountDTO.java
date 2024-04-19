package com.targa.labs.dev.cqrsesv2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyAmountDTO {
    private BigDecimal amount;
}
