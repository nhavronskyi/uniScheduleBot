package com.example.unischedulebot.component;

import com.example.unischedulebot.service.BotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final TelegramProps telegramProps;

    private final BotService botService;

    @Override
    public String getBotUsername() {
        return telegramProps.getUsername();
    }

    @Override
    public String getBotToken() {
        return telegramProps.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            sendApiMethodAsync(botService.buttonHelper(update));
        } else if (update.hasCallbackQuery()) {
            sendApiMethodAsync(botService.callbackQueryHelper(update));
        }
    }
}
