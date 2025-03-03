package com.schedule.scheduledtaskbot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schedule.scheduledtaskbot.client.LunaClient;
import com.schedule.scheduledtaskbot.model.entity.BotUserEntity;
import com.schedule.scheduledtaskbot.repository.BotUserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.*;
import java.util.stream.Collectors;

import static com.schedule.scheduledtaskbot.constants.MessageConstants.YOU_HAVE_ALREADY_REGISTERED;
import static com.schedule.scheduledtaskbot.constants.MessageConstants.YOU_HAVE_SUCCESSFULLY_REGISTERED;

@Service
@RequiredArgsConstructor
public class CommandService {

    private final BotUserEntityRepository botUserEntityRepository;

    private final LunaClient lunaClient;

    public SendMessage register(Message registerMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(registerMessage.getChatId());
        Optional<BotUserEntity> botUserEntityOptional = botUserEntityRepository.findByTgCode(registerMessage.getChatId().toString());
        if (botUserEntityOptional.isPresent()) {
            String lastName = registerMessage.getFrom().getLastName();
            String firstName = registerMessage.getFrom().getFirstName();
            BotUserEntity botUserEntity = botUserEntityOptional.get();
            botUserEntity.setUsername(lastName != null ? lastName + " " + firstName : firstName);
            botUserEntityRepository.save(botUserEntity);
            message.setText(YOU_HAVE_ALREADY_REGISTERED.formatted(botUserEntity.getUsername()));
        }
        else {
            BotUserEntity botUserEntity = new BotUserEntity();
            String lastName = registerMessage.getFrom().getLastName();
            String firstName = registerMessage.getFrom().getFirstName();
            botUserEntity.setUsername(lastName != null ? lastName + " " + firstName : firstName);
            botUserEntity.setTgCode(registerMessage.getChatId().toString());
            botUserEntityRepository.save(botUserEntity);
            message.setText(YOU_HAVE_SUCCESSFULLY_REGISTERED.formatted(botUserEntity.getUsername()));
        }
        return message;
    }

    public List<SendMessage> sendLunaMessage() {
        String messageText;
        String lunaStaffResponse = lunaClient.getStaff("Bearer gtcwf654agufy25gsadh");
        String lunaActivityResponse = lunaClient.getActivity("Bearer gtcwf654agufy25gsadh");
        List<Object> staff;
        Map<String, Object> activities;
        try {
            staff = new ObjectMapper().readValue(lunaStaffResponse, ArrayList.class);
            activities = new ObjectMapper().readValue(lunaActivityResponse, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String staffNeededId = staff.stream().filter(t -> ((Map<String, Object>)t).get("name").toString().equals("Ольга Кремень")).findFirst().map(t -> ((Map<String, Object>)t).get("id").toString()).orElse(null);
        if (staffNeededId != null) {
            List<Map<String, Object>> data = (List<Map<String, Object>>) activities.get("data");
            data = data.stream().filter(t -> t.get("staff_id").toString().equals(staffNeededId) && Integer.parseInt(t.get("capacity").toString()) - Integer.parseInt(t.get("records_count").toString()) > 0).toList();
            if (data.isEmpty()) {
                messageText = "No free slots available";
            }
            else {
                messageText = data.stream().map(t -> t.get("date") + ":" + t.get("capacity") + ":" + t.get("records_count") + "\n").collect(Collectors.joining("\n"));
            }
        }
        else {
            messageText = "No activity";
        }
        return botUserEntityRepository.findAll().stream().map(t -> new SendMessage(t.getTgCode(), messageText)).toList();
    }
}
