package com.github.ryuseiishida.testbot;

import com.linecorp.bot.client.LineMessagingService;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.*;
import com.linecorp.bot.model.message.*;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;

public class Ranking {
    public ReplyMessage getRanking(String replyevent, String token) throws IOException {
    ArrayList<Message> replymessage = new ArrayList<>();
    final String POPULAR_URL = "http://qiita.com/popular-items";
    String result = "";
    String tmp = "";
    

    Document document_popular = Jsoup.connect(POPULAR_URL).get();
    Elements elements = document_popular.select(".popularItem_articleTitle_text");
    
    
    int i = 1;
    for(Element element : elements){
        result += "\n------------------\n\n";
        result += element.text() + "\n";
        tmp = element.outerHtml().substring(78);
        tmp = tmp.substring(0, tmp.indexOf("&"));
        result += "http://qiita.com" + tmp + "\n";
        if(i>10){
            break;
        }
        i++;
    }

        

    ArrayList<CarouselColumn> carouselColumn = new ArrayList<CarouselColumn>();
    ArrayList<Action> actions = new ArrayList<Action>();

    replymessage.add(new TextMessage(result));
    ReplyMessage replyMessage = new ReplyMessage(token, replymessage);

    return replyMessage;
    }
}