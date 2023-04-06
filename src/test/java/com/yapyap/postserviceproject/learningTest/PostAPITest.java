package com.yapyap.postserviceproject.learningTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;

import java.net.URL;


public class PostAPITest {

    @Test
    public void postApiTest() throws IOException, ParserConfigurationException, SAXException {
        String stringUrl = "http://openapi.epost.go.kr/trace/retrieveLongitudinalCombinedService/retrieveLongitudinalCombinedService/getLongitudinalCombinedList?serviceKey=hCn95S3o5nhBpHtwPmNxKY%2BVxObFAiXc8wqjhTA3%2B%2BGQSR3u3dgbKLkwMkwfcSZh%2BMxQ27S%2B%2FfqI3GudPL%2FrTg%3D%3D&rgist=";

        String invoiceCode = "CN051928845JP";

        stringUrl += invoiceCode;
        URL url = new URL(stringUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);

        System.out.println(connection.getResponseCode());

        String res = readJson(connection);

        Document doc = Jsoup.parse(res);

        System.out.println(doc);

    }




    public String readJson(HttpURLConnection conn) throws IOException {
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
