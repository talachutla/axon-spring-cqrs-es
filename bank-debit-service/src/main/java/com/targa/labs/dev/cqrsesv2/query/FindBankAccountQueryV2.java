package com.targa.labs.dev.cqrsesv2.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindBankAccountQueryV2 {
    private UUID accountId;
}
