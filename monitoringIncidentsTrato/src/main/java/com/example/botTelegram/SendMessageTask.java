package com.example.botTelegram;

import com.example.botTelegram.service.ServiceaidService;
import com.example.botTelegram.utis.RobotData;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalTime;
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

//    @Override
//    public void run() {
//        ServiceaidService serviceaidService = new ServiceaidService();
//        String reponse;
//        try {
//            reponse = serviceaidService.getIncidents();
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        bot.sendMessage(chatId, reponse);
//    }

    @Override
    public void run() {
        LocalTime now = LocalTime.now();
        LocalTime start = LocalTime.of(7, 0); // 07:00
        LocalTime end = LocalTime.of(18, 0);  // 18:00

        if (now.isAfter(start) && now.isBefore(end)) {
            ServiceaidService serviceaidService = new ServiceaidService();
            String response;
            try {
                response = serviceaidService.getIncidents();
                bot.sendMessage(chatId, response);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {

        Bot bot = new Bot(RobotData.BOT_TOKEN, RobotData.BOT_TOKEN);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        SendMessageTask task = new SendMessageTask(bot, RobotData.CHAT_ID);

        long initialDelay = 0L; // atraso inicial
        long period = 60L; // período de 1 hora

        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MINUTES);
    }
}