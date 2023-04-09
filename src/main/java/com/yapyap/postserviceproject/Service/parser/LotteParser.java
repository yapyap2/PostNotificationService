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
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class LotteParser implements Parser{
    String carrier = "LOTTE";
    DocumentGetter documentGetter;
    @Override
    public List<Status> getStatus(String query) {
        try{
            return parsing(query);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private List<Status> parsing(String query) throws IOException {
        Document doc = documentGetter.getDocument(ApiAddress.LOTTE_ADDRESS + query);

        Element scroll =  doc.getElementsByClass("scroll_date_table").get(0);

        List<Element> trList = scroll.getElementsByTag("tr");
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

        return statusList;
    }
    @Override
    public void setDocumentGetter(DocumentGetter documentGetter) {
        this.documentGetter = documentGetter;
    }

    private boolean checkAvailable(Document doc){

        Element scroll =  doc.getElementsByClass("scroll_date_table").get(0);

        List<Element> trList = scroll.getElementsByTag("tr");

        return !trList.get(1).text().startsWith("보내시는");
    }
    @Override
    public boolean verifyInvoiceCode(String invoice) {
        Document doc;
        try {
            doc = documentGetter.getDocument(ApiAddress.LOTTE_ADDRESS + invoice);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return checkAvailable(doc);
    }

    @Override
    public boolean checkComplete(Status status) {
        return status.getDetail().equals("배달 완료");
    }

    @Override
    public String getCarrier() {
        return carrier;
    }
}
