package com.billing.config;

import com.billing.domain.ChargeExecution;
import com.billing.domain.ChargeSchedule;
import com.billing.domain.enums.ExecutionStatus;
import com.billing.domain.enums.Frequency;
import com.billing.domain.enums.ProductType;
import com.billing.infrastructure.persistence.ChargeExecutionRepository;
import com.billing.infrastructure.persistence.ChargeScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final ChargeScheduleRepository chargeScheduleRepository;
    private final ChargeExecutionRepository chargeExecutionRepository;

    @Override
    public void run(String... args) {
        if (chargeScheduleRepository.count() > 0 || chargeExecutionRepository.count() > 0) {
            return; // evita duplicação
        }

        // ---------- ChargeSchedules ----------
        ChargeSchedule schedule1 = ChargeSchedule.builder()
                .id(UUID.randomUUID())
                .externalReferenceId("sub_123")
                .referenceSource("subscription")
                .productType(ProductType.ASSINATURA)
                .frequency(Frequency.MENSAL)
                .startDate(LocalDate.now().minusMonths(2))
                .endDate(null)
                .totalCycles(null)
                .paused(false)
                .specificDates(null)
                .metadata(Map.of("customerId", "cus_001"))
                .build();

        ChargeSchedule schedule2 = ChargeSchedule.builder()
                .id(UUID.randomUUID())
                .externalReferenceId("bundle_456")
                .referenceSource("bundle")
                .productType(ProductType.BUNDLE)
                .frequency(Frequency.QUINZENAL)
                .startDate(LocalDate.now().minusWeeks(1))
                .endDate(LocalDate.now().plusMonths(3))
                .totalCycles(6)
                .paused(false)
                .specificDates(List.of())
                .metadata(Map.of("bundleName", "pack_digital"))
                .build();

        ChargeSchedule schedule3 = ChargeSchedule.builder()
                .id(UUID.randomUUID())
                .externalReferenceId("order_789")
                .referenceSource("ebook-purchase")
                .productType(ProductType.AVULSO)
                .frequency(Frequency.UNICA)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .totalCycles(1)
                .paused(false)
                .specificDates(List.of(LocalDate.now()))
                .metadata(Map.of("ebookTitle", "Java Essentials"))
                .build();

        chargeScheduleRepository.saveAll(List.of(schedule1, schedule2, schedule3));

        // ---------- ChargeExecutions ----------
        ChargeExecution execution1 = ChargeExecution.builder()
                .id(UUID.randomUUID())
                .chargeScheduleId(schedule1.getId())
                .scheduledDate(LocalDate.now().minusMonths(1))
                .executionTime(LocalDateTime.now().minusMonths(1))
                .status(ExecutionStatus.SUCCESS)
                .gatewayTransactionId("trx_" + UUID.randomUUID())
                .errorDetails(null)
                .build();

        ChargeExecution execution2 = ChargeExecution.builder()
                .id(UUID.randomUUID())
                .chargeScheduleId(schedule2.getId())
                .scheduledDate(LocalDate.now().minusWeeks(1))
                .executionTime(LocalDateTime.now().minusWeeks(1))
                .status(ExecutionStatus.FAILED)
                .gatewayTransactionId(null)
                .errorDetails("Timeout ao tentar cobrar via gateway X")
                .build();

        ChargeExecution execution3 = ChargeExecution.builder()
                .id(UUID.randomUUID())
                .chargeScheduleId(schedule3.getId())
                .scheduledDate(LocalDate.now())
                .executionTime(LocalDateTime.now())
                .status(ExecutionStatus.SUCCESS)
                .gatewayTransactionId("trx_" + UUID.randomUUID())
                .errorDetails(null)
                .build();

        chargeExecutionRepository.saveAll(List.of(execution1, execution2, execution3));

        System.out.println("✅ MongoDB seeded with ChargeSchedules and ChargeExecutions.");
    }
}
