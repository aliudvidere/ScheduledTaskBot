package com.schedule.scheduledtaskbot.scheduler;

import com.schedule.scheduledtaskbot.service.CommandService;
import com.schedule.scheduledtaskbot.telegram.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PingTaskScheduler {

    private final TelegramBot telegramBot;

    private final CommandService commandService;

    @Scheduled(fixedRate = 60_000L, initialDelay = 0L)
    public void activityRequest() {
        List<SendMessage> messageList = commandService.sendLunaMessage();
        if (!messageList.isEmpty()) {
            messageList.forEach(telegramBot::sendMessage);
        }
    }
}
