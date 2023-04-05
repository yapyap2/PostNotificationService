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


        if(!parserMap.get(Carrier.valueOf(carrier)).verifyInvoiceCode(invoice)){
            throw new RuntimeException("unavailable invoice code");
        }


        UserInvoice user = new UserInvoice();
        user.setInvoiceNumber(invoice);
        user.setEmail(email);
        user.setCarrier(carrier);

        invoiceList.add(user);
    }

    @Scheduled(fixedDelay = 1000)
    public void searching(){

        List<UserInvoice> removeList = new ArrayList<>();

        for(UserInvoice invoice : invoiceList){
            Parser parser = parserMap.get(invoice.getCarrier());
            List<Status> statusList = null;
            try{
                statusList = parser.getStatus(invoice.getInvoiceNumber());
            } catch (RuntimeException e){
                continue;
            }
            if(invoice.getStatuses().size() < statusList.size()){  //배송상황 변경사항 존재하는 경우
                invoice.updateStatus(statusList.subList(0, statusList.size()- invoice.getStatuses().size()));

                if(parser.checkComplete(statusList.get(0))){ //배송 완료된 경우
                    removeList.add(invoice);
                }
            }
        }
        invoiceList.removeAll(removeList);
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
