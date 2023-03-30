package com.yapyap.postserviceproject.Service.parser;

import com.yapyap.postserviceproject.ApiAddress;
import com.yapyap.postserviceproject.Service.parser.documentGetter.DocumentGetter;
import com.yapyap.postserviceproject.Status;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CjParser implements Parser{

    private DocumentGetter documentGetter;

    @Override
    public List<Status> parsing(String query) {

        Document doc = null;

        try {
            doc = documentGetter.getDocument(ApiAddress.CJ_ADDRESS + query);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Element table = doc.getElementsByTag("table").get(6);

        List<Element> trList = table.getElementsByTag("tr");
        trList.remove(0);

        List<Status> statusList = new ArrayList<>();

        for(Element tr : trList){

            if(tr.childrenSize() == 2) continue;

            List<Element> td = tr.getElementsByTag("td");

            Status status = new Status();

            // index 0는 날짜, index 1은 시간, index 3는 위치, index 5는 현재상황을 담고있음

            String dateTime = td.get(0).text() + " " + td.get(1).text();
            LocalDateTime d = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            status.setStatusTime(d);

            status.setCurrentPosition(td.get(3).text());
            status.setDetail(td.get(5).text());

            statusList.add(status);
        }
        return statusList;
    }


    public void setDocumentGetter(DocumentGetter documentGetter) {
        this.documentGetter = documentGetter;
    }
}
