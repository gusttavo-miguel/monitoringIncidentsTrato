package com.example.botTelegram;

import com.example.botTelegram.service.ServiceaidService;
import com.example.botTelegram.utis.RobotData;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SendMessageTask implements Runnable {

    private final Bot bot;
    private final String chatId;

    public SendMessageTask(Bot bot, String chatId) {
        this.bot = bot;
        this.chatId = chatId;
    }

    @Override
    public void run() {
        ServiceaidService serviceaidService = new ServiceaidService();
        String reponse;
        try {
            reponse = serviceaidService.getIncidents();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        bot.sendMessage(chatId, reponse);
    }

    public static void main(String[] args) {

        Bot bot = new Bot(RobotData.BOT_TOKEN, RobotData.BOT_TOKEN);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        SendMessageTask task = new SendMessageTask(bot, RobotData.CHAT_ID);

        long initialDelay = 0L; // atraso inicial
        long period = 20L; // período de 20 minutos

        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MINUTES);
    }
}