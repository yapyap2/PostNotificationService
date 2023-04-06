package com.yapyap.postserviceproject.Service;

import com.yapyap.postserviceproject.Carrier;
import com.yapyap.postserviceproject.InvoiceNumber;
import com.yapyap.postserviceproject.Service.documentGetter.LocalDocumentGetter;
import com.yapyap.postserviceproject.Service.parser.CjParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class SearchingServiceTest {

    @Autowired
    SearchingService searchingService;

    @Autowired
    ApplicationContext context;
    @Before
    public void before(){
        searchingService.clearList();
    }


    @Test
    public void invoiceAddTest(){

        UserInvoice invoice = new UserInvoice();
        invoice.setInvoiceNumber(InvoiceNumber.CJ_INVOICE_NUM);
        invoice.setEmail("wonwoo42@gmail.com");
        invoice.setCarrier(1);

        searchingService.addInvoiceNumber(invoice.getInvoiceNumber(), invoice.getEmail(), invoice.getCarrier().intValue());

        List<UserInvoice> list = searchingService.getInvoiceList();


        assertThat(list.size(), is(1));
        assertThat(list.get(0).getInvoiceNumber(), is(invoice.getInvoiceNumber()));
        assertThat(list.get(0).getEmail(), is(invoice.getEmail()));
        assertThat(list.get(0).getCarrier(), is(Carrier.CJ));

    }

    @Test
    public void searchingTest() throws InterruptedException {
        searchingService.addInvoiceNumber("363818621704", "wonwoo42@gmail.com", 1);


        Thread.sleep(1000);
    }

    @Test(expected = RuntimeException.class)
    public void unavailableInvoiceTest() throws InterruptedException {

        try{
            searchingService.addInvoiceNumber("fake Invoice Number", "wonwoo42@gmail.com", 1);
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    @DirtiesContext
    public void statusUpdateTestInLocal() throws InterruptedException {

        searchingService.setDeleteMode(false);

        LocalDocumentGetter documentGetter = new LocalDocumentGetter();
        documentGetter.setLocalResource("/CjHtml1.html");

        context.getBean("cjParser", CjParser.class).setDocumentGetter(documentGetter);
        searchingService.addInvoiceNumber("363818621704", "wonwoo42@gmail.com", 1);

        Thread.sleep(1000);
        assertThat(searchingService.getInvoiceList().get(0).getStatuses().size(), is(7));

        documentGetter.setLocalResource("/CjHtml2.html");

        Thread.sleep(1000);
        assertThat(searchingService.getInvoiceList().get(0).getStatuses().size(), is(8));

    }

    @Test
    public void completedInvoiceTest() throws InterruptedException {

        LocalDocumentGetter localDocumentGetter = new LocalDocumentGetter();
        localDocumentGetter.setLocalResource("/CjHtml1.html");

        context.getBean("cjParser", CjParser.class).setDocumentGetter(localDocumentGetter);

        searchingService.addInvoiceNumber("fake invoice code", "wonwoo42@gmail.com", Carrier.CJ.intValue());

        Thread.sleep(1000);

        assertThat(searchingService.getInvoiceList().size(), is(1));

        localDocumentGetter.setLocalResource("/CjHtml2.html");

        Thread.sleep(1000);

        assertThat(searchingService.getInvoiceList().size(), is(0));
    }

    @DirtiesContext
    @Test(expected = RuntimeException.class)
    public void duplicateInvoiceCodeTest(){

        searchingService.setDeleteMode(false);

        searchingService.addInvoiceNumber(InvoiceNumber.CJ_INVOICE_NUM, "www", Carrier.CJ.intValue());

        try {
            searchingService.addInvoiceNumber(InvoiceNumber.CJ_INVOICE_NUM, "www2", Carrier.CJ.intValue());
        } catch (RuntimeException e){
            e.printStackTrace();
            throw e;
        }
    }

}
