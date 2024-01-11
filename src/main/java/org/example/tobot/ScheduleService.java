package org.example.tobot;


import lombok.RequiredArgsConstructor;
import org.example.tobot.dto.CentralRussianBankService;
import org.example.tobot.dto.ValuteCursOnDate;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ScheduleService {

    private final ActiveChatRepository activeChatRepository;
    private final BotService botService;
    private final CentralRussianBankService centralRussianBankService;
    private final List<ValuteCursOnDate> previousRates = new ArrayList<>();


    public void notifyAboutChangesInCurrencyRate() {
        try {
            List<ValuteCursOnDate> currentRates = centralRussianBankService.getCurrenciesFromCbr();
            Set<Long> activeChatIds = activeChatRepository.findAll().stream().map(ActiveChat::getChatId).collect(
                    Collectors.toSet());
            if(!previousRates.isEmpty()) {
                for(int index = 0; index < currentRates.size(); index++) {
                    if(currentRates.get(index).getCourse() - previousRates.get(index).getCourse() >= 10.0) {
                        botService.sendNotificationToAllActiveChats("Курс " +
                                currentRates.get(index).getName() + " увеличился на 10 рублей", activeChatIds);
                    }else if (currentRates.get(index).getCourse() - previousRates.get(index).getCourse() <= 10.0) {
                        botService.sendNotificationToAllActiveChats("Курс " +
                                currentRates.get(index).getName() + " уменьшился на 10 рублей", activeChatIds);
                    }
                }
            } else {
                previousRates.addAll(currentRates);
            }
        }catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }
}
