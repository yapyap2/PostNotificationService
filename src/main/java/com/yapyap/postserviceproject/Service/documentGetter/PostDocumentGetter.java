package com.yapyap.postserviceproject.Service.documentGetter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostDocumentGetter implements DocumentGetter{
    @Override
    public Document getDocument(String url) throws IOException {

        URL urlObject = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("GET");

        String res = readBody(connection);

        return Jsoup.parse(res);
    }

    private String readBody(HttpURLConnection conn) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer stringBuffer = new StringBuffer();
        String inputLine;

        while ((inputLine = bufferedReader.readLine()) != null) {
            stringBuffer.append(inputLine);
        }
        bufferedReader.close();

        return stringBuffer.toString();
    }
}
