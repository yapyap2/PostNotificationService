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

public class LogenParser implements Parser{
    String carrier = "LOGEN";

    private DocumentGetter documentGetter;
    @Override
    public List<Status> getStatus(String query) {

        try{
            return parsing(query);
        } catch (IOException e) {
            throw new RuntimeException(e);}
    }


    private List<Status> parsing(String invoice) throws IOException{

        Document document = documentGetter.getDocument(ApiAddress.LOGEN_ADDRESS + invoice);

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

        Collections.reverse(statusList);
        return statusList;
    }

    @Override
    public void setDocumentGetter(DocumentGetter documentGetter) {
        this.documentGetter = documentGetter;
    }

    private boolean checkAvailable(Document doc){
        if(doc.getElementsByClass("data tkInfo").size() == 1) return true;
        else return false;
    }

    @Override
    public boolean verifyInvoiceCode(String invoice) {
        Document doc;
        try {
            doc = documentGetter.getDocument(ApiAddress.LOGEN_ADDRESS + invoice);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return checkAvailable(doc);
    }

    @Override
    public boolean checkComplete(Status status) {
        if(status.getDetail().equals("배송완료")) return true;
        else return false;
    }

    @Override
    public String getCarrier() {
        return carrier;
    }
}
