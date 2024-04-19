package com.targa.labs.dev.cqrsesv2.exception;

import java.util.UUID;

public class AccountNotFoundExceptionV2 extends Throwable {
    public AccountNotFoundExceptionV2(UUID id) {
        super("Cannot found account number [" + id + "]");
    }
}
