package com.targa.labs.dev.cqrsesv2.service;

import com.targa.labs.dev.cqrsesv2.entity.BankAccountV2;
import com.targa.labs.dev.cqrsesv2.query.FindBankAccountQueryV2;
import lombok.AllArgsConstructor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.targa.labs.dev.cqrsesv2.service.ServiceUtilsV2.formatUuid;

@Service
@AllArgsConstructor
public class AccountQueryServiceV2 {
    private final QueryGateway queryGateway;
    private final EventStore eventStore;

    public CompletableFuture<BankAccountV2> findById(String accountId) {
        return this.queryGateway.query(
                new FindBankAccountQueryV2(formatUuid(accountId)),
                ResponseTypes.instanceOf(BankAccountV2.class)
        );
    }

    public List<Object> listEventsForAccount(String accountId) {
        return this.eventStore
                .readEvents(formatUuid(accountId).toString())
                .asStream()
                .map(Message::getPayload)
                .collect(Collectors.toList());
    }
}
