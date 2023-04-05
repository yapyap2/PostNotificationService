package com.yapyap.postserviceproject.Service;

import com.yapyap.postserviceproject.InvoiceNumber;
import com.yapyap.postserviceproject.Service.documentGetter.ApiDocumentGetter;
import com.yapyap.postserviceproject.Service.documentGetter.DocumentGetter;
import com.yapyap.postserviceproject.Service.documentGetter.LocalDocumentGetter;
import com.yapyap.postserviceproject.Status;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ParserTest {

    Parser cjParser = new CjParser();

    DocumentGetter documentGetter = new ApiDocumentGetter();
    LocalDocumentGetter localDocumentGetter = new LocalDocumentGetter();


    @Before
    public void beforeTest(){
        cjParser.setDocumentGetter(documentGetter);
    }

    @Test
    public void simpleParsingUsingApiTest(){
        List<Status> list = cjParser.getStatus(InvoiceNumber.CJ_INVOICE_NUM);

        list.forEach(item -> System.out.println(item));

    }

    @Test
    public void statusUpdateTest(){

        LocalDocumentGetter localDocumentGetter = new LocalDocumentGetter();
        localDocumentGetter.setLocalResource("/CjHtml1.html");

        cjParser.setDocumentGetter(localDocumentGetter);

        List<Status> list1 = cjParser.getStatus("fake Invoice Number");

        localDocumentGetter.setLocalResource("CjHtml2.html");

        List<Status> list2 = cjParser.getStatus("fake Invoice Number");

        assertThat(list2.size(), is(list1.size() + 1 ));
        assertThat(list2.get(1).toString(), is(list1.get(0).toString()));

    }

    @Test()
    public void verifyInvoiceCodeTest(){

        assertTrue(cjParser.verifyInvoiceCode(InvoiceNumber.CJ_INVOICE_NUM));
        assertFalse(cjParser.verifyInvoiceCode("fake invoice code"));

    }

    @Test
    public void checkCompleteTest(){
        LocalDocumentGetter localDocumentGetter =  new LocalDocumentGetter();
        localDocumentGetter.setLocalResource("/CjHtml1.html");

        cjParser.setDocumentGetter(localDocumentGetter);
        List<Status> list = cjParser.getStatus("fake invoice code");
        assertFalse(cjParser.checkComplete(list.get(0)));

        localDocumentGetter.setLocalResource("/CjHtml2.html");
        list = cjParser.getStatus("fake invoice code");
        assertTrue(cjParser.checkComplete(list.get(0)));

    }


}