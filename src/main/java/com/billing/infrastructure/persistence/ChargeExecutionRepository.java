package com.billing.infrastructure.persistence;

import com.billing.domain.ChargeExecution;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ChargeExecutionRepository extends MongoRepository<ChargeExecution, UUID> {
}
