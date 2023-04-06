package com.yapyap.postserviceproject.learningTest;

import com.yapyap.postserviceproject.Status;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LogenAPITest {

    @Test
    public void apiTest() throws IOException {
        String invoice = "34788507110";

        Document document = Jsoup.connect("https://www.ilogen.com/web/personal/trace/" + invoice).get();

        Element table = document.getElementsByClass("data tkInfo").get(0);
        List<Element> trList =  table.getElementsByTag("tr");

        trList.remove(0);
        List<Status> statusList = new ArrayList<>();

        for (Element ele : trList) {
            List<Element> tdList = ele.getElementsByTag("td");

            Status status = new Status();

            String dateTime = tdList.get(0).text();
            LocalDateTime d = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
            status.setStatusTime(d);

            status.setCurrentPosition(tdList.get(1).text());
            status.setDetail(tdList.get(2).text());

            statusList.add(status);
        }


        statusList.forEach(i -> System.out.println(i.toString()));
    }

}
