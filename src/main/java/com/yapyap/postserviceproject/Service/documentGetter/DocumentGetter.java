package com.yapyap.postserviceproject.Service.documentGetter;

import org.jsoup.nodes.Document;

import java.io.IOException;

public interface DocumentGetter {

    Document getDocument(String url) throws IOException;
}
