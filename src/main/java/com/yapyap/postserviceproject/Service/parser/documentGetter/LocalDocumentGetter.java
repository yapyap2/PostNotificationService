package com.yapyap.postserviceproject.Service.parser.documentGetter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LocalDocumentGetter implements DocumentGetter{

    String localResource;
    @Override
    public Document getDocument(String url) throws IOException {

        URL localUrl = getClass().getClassLoader().getResource("static/" + localResource);

        File html = new File(localUrl.getFile());

        return Jsoup.parse(html);
    }

    public void setLocalResource(String localResource) {
        this.localResource = localResource;
    }
}
