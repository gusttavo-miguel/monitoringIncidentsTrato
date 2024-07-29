package com.accenture.vli.trato;

import com.accenture.vli.trato.credentials.RobotData;
import com.accenture.vli.trato.service.ServiceaideService;
import com.accenture.vli.trato.utis.FilterAndFormatMessage;

public class Main {

    public static void main(String[] args) {

        TelegramBot telegramBot = new TelegramBot(RobotData.BOT_TOKEN, RobotData.BOT_USER_NAME, RobotData.CHAT_ID);
        ServiceaideService serviceaideService = new ServiceaideService();

        String json = serviceaideService.getIncidents();

        FilterAndFormatMessage filterAndFormatMessage = new FilterAndFormatMessage();
        var messageForTelegram = filterAndFormatMessage.filterAndFormatMessage(json);

        if (!messageForTelegram.isEmpty()) {
           telegramBot.sendMessage(messageForTelegram);
        }
    }
}