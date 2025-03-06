package com.schedule.scheduledtaskbot.service;

import com.schedule.scheduledtaskbot.telegram.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PeriodicTaskService {

    private final TelegramBot telegramBot;

    private final CommandService commandService;

    public void activityRequest() {
        List<SendMessage> messageList = commandService.sendLunaMessage();
        if (!messageList.isEmpty()) {
            messageList.forEach(telegramBot::sendMessage);
        }
    }
}
