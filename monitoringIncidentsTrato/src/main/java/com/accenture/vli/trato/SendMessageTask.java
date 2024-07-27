package com.accenture.vli.trato;

import com.accenture.vli.trato.credentials.RobotData;
import com.accenture.vli.trato.service.ServiceaideService;
import com.accenture.vli.trato.utis.FilterAndFormatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SendMessageTask implements Runnable {

    private final TelegramBot bot;
    private final String chatId;

    public SendMessageTask(TelegramBot bot, String chatId) {
        this.bot = bot;
        this.chatId = chatId;
    }

    @Override
    public void run() {
        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(7, 0); // 07:00
        LocalTime end = LocalTime.of(23, 45);  // 18:00

        if (now.isAfter(start) && now.isBefore(end)) {
            ServiceaideService serviceaideService = new ServiceaideService();
            String json;
            try {
                json = serviceaideService.getIncidents();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            FilterAndFormatMessage filterAndFormatMessage = new FilterAndFormatMessage();
            var messageForTelegram = filterAndFormatMessage.filterAndFormatMessage(json);

            if (!messageForTelegram.isEmpty()) {
                bot.sendMessage(chatId, messageForTelegram);
            }
        }
    }

    public static void main(String[] args) {

        TelegramBot telegramBot = new TelegramBot(RobotData.BOT_TOKEN, RobotData.BOT_TOKEN);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        SendMessageTask sendMessageTask = new SendMessageTask(telegramBot, RobotData.CHAT_ID);

        long initialDelay = 0L; // atraso inicial
        long period = 5L; // período em minutos

        scheduler.scheduleAtFixedRate(sendMessageTask, initialDelay, period, TimeUnit.MINUTES);
    }
}