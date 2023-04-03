package com.yapyap.postserviceproject.Service;

import com.yapyap.postserviceproject.Carrier;
import com.yapyap.postserviceproject.Service.SearchingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Before
    public void before(){
        searchingService.clearList();
    }


    @Test
    public void invoiceAddTest(){

        UserInvoice invoice = new UserInvoice();
        invoice.setInvoiceNumber("invoice Number");
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


        Thread.sleep(10000);
    }


}
