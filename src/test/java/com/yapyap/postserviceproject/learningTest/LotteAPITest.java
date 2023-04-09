package com.yapyap.postserviceproject.learningTest;

import com.yapyap.postserviceproject.Status;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LotteAPITest {

    @Test
    public void apiTest() throws IOException {

        String invoice = "404969447013";
        Document doc = Jsoup.connect("https://www.lotteglogis.com/mobile/reservation/tracking/linkView?InvNo=" + invoice).get();

        Element scroll =  doc.getElementsByClass("scroll_date_table").get(0);

        List<Element> trList = scroll.getElementsByTag("tr");
        trList.remove(0);
        trList.remove(0);

        List<Status> statusList = new ArrayList<>();

        for(Element ele : trList){

            Status status = new Status();

            List<Element> tdList = ele.getElementsByTag("td");

            status.setDetail(tdList.get(0).text());

            String dateTime = tdList.get(1).text();

            try {
                status.setStatusTime(LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            } catch (DateTimeParseException e){
                dateTime = dateTime.substring(0, 10) + " " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
                status.setStatusTime(LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            }

            status.setCurrentPosition(tdList.get(2).text());


            statusList.add(status);
       }
        statusList.forEach(status -> System.out.println(status));
    }
}
