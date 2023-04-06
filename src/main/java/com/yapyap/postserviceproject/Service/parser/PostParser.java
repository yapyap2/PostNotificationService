package com.yapyap.postserviceproject.Service.parser;

import com.yapyap.postserviceproject.ApiAddress;
import com.yapyap.postserviceproject.Service.documentGetter.DocumentGetter;
import com.yapyap.postserviceproject.Status;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostParser implements Parser{

    DocumentGetter documentGetter;
    @Override
    public List<Status> getStatus(String query) {
        try {
            return parsing(query);
        } catch (IOException e) {
            throw new RuntimeException(e);}
    }

    private List<Status> parsing(String invoice) throws IOException{

        Document doc = documentGetter.getDocument(ApiAddress.POST_ADDRESS + invoice);

        List<Element> trackList = doc.getElementsByTag("detaileTrackList");
        List<Status> statusList = new ArrayList<>();

        for(Element ele : trackList){
            Status status = new Status();
            List<Element> elelist = ele.getAllElements();

            String date = elelist.get(2).text() + " " + elelist.get(3).text();

            status.setStatusTime(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            status.setDetail(elelist.get(4).text());
            status.setCurrentPosition(elelist.get(5).text());

            statusList.add(status);
        }
        Collections.reverse(statusList);

        return statusList;
    }

    @Override
    public void setDocumentGetter(DocumentGetter documentGetter) {
        this.documentGetter = documentGetter;
    }

    public Boolean checkAvailable(Document doc){
        return doc.getElementsByTag("successYN").get(0).text().startsWith("Y");
    }

    @Override
    public boolean verifyInvoiceCode(String invoice) {
        Document doc;
        try {
            doc = documentGetter.getDocument(ApiAddress.CJ_ADDRESS + invoice);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return checkAvailable(doc);
    }

    @Override
    public boolean checkComplete(Status status) {
        return status.getDetail().startsWith("배달완료");
    }

}
