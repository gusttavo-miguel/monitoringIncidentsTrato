package com.accenture.vli.trato;

import com.accenture.vli.trato.credentials.RobotData;
import com.accenture.vli.trato.service.ServiceaideService;
import com.accenture.vli.trato.utis.FilterAndFormatMessage;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

public class Main implements HttpFunction {

    @Override
    public void service(HttpRequest httpRequest, HttpResponse httpResponse) throws Exception {
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