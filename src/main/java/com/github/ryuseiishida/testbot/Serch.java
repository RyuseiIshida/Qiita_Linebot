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

public class Serch {
    public ReplyMessage getSerch(String replyevent, String token)throws IOException {
    ArrayList<Message> replymessage = new ArrayList<Message>();
    ArrayList<CarouselColumn> carouselColumn = new ArrayList<CarouselColumn>();
    CarouselTemplate carouselTemplate = new CarouselTemplate(carouselColumn);
    ArrayList<Action> actions = new ArrayList<Action>();
    final String SERCH_URL = "http://qiita.com/search?q=";//検索アドレス
    String q = "";//検索キーワードアドレス
    String page = "&page=";//ページ番号アドレス
    int numofpage = 1; //ページ番号
    String result = "";//出力用
    String tmp = "";
    
    q = replyevent;
    
    if(replyevent.contains("ページ")){
        tmp = replyevent.substring(replyevent.length()-1);
        numofpage += new Integer(tmp);
        page += numofpage;
        q = replyevent.substring(0, q.indexOf(","));
    }
    else{
        numofpage=2;
    }

    Document document_serch = Jsoup.connect(SERCH_URL+q+page).get();
    Elements elements =  document_serch.select(".searchResult_itemTitle");

    for(Element element : elements){
        result += "\n------------------\n\n";
        result += element.text() + "\n";
        tmp = element.outerHtml().substring(44);
        tmp = tmp.substring(0, tmp.indexOf("\""));
        result += "http://qiita.com/" + tmp + "\n";
    }

    actions.add(new PostbackAction("次のページ","postback",q+ ",ページ" + numofpage));
    carouselColumn.add(new CarouselColumn(null, null, "もっと見る", actions));
    replymessage.add(new TextMessage("[ " + replyevent +" ]の検索結果"));
    replymessage.add(new TextMessage(result));
    replymessage.add(new TemplateMessage("template",carouselTemplate));

    ReplyMessage replyMessage = new ReplyMessage(token, replymessage);

    return replyMessage;
    }
}


