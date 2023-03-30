package com.yapyap.postserviceproject.Service.parser;

import com.yapyap.postserviceproject.InvoiceNumber;
import com.yapyap.postserviceproject.Service.parser.documentGetter.ApiDocumentGetter;
import com.yapyap.postserviceproject.Service.parser.documentGetter.DocumentGetter;
import com.yapyap.postserviceproject.Service.parser.documentGetter.LocalDocumentGetter;
import com.yapyap.postserviceproject.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

class ParserTest {

    Parser cjParser = new CjParser();

    DocumentGetter documentGetter = new ApiDocumentGetter();


    @BeforeEach
    public void beforeTest(){
        cjParser.setDocumentGetter(documentGetter);
    }

    @Test
    public void simpleParsingUsingApiTest(){
        List<Status> list = cjParser.parsing(InvoiceNumber.CJ_INVOICE_NUM);

        list.forEach(item -> System.out.println(item));

    }

    @Test
    public void statusUpdateTest(){

        LocalDocumentGetter localDocumentGetter = new LocalDocumentGetter();
        localDocumentGetter.setLocalResource("/CjHtml1.html");

        cjParser.setDocumentGetter(localDocumentGetter);

        List<Status> list1 = cjParser.parsing("fake Invoice Number");

        localDocumentGetter.setLocalResource("CjHtml2.html");

        List<Status> list2 = cjParser.parsing("fake Invoice Number");

        assertThat(list2.size(), is(list1.size() + 1 ));
        assertThat(list2.get(1).toString(), is(list1.get(0).toString()));

    }

}