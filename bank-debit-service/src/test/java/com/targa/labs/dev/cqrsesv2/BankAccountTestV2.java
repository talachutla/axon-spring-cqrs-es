package com.targa.labs.dev.cqrsesv2;

import com.targa.labs.dev.cqrsesv2.aggregate.BankAccountAggregateV2;
import com.targa.labs.dev.cqrsesv2.command.CreateAccountCommandV2;
import com.targa.labs.dev.cqrsesv2.command.CreditMoneyCommandV2;
import com.targa.labs.dev.cqrsesv2.command.DebitMoneyCommandV2;
import com.targa.labs.dev.cqrsesv2.event.AccountCreatedEventV2;
import com.targa.labs.dev.cqrsesv2.event.MoneyCreditedEventV2;
import com.targa.labs.dev.cqrsesv2.event.MoneyDebitedEventV2;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class BankAccountTestV2 {
    private static final String customerName = "Nebrass";

    private FixtureConfiguration<BankAccountAggregateV2> fixture;
    private UUID id;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(BankAccountAggregateV2.class);
        id = UUID.randomUUID();
    }

    @Test
    public void should_dispatch_accountcreated_event_when_createaccount_command() {
        fixture.givenNoPriorActivity()
                .when(new CreateAccountCommandV2(
                        id,
                        BigDecimal.valueOf(1000),
                        customerName)
                )
                .expectEvents(new AccountCreatedEventV2(
                        id,
                        BigDecimal.valueOf(1000),
                        customerName)
                );
    }

    @Test
    public void should_dispatch_moneycredited_event_when_balance_is_lower_than_debit_amount() {
        fixture.given(new AccountCreatedEventV2(
                        id,
                        BigDecimal.valueOf(1000),
                        customerName))
                .when(new CreditMoneyCommandV2(
                        id,
                        BigDecimal.valueOf(100))
                )
                .expectEvents(new MoneyCreditedEventV2(
                        id,
                        BigDecimal.valueOf(100))
                );
    }

    @Test
    public void should_dispatch_moneydebited_event_when_balance_is_upper_than_debit_amount() {
        fixture.given(new AccountCreatedEventV2(
                        id,
                        BigDecimal.valueOf(1000),
                        customerName))
                .when(new DebitMoneyCommandV2(
                        id,
                        BigDecimal.valueOf(100)))
                .expectEvents(new MoneyDebitedEventV2(
                        id,
                        BigDecimal.valueOf(100)));
    }

    @Test
    public void should_not_dispatch_event_when_balance_is_lower_than_debit_amount() {
        fixture.given(new AccountCreatedEventV2(
                        id,
                        BigDecimal.valueOf(1000),
                        customerName))
                .when(new DebitMoneyCommandV2(
                        id,
                        BigDecimal.valueOf(5000)))
                .expectNoEvents();
    }
}
