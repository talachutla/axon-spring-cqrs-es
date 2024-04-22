package com.targa.labs.dev.cqrses;

import com.targa.labs.dev.cqrses.entity.BankAccount;
import com.targa.labs.dev.cqrses.models.AccountCreationDTO;
import com.targa.labs.dev.cqrses.service.AccountCommandService;
import com.targa.labs.dev.cqrses.service.AccountQueryService;
import com.targa.labs.dev.gateway.rest.AccountCommandController;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@WebMvcTest(AccountCommandController.class)
class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountCommandService accountCommandService;

    @MockBean
    private AccountQueryService accountQueryService;

    @Test
    void testCreateAccount() throws Exception {
        AccountCreationDTO accountRequest = new AccountCreationDTO(BigDecimal.valueOf(100), "SampleUser");
        BankAccount account = new BankAccount(UUID.randomUUID(), "SampleUser", BigDecimal.valueOf(100));
        CompletableFuture<BankAccount> future = CompletableFuture.completedFuture(account);

        when(accountCommandService.createAccount(any(AccountCreationDTO.class))).thenReturn(future);

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"initialBalance\":100,\"owner\":\"SampleUser\"}"))
                .andExpect(status().isCreated());
    }
}
