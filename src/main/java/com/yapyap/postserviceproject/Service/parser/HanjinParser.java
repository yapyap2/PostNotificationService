package com.yapyap.postserviceproject.Service.parser;

import com.yapyap.postserviceproject.ApiAddress;
import com.yapyap.postserviceproject.Service.documentGetter.DocumentGetter;
import com.yapyap.postserviceproject.Status;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HanjinParser implements Parser{
    String carrier = "HANJIN";

    DocumentGetter documentGetter;
    @Override
    public List<Status> getStatus(String query) {
        try{
            return parsing(query);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private List<Status> parsing(String invoice) throws IOException {

        Document doc = documentGetter.getDocument(ApiAddress.HANJIN_ADDRESS + invoice);

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

        return statusList;
    }

    @Override
    public void setDocumentGetter(DocumentGetter documentGetter) {
        this.documentGetter = documentGetter;
    }

    private boolean checkAvailable(Document doc){
        try{
            doc.getElementsByClass("board-list-table").get(1);
        } catch (IndexOutOfBoundsException e){
            return false;
        }
        return true;
    }

    @Override
    public boolean verifyInvoiceCode(String invoice) {
        Document doc;
        try {
            doc = documentGetter.getDocument(ApiAddress.HANJIN_ADDRESS + invoice);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return checkAvailable(doc);
    }

    @Override
    public boolean checkComplete(Status status) {
        return status.getDetail().startsWith("배송완료");
    }

    @Override
    public String getCarrier() {
        return carrier;
    }
}
