package com.billing.domain;

import com.billing.domain.enums.Frequency;
import com.billing.domain.enums.ProductType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Document(collection = "charge_schedules")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargeSchedule {

    @Id
    private UUID id;

    @Field("externalReferenceId")
    private String externalReferenceId;

    @Field("referenceSource")
    private String referenceSource;

    @Field("productType")
    private ProductType productType;

    @Field("frequency")
    private Frequency frequency;

    @Field("startDate")
    private LocalDate startDate;

    @Field("endDate")
    private LocalDate endDate;

    @Field("totalCycles")
    private Integer totalCycles;

    @Field("specificDates")
    private List<LocalDate> specificDates;

    @Field("paused")
    private boolean paused;

    @Field("metadata")
    private Map<String, Object> metadata;

    public boolean isActiveOn(LocalDate date) {
        if (paused) return false;
        if (startDate != null && date.isBefore(startDate)) return false;
        if (endDate != null && date.isAfter(endDate)) return false;
        return true;
    }

    public boolean isSpecificDate(LocalDate date) {
        return specificDates != null && specificDates.contains(date);
    }
}