package com.schedule.scheduledtaskbot.scheduler;

import com.schedule.scheduledtaskbot.service.CommandService;
import com.schedule.scheduledtaskbot.telegram.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PingTaskScheduler {

    private final TelegramBot telegramBot;

    private final CommandService commandService;

    @Scheduled(fixedRate = 3_600_000L, initialDelay = 0L)
    public void activityRequest() {
        commandService.sendLunaMessage().forEach(telegramBot::sendMessage);
    }
}
