package com.yapyap.postserviceproject.Service;

import com.yapyap.postserviceproject.Carrier;
import com.yapyap.postserviceproject.InvoiceNumber;
import com.yapyap.postserviceproject.Service.documentGetter.ApiDocumentGetter;
import com.yapyap.postserviceproject.Service.documentGetter.LocalDocumentGetter;
import com.yapyap.postserviceproject.Service.documentGetter.PostDocumentGetter;
import com.yapyap.postserviceproject.Service.parser.CjParser;
import com.yapyap.postserviceproject.Service.parser.Parser;
import com.yapyap.postserviceproject.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/testApplicationContext.xml")
public class SearchingServiceTest {

    @Autowired
    SearchingService searchingService;

    @Autowired
    ApplicationContext context;
    @Before
    public void before(){
        searchingService.setDeleteMode(true);
        searchingService.clearList();
        searchingService.getParserList().forEach(parser -> parser.setDocumentGetter(new LocalDocumentGetter(parser.getCarrier() + "Service")));
    }

    private void prepareOnline(){
        List<Parser> list = searchingService.getParserList();
        for (Parser parser : list){
            if(parser.getCarrier().equals("POST")){
                parser.setDocumentGetter(new PostDocumentGetter());
            }
            parser.setDocumentGetter(new ApiDocumentGetter());
        }
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

        searchingService.searching();
    }

    @Test
    public void unavailableInvoiceTest() throws InterruptedException {

        prepareOnline();

        for(int i = 1; i <= Carrier.carrierCount(); i++){
            try {
                searchingService.addInvoiceNumber("fake Invoice Number" + Integer.toString(i), "email", i);
            } catch (RuntimeException e){
                e.printStackTrace();
                assertTrue(e.getMessage().startsWith("unavailable invoice code"));
            }
        }
    }

    @Test
    @DirtiesContext
    public void statusUpdateTestInLocal() throws InterruptedException {
        searchingService.setDeleteMode(false);

        for(int i = 1; i <= Carrier.carrierCount(); i++){

            String fakeInvoiceCode = "fake invoice code" + Integer.toString(i);
            searchingService.addInvoiceNumber(fakeInvoiceCode, "email", i);
            searchingService.searching();

            List<Status> list1 = new ArrayList<>();
            list1.addAll(searchingService.getInvoiceMap().get(fakeInvoiceCode).getStatuses());

            searchingService.searching();


            List<Status> list2 = new ArrayList<>();
            list2.addAll(searchingService.getInvoiceMap().get(fakeInvoiceCode).getStatuses());

            assertThat(list2.size(), is(list1.size() + 1));
            assertThat(list2.get(1), is(list1.get(0)));
        }

    }

    @Test
    public void completedInvoiceTest() {

        for(int i = 1; i <= Carrier.carrierCount(); i++){
            String fakeInvoiceNumber = "fake invoice Number" + Integer.toString(i);

            searchingService.addInvoiceNumber(fakeInvoiceNumber, "email", i);

            searchingService.searching();

            assertThat(searchingService.getInvoiceList().size(), is(1));

            searchingService.searching();

            assertThat(searchingService.getInvoiceList().size(), is(0));
        }
    }

    @Test(expected = RuntimeException.class)
    public void duplicateInvoiceCodeTest(){

        searchingService.addInvoiceNumber(InvoiceNumber.CJ_INVOICE_NUM, "www", Carrier.CJ.intValue());

        try {
            searchingService.addInvoiceNumber(InvoiceNumber.CJ_INVOICE_NUM, "www2", Carrier.CJ.intValue());
        } catch (RuntimeException e){
            e.printStackTrace();
            throw e;
        }
    }

}
