package com.targa.labs.dev.cqrsesv2.service;

import com.targa.labs.dev.cqrsesv2.command.CreateAccountCommandV2;
import com.targa.labs.dev.cqrsesv2.command.CreditMoneyCommandV2;
import com.targa.labs.dev.cqrsesv2.command.DebitMoneyCommandV2;
import com.targa.labs.dev.cqrsesv2.entity.BankAccountV2;
import com.targa.labs.dev.cqrsesv2.models.AccountCreationDTO;
import com.targa.labs.dev.cqrsesv2.models.MoneyAmountDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.targa.labs.dev.cqrsesv2.service.ServiceUtilsV2.formatUuid;

@Service
@AllArgsConstructor
public class AccountCommandServiceV2 {
    private final CommandGateway commandGateway;

    public CompletableFuture<BankAccountV2> createAccount(AccountCreationDTO creationDTO) {
        return this.commandGateway.send(new CreateAccountCommandV2(
                UUID.randomUUID(),
                creationDTO.getInitialBalance(),
                creationDTO.getOwner()
        ));
    }

    public CompletableFuture<String> creditMoneyToAccount(String accountId,
                                                          MoneyAmountDTO moneyCreditDTO) {
        return this.commandGateway.send(new CreditMoneyCommandV2(
                formatUuid(accountId),
                moneyCreditDTO.getAmount()
        ));
    }

    public CompletableFuture<String> debitMoneyFromAccount(String accountId,
                                                           MoneyAmountDTO moneyDebitDTO) {
        return this.commandGateway.send(new DebitMoneyCommandV2(
                formatUuid(accountId),
                moneyDebitDTO.getAmount()
        ));
    }
}
