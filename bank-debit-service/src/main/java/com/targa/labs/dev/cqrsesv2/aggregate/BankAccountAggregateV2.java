package com.targa.labs.dev.cqrsesv2.aggregate;

import com.targa.labs.dev.cqrsesv2.command.CreateAccountCommandV2;
import com.targa.labs.dev.cqrsesv2.command.CreditMoneyCommandV2;
import com.targa.labs.dev.cqrsesv2.command.DebitMoneyCommandV2;
import com.targa.labs.dev.cqrsesv2.event.AccountCreatedEventV2;
import com.targa.labs.dev.cqrsesv2.event.MoneyCreditedEventV2;
import com.targa.labs.dev.cqrsesv2.event.MoneyDebitedEventV2;
import com.targa.labs.dev.cqrsesv2.exception.InsufficientBalanceExceptionV2;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Aggregate
public class BankAccountAggregateV2 {

    @AggregateIdentifier
    private UUID id;
    private BigDecimal balance;
    private String owner;

    @CommandHandler
    public BankAccountAggregateV2(CreateAccountCommandV2 command) {

        AggregateLifecycle.apply(
                new AccountCreatedEventV2(
                        command.getAccountId(),
                        command.getInitialBalance(),
                        command.getOwner()
                )
        );
    }

    @EventSourcingHandler
    public void on(AccountCreatedEventV2 event) {
        this.id = event.getId();
        this.owner = event.getOwner();
        this.balance = event.getInitialBalance();
    }

    @CommandHandler
    public void handle(CreditMoneyCommandV2 command) {
        AggregateLifecycle.apply(
                new MoneyCreditedEventV2(
                        command.getAccountId(),
                        command.getCreditAmount()
                )
        );
    }

    @EventSourcingHandler
    public void on(MoneyCreditedEventV2 event) {
        this.balance = this.balance.add(event.getCreditAmount());
    }

    @CommandHandler
    public void handle(DebitMoneyCommandV2 command) {
        AggregateLifecycle.apply(
                new MoneyDebitedEventV2(
                        command.getAccountId(),
                        command.getDebitAmount()
                )
        );
    }

    @EventSourcingHandler
    public void on(MoneyDebitedEventV2 event) throws InsufficientBalanceExceptionV2 {
        if (this.balance.compareTo(event.getDebitAmount()) < 0) {
            throw new InsufficientBalanceExceptionV2(event.getId(), event.getDebitAmount());
        }
        this.balance = this.balance.subtract(event.getDebitAmount());
    }
}
