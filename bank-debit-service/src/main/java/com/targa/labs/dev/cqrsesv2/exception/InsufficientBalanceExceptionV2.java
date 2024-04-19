package com.targa.labs.dev.cqrsesv2.exception;

import java.math.BigDecimal;
import java.util.UUID;

public class InsufficientBalanceExceptionV2 extends Throwable {
    public InsufficientBalanceExceptionV2(UUID accountId, BigDecimal debitAmount) {
        super("Insufficient Balance: Cannot debit " + debitAmount +
                " from account number [" + accountId.toString() + "]");
    }
}
