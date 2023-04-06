package com.yapyap.postserviceproject.Service;

import com.yapyap.postserviceproject.InvoiceNumber;
import com.yapyap.postserviceproject.Service.documentGetter.ApiDocumentGetter;
import com.yapyap.postserviceproject.Service.documentGetter.DocumentGetter;
import com.yapyap.postserviceproject.Service.documentGetter.LocalDocumentGetter;
import com.yapyap.postserviceproject.Service.documentGetter.PostDocumentGetter;
import com.yapyap.postserviceproject.Service.parser.CjParser;
import com.yapyap.postserviceproject.Service.parser.Parser;
import com.yapyap.postserviceproject.Service.parser.PostParser;
import com.yapyap.postserviceproject.Status;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ParserTest {

    Parser cjParser = new CjParser();
    Parser postParser = new PostParser();


    DocumentGetter documentGetter = new ApiDocumentGetter();
    LocalDocumentGetter localDocumentGetter = new LocalDocumentGetter();


    @Before
    public void beforeTest(){
        cjParser.setDocumentGetter(documentGetter);
        postParser.setDocumentGetter(new PostDocumentGetter());
    }

    @Test
    public void simpleParsingUsingApiTest(){
        List<Status> cjList = cjParser.getStatus(InvoiceNumber.CJ_INVOICE_NUM);
        List<Status> postList = postParser.getStatus(InvoiceNumber.POST_INVOICE_NUM);

        cjList.forEach(item -> System.out.println(item));
        postList.forEach(item -> System.out.println(item));

    }

    @Test
    public void statusUpdateTest(){

        LocalDocumentGetter localDocumentGetterForCJ = new LocalDocumentGetter();
        localDocumentGetterForCJ.setLocalResource("CJ");
        cjParser.setDocumentGetter(localDocumentGetterForCJ);
        List<Status> cjList1 = cjParser.getStatus("fake Invoice Number");
        List<Status> cjList2 = cjParser.getStatus("fake Invoice Number");

        assertThat(cjList2.size(), is(cjList1.size() + 1 ));
        assertThat(cjList2.get(1).toString(), is(cjList1.get(0).toString()));

    }

    @Test()
    public void verifyInvoiceCodeTest(){

        assertTrue(cjParser.verifyInvoiceCode(InvoiceNumber.CJ_INVOICE_NUM));
        assertFalse(cjParser.verifyInvoiceCode("fake invoice code"));

    }

    @Test
    public void checkCompleteTest(){
        LocalDocumentGetter localDocumentGetter =  new LocalDocumentGetter();
        localDocumentGetter.setLocalResource("CJ");

        cjParser.setDocumentGetter(localDocumentGetter);
        List<Status> list = cjParser.getStatus("fake invoice code");
        assertFalse(cjParser.checkComplete(list.get(0)));

        list = cjParser.getStatus("fake invoice code");
        assertTrue(cjParser.checkComplete(list.get(0)));

    }


}