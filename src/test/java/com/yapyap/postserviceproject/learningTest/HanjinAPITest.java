package com.yapyap.postserviceproject.learningTest;

import com.yapyap.postserviceproject.ApiAddress;
import com.yapyap.postserviceproject.InvoiceNumber;
import com.yapyap.postserviceproject.Status;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HanjinAPITest {

    @Test
    public void apiTest() throws IOException {
        String invoice = "452205032834";

        Document doc = Jsoup.connect(ApiAddress.HANJIN_ADDRESS + invoice).get();

        Element board =doc.getElementsByClass("board-list-table").get(1);

        Element tbody = board.getElementsByTag("tbody").get(0);

        List<Element> list = tbody.getElementsByTag("tr");

        List<Status> statusList = new ArrayList<>();

        for(Element ele : list){
            Status status = new Status();
            List<Element> tdList = ele.getElementsByTag("td");

            String date = tdList.get(0).text();
            String time = tdList.get(1).text();
            String dateTime =   date + " " + time;
            status.setStatusTime(LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

            status.setCurrentPosition(tdList.get(2).text());

            status.setDetail(tdList.get(3).text());

            statusList.add(status);
        }

        Collections.reverse(statusList);

        statusList.forEach(status -> System.out.println(status));
    }

    @Test()
    public void checkAvailable() throws IOException {
        String invoice = "4522";

        Document doc = Jsoup.connect(ApiAddress.HANJIN_ADDRESS + invoice).get();

        System.out.println(doc.getElementsByClass("board-list-table").get(1).text());
    }

}
