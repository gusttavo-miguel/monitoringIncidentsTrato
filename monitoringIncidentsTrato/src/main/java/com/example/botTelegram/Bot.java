package com.example.botTelegram;

import com.example.botTelegram.service.REST;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

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
            SendMessage response = null; // envia a mensagem do usário ao método toRespond, que neste código, é responável pelo processamento das ações do bot
            try {
                response = toRespond(update);
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

        String[] response = new String[0];

        if(textMessage.equals("start")){
            REST REST = new REST();
            response  = new String[]{REST.getIncidents()};
        }

//        var response = switch (textMessage) {
//            case "oi" ->  """
//                    Olá!, eu sou um bot!
//
//                    O que você deseja ?
//                    1 - Buscar incidents
//                    2 - Saber a data atual
//                    3 - Saber a hora atual
//                    """;
//            case "1" -> REST.getIncidents();
//            case "2" -> getData();
//            case "3" -> getHora();
//            default -> "utilize um dos comandos:\n1 - Saber a data atual\n2 - Saber a hora atual\n";
//        };

        return SendMessage.builder()
                .text(Arrays.toString(response))
                .chatId(chatId)
                .build();
    }

    public String getIncidents() {

        var formatter = new SimpleDateFormat("HH:mm:ss");
        return "A hora atual é: " + formatter.format(new Date());




    }

    public String getData() {
        var formatter = new SimpleDateFormat("dd/MM/yyyy");
        return "A data atual é: " + formatter.format(new Date());
    }

    public String getHora() {
        var formatter = new SimpleDateFormat("HH:mm:ss");
        return "A hora atual é: " + formatter.format(new Date());
    }
}