package com.billing.domain;

import com.billing.domain.enums.ExecutionStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "charge_executions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargeExecution {

    @Id
    private UUID id;

    @Field("chargeScheduleId")
    private UUID chargeScheduleId;

    @Field("scheduledDate")
    private LocalDate scheduledDate;

    @Field("executionTime")
    private LocalDateTime executionTime;

    @Field("status")
    private ExecutionStatus status;

    @Field("gatewayTransactionId")
    private String gatewayTransactionId;

    @Field("errorDetails")
    private String errorDetails;

    public boolean isRetryable() {
        return status == ExecutionStatus.FAILED;
    }

    public boolean isSuccess() {
        return status == ExecutionStatus.SUCCESS;
    }
}