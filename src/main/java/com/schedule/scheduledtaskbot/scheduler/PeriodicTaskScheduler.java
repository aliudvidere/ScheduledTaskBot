package com.schedule.scheduledtaskbot.scheduler;

import com.schedule.scheduledtaskbot.model.entity.PeriodicTaskEntity;
import com.schedule.scheduledtaskbot.repository.PeriodicTaskEntityRepository;
import com.schedule.scheduledtaskbot.service.PeriodicTaskExecutor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PeriodicTaskScheduler {

    private final PeriodicTaskEntityRepository periodicTaskEntityRepository;

    private final PeriodicTaskExecutor periodicTaskExecutor;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        refreshTasks();
    }

    @Scheduled(fixedRate = 10000)
    public void refreshTasks() {
        List<PeriodicTaskEntity> tasks = periodicTaskEntityRepository.findByIsActiveTrue();
        Set<Long> activeTaskIds = tasks.stream().map(PeriodicTaskEntity::getId).collect(Collectors.toSet());

        scheduledTasks.keySet().removeIf(id -> {
            if (!activeTaskIds.contains(id)) {
                scheduledTasks.get(id).cancel(false);
                return true;
            }
            return false;
        });


        for (PeriodicTaskEntity task : tasks) {
            if (!scheduledTasks.containsKey(task.getId())) {
                ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(
                        () -> periodicTaskExecutor.executeTask(task.getClassName(), task.getMethodName()),
                        0,
                        task.getIntervalSeconds(),
                        TimeUnit.SECONDS
                );
                scheduledTasks.put(task.getId(), future);
            }
        }
    }
}

