package com.targa.labs.dev.cqrsesv2.projection;

import com.targa.labs.dev.cqrsesv2.entity.BankAccountV2;
import com.targa.labs.dev.cqrsesv2.event.AccountCreatedEventV2;
import com.targa.labs.dev.cqrsesv2.event.MoneyCreditedEventV2;
import com.targa.labs.dev.cqrsesv2.event.MoneyDebitedEventV2;
import com.targa.labs.dev.cqrsesv2.exception.AccountNotFoundExceptionV2;
import com.targa.labs.dev.cqrsesv2.query.FindBankAccountQueryV2;
import com.targa.labs.dev.cqrsesv2.repository.BankAccountRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
//@RequiredArgsConstructor
@Component
public class BankAccountProjectionV2 {

    private final BankAccountRepositoryV2 repository;
//    private final QueryUpdateEmitter updateEmitter;
    @Autowired // or use constructor injection with @Autowired (preferred)
    public BankAccountProjectionV2(BankAccountRepositoryV2 bankAccountRepositoryv2) {
        this.repository = bankAccountRepositoryv2;
    }

    @EventHandler
    public void on(AccountCreatedEventV2 event) {
        log.debug("Handling a Bank Account creation command {}", event.getId());
        BankAccountV2 bankAccount = new BankAccountV2(
                event.getId(),
                event.getOwner(),
                event.getInitialBalance()
        );
        this.repository.save(bankAccount);
    }

    @EventHandler
    public void on(MoneyCreditedEventV2 event) throws AccountNotFoundExceptionV2 {
        log.info("Handling a V2 Bank Account Credit command {}", event.getId());
        Optional<BankAccountV2> optionalBankAccount = this.repository.findById(event.getId());
        if (optionalBankAccount.isPresent()) {
            BankAccountV2 bankAccount = optionalBankAccount.get();
            bankAccount.setBalance(bankAccount.getBalance().add(event.getCreditAmount()));
            this.repository.save(bankAccount);
        } else {
            throw new AccountNotFoundExceptionV2(event.getId());
        }
    }

    @EventHandler
    public void on(MoneyDebitedEventV2 event) throws AccountNotFoundExceptionV2 {
        log.debug("Handling a Bank Account Debit command {}", event.getId());
        Optional<BankAccountV2> optionalBankAccount = this.repository.findById(event.getId());
        if (optionalBankAccount.isPresent()) {
            BankAccountV2 bankAccount = optionalBankAccount.get();
            bankAccount.setBalance(bankAccount.getBalance().subtract(event.getDebitAmount()));
            this.repository.save(bankAccount);
        } else {
            throw new AccountNotFoundExceptionV2(event.getId());
        }
    }

    @QueryHandler
    public BankAccountV2 handle(FindBankAccountQueryV2 query) {
        log.debug("Handling FindBankAccountQuery query: {}", query);
        return this.repository.findById(query.getAccountId()).orElse(null);
    }
}
