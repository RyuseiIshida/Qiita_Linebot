package com.github.ryuseiishida.testbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.linecorp.bot.client.LineMessagingService;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.message.*;

import java.io.IOException;


@SpringBootApplication
@LineMessageHandler
public class QiitaBotController {

    @Autowired
    private LineMessagingService mLineMessagingService;

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws IOException {
    String replyevent = event.getMessage().getText();
    String token = event.getReplyToken();
    ReplyMessage replymessage;

    if(replyevent.contains("ランキング")){
        Ranking ranking = new Ranking();
        replymessage = ranking.getRanking(replyevent,token);
    }
    else{
        Serch serch = new Serch();
        replymessage = serch.getSerch(replyevent,token);
    }

    mLineMessagingService.replyMessage(replymessage).execute();
    }

    public static void main(String[] args) {
    SpringApplication.run(QiitaBotController.class, args);
    }
}