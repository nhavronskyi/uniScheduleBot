package com.example.unischedulebot.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BotServiceImpl implements BotService {

    private GService gService;

    public SendMessage callbackQueryHelper(Update update) {
        String message = null;
        switch (update.getCallbackQuery().getData()) {
            case "getSchedule" -> message = gService.getSchedule().entrySet()
                    .stream()
                    .map(x -> x.getKey() + " " + x.getValue().stream().map(y -> "\n" + y + "\n").collect(Collectors.joining()) + "\n\n")
                    .collect(Collectors.joining());
            case "getNextEvent" -> message = gService.getNextEvent();
            case "createEventsForSchedule" -> {
                gService.createEventsForSchedule();
                message = "events from the schedule have been created";
            }
        }
        return sendMessage(message, update.getCallbackQuery().getMessage().getChatId());
    }

    private SendMessage sendMessage(String message, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        return sendMessage;
    }

    @Override
    public SendMessage buttonHelper(Update update) {
        return buttonManager(Map.of(
                "getSchedule", "getSchedule",
                "getNextEvent", "getNextEvent",
                "createEventsForSchedule", "createEventsForSchedule"
        ), update.getMessage().getChatId());
    }

    private SendMessage buttonManager(Map<String, String> map, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Choose option");
        if (map != null) {
            attachButtons(sendMessage, map);
        }
        sendMessage.setChatId(chatId);
        return sendMessage;
    }

    private void attachButtons(SendMessage message, Map<String, String> map) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        for (String s : map.keySet()) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setText(s);
            button.setCallbackData(s);
            keyboard.add(List.of(button));
        }
        markup.setKeyboard(keyboard);
        message.setReplyMarkup(markup);
    }


}
