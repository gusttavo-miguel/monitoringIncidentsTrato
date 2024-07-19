package com.example.botTelegram;

import com.example.botTelegram.service.ServiceaidService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

// Back-end do bot
public class Bot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return RobotData.BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return RobotData.BOT_TOKEN;
    }

    // Método responsável por coletar a mensagem enviada pelo usuário no chat do bot no telegram e realizar uma ação apartir disso.
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage response = null;
            try {
                response = toRespond(update); // envia a mensagem do usário ao método toRespond, que neste código, é responável pelo processamento das ações do bot
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            try {
                execute(response); // método que envia a resposta do bot ao chat do usuário no telegram
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    // Método responsávem por realizar as ações do bot com base na integração do usuário
    private SendMessage toRespond(Update update) throws JsonProcessingException {
        var textMessage = update.getMessage().getText().toLowerCase();
        var chatId = update.getMessage().getChatId().toString();

        ServiceaidService ServiceaidService = new ServiceaidService();
        var retorno = "";

        if (textMessage.equalsIgnoreCase("start")) {
            retorno = ServiceaidService.getIncidents();
        }


        return SendMessage.builder()
                .text(retorno)
                .chatId(chatId)
                .build();


        //        retorno = switch (textMessage) {
//            case "oi" -> """
//                    Olá!, eu sou um bot!
//
//                    O que você deseja ?
//                    1 - Monitorar incidents
//                    2 - Saber a data atual
//                    3 - Saber a hora atual
//                    """;
//            case "1" -> ServiceaidService.getIncidents();
//            case "2" -> getData();
//            case "3" -> getHora();
//            default -> "utilize um dos comandos:\n1 - Saber a data atual\n2 - Saber a hora atual\n";
//        };

    }
}