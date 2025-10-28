package com.billing.controller.api;

import com.billing.domain.ChargeSchedule;
import com.billing.infrastructure.persistence.ChargeScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ChargeScheduleController {

    private final ChargeScheduleRepository repository;

    @PostMapping
    public ResponseEntity<ChargeSchedule> create(@RequestBody ChargeSchedule schedule) {
        return ResponseEntity.ok(repository.save(schedule));
    }

    @GetMapping
    public List<ChargeSchedule> getAll() {
        return repository.findAll();
    }
}