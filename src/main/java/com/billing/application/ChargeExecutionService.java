package com.billing.application;

import com.billing.domain.*;
import com.billing.domain.enums.ExecutionStatus;
import com.billing.infrastructure.external.ExternalProductAdapter;
import com.billing.infrastructure.persistence.ChargeExecutionRepository;
import com.billing.infrastructure.persistence.ChargeScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChargeExecutionService {

    private final ChargeScheduleRepository scheduleRepository;
    private final ChargeExecutionRepository executionRepository;
    private final ExternalProductAdapter productAdapter;

    @Scheduled(cron = "0 0 2 * * *") // roda às 2h da manhã todos os dias
    public void executeScheduledCharges() {
        LocalDate today = LocalDate.now();
        List<ChargeSchedule> dueSchedules = scheduleRepository.findDueSchedules(today);

        for (ChargeSchedule schedule : dueSchedules) {
            if (!productAdapter.isEligibleForCharge(schedule.getReferenceSource(), schedule.getExternalReferenceId())) {
                continue;
            }

            ChargeExecution execution = ChargeExecution.builder()
                    .id(UUID.randomUUID())
                    .chargeScheduleId(schedule.getId())
                    .scheduledDate(today)
                    .executionTime(LocalDateTime.now())
                    .status(ExecutionStatus.SUCCESS)
                    .gatewayTransactionId("fake-trx-" + UUID.randomUUID())
                    .build();

            executionRepository.save(execution);
        }
    }
}