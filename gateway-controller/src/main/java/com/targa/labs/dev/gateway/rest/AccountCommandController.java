package com.targa.labs.dev.gateway.rest;

import com.targa.labs.dev.cqrses.entity.BankAccount;
import com.targa.labs.dev.cqrses.models.AccountCreationDTO;
import com.targa.labs.dev.cqrses.models.MoneyAmountDTO;
import com.targa.labs.dev.cqrses.service.AccountCommandService;
import com.targa.labs.dev.cqrsesv2.service.AccountCommandServiceV2;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/accounts")
@Api(value = "Bank Account Commands", description = "Bank Account Commands API")
//@AllArgsConstructor
public class AccountCommandController {

    private final AccountCommandService accountCommandService;
    private final AccountCommandServiceV2 accountCommandServiceV2;

    @Autowired
    public AccountCommandController(AccountCommandService accountCommandService, AccountCommandServiceV2 accountCommandServiceV2) {
        this.accountCommandService = accountCommandService;
        this.accountCommandServiceV2 = accountCommandServiceV2;
    }

    @PostMapping
    @ResponseStatus(value = CREATED)
    public CompletableFuture<BankAccount> createAccount(@RequestBody AccountCreationDTO creationDTO) {
        return this.accountCommandService.createAccount(creationDTO);
    }

    @PutMapping(value = "/credit/{accountId}")
    public CompletableFuture<String> creditMoneyToAccount(@PathVariable(value = "accountId") String accountId,
                                                          @RequestBody MoneyAmountDTO moneyCreditDTO) {
        return this.accountCommandService.creditMoneyToAccount(accountId, moneyCreditDTO);
    }

    @PutMapping(value = "/debit/{accountId}")
    public CompletableFuture<String> debitMoneyFromAccount(@PathVariable(value = "accountId") String accountId,
                                                           @RequestBody MoneyAmountDTO moneyDebitDTO) {
        return this.accountCommandService.debitMoneyFromAccount(accountId, moneyDebitDTO);
    }
}
