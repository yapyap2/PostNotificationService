package com.yapyap.postserviceproject.Service;



import com.yapyap.postserviceproject.Carrier;
import com.yapyap.postserviceproject.Status;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class SearchingService {

    Map<Carrier, Parser> parserMap;

    List<UserInvoice> invoiceList = new ArrayList<>();
    public void addInvoiceNumber(String invoice, String email, int carrier){

        UserInvoice user = new UserInvoice();
        user.setInvoiceNumber(invoice);
        user.setEmail(email);
        user.setCarrier(carrier);

        invoiceList.add(user);
    }

    @Scheduled(fixedDelay = 1000)
    public void searching(){

        for(UserInvoice invoice : invoiceList){
            Parser parser = parserMap.get(invoice.getCarrier());
            List<Status> list = parser.getStatus(invoice.getInvoiceNumber());

            list.forEach(status -> System.out.println(status));
        }

    }


    public void clearList(){
        invoiceList.clear();
    }

    public void setParserMap(Map<Carrier, Parser> parserMap) {
        this.parserMap = parserMap;
    }

    public List<UserInvoice> getInvoiceList(){
        return invoiceList;
    }
}
