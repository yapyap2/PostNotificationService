package com.yapyap.postserviceproject.Service.documentGetter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ApiDocumentGetter implements DocumentGetter{
    @Override
    public Document getDocument(String url) throws IOException {

        return Jsoup.connect(url).get();
    }
}
