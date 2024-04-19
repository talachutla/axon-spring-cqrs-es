package com.targa.labs.dev.cqrsesv2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankAccountV2 {
    @Id
    private UUID id;
    private String owner;
    private BigDecimal balance;
}
