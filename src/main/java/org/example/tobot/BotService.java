package org.example.tobot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.tobot.dto.CentralRussianBankService;
import org.example.tobot.dto.ValuteCursOnDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@RequiredArgsConstructor
public class BotService extends TelegramLongPollingBot {

    private final CentralRussianBankService centralRussianBankService;

    @Value("${bot.api.key}")
    private String apiKey;

    @Value("${bot.name}")
    private String name;

    @PostConstruct
    public void start() {
        log.info("username: {}, token: {}", name, apiKey);
    }
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        try {
            SendMessage response = new SendMessage();
            Long chatId = message.getChatId();
            response.setChatId(String.valueOf(chatId));


            if ("/currentrates".equalsIgnoreCase(message.getText())) {

                for (ValuteCursOnDate valuteCursOnDate : centralRussianBankService.getCurrenciesFromCbr()) {


                    response.setText(StringUtils.defaultIfBlank(response.getText(), "") + valuteCursOnDate.getName() + " - " + valuteCursOnDate.getCourse() + "\n");
                }
            }

            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return apiKey;
    }


}
