package com.schedule.scheduledtaskbot.repository;

import com.schedule.scheduledtaskbot.model.entity.PeriodicTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeriodicTaskEntityRepository extends JpaRepository<PeriodicTaskEntity, Integer> {
    List<PeriodicTaskEntity> findByIsActiveTrue();
}
