package com.targa.labs.dev.cqrsesv2.repository;

import com.targa.labs.dev.cqrsesv2.entity.BankAccountV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BankAccountRepositoryV2 extends JpaRepository<BankAccountV2, UUID> {
}
