package com.billing.infrastructure.persistence;

import com.billing.domain.ChargeSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ChargeScheduleRepository extends MongoRepository<ChargeSchedule, UUID> {

    @Query(value = "{ " +
            "'paused': false, " +
            "'startDate': { '$lte': ?0 }, " +
            "$or: [ { 'endDate': null }, { 'endDate': { '$gte': ?0 } } ] " +
            "}")
    List<ChargeSchedule> findDueSchedules(LocalDate today);
}
