package com.yapyap.postserviceproject.learningTest;

import com.yapyap.postserviceproject.Status;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class JsoupTest {
    @Test
    public void doorToDoorJsoupTest() throws IOException {

        String query = "363818621704";

        Document doc = Jsoup.connect("http://nplus.doortodoor.co.kr/web/detail.jsp?slipno=" + query).get();

        Element table = doc.getElementsByTag("table").get(6);

        List<Element> trList = table.getElementsByTag("tr");
        trList.remove(0);

        List<Status> statusList = new ArrayList<>();

        for(Element tr : trList){

            if(tr.childrenSize() == 2) continue;

            List<Element> td = tr.getElementsByTag("td");

            Status status = new Status();

            // index 0는 날짜, index 1은 시간, index 3는 위치, index 5는 현재상황을 담고있음
            System.out.println(td.get(0).text() +"\n" +td.get(1).text() +"\n" + td.get(3).text() +"\n" +td.get(5).text() );

            String dateTime = td.get(0).text() + " " + td.get(1).text();
            LocalDateTime d = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            status.setStatusTime(d);

            status.setCurrentPosition(td.get(3).text());
            status.setDetail(td.get(5).text());

            statusList.add(status);
        }

    }



}
