package com.yapyap.postserviceproject.Service.documentGetter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LocalDocumentGetter implements DocumentGetter{

    private int counter = 1;
    String localResource;
    @Override
    public Document getDocument(String url) throws IOException {
        if(counter==4){
            counter = 1;
        }
        URL localUrl = getClass().getClassLoader().getResource("static/" + localResource + "/" +localResource + "Html" + Integer.toString(counter) + ".html");
        File html = new File(localUrl.getFile());

        counter += 1;
        return Jsoup.parse(html);
    }

    public void setLocalResource(String localResource) {
        this.localResource = localResource;
    }

}
