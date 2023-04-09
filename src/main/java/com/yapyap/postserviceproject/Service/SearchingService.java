package com.yapyap.postserviceproject.Service;



import com.yapyap.postserviceproject.Carrier;
import com.yapyap.postserviceproject.Service.Exception.DuplicatedInvoiceException;
import com.yapyap.postserviceproject.Service.Exception.UnavailableInvoiceException;
import com.yapyap.postserviceproject.Service.parser.Parser;
import com.yapyap.postserviceproject.Status;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchingService {

    Map<Carrier, Parser> parserMap;
    HashMap<String, UserInvoice> invoiceMap = new HashMap<>();

    Boolean deleteMode = true;
    public void addInvoiceNumber(String invoice, String email, int carrier){

        if(invoiceMap.containsKey(invoice)){
            throw new DuplicatedInvoiceException("Duplicated invoice code : " + invoice);
        }

        if(!parserMap.get(Carrier.valueOf(carrier)).verifyInvoiceCode(invoice)){
            throw new UnavailableInvoiceException("unavailable invoice code : " + invoice);
        }


        UserInvoice user = new UserInvoice();
        user.setInvoiceNumber(invoice);
        user.setEmail(email);
        user.setCarrier(carrier);

        invoiceMap.put(invoice, user);
    }

    public void searching(){

        List<UserInvoice> removeList = new ArrayList<>();

        for(UserInvoice invoice : invoiceMap.values()){
            Parser parser = parserMap.get(invoice.getCarrier());
            List<Status> statusList = null;
            try{
                statusList = parser.getStatus(invoice.getInvoiceNumber());
            } catch (RuntimeException e){   // API 통신 오류, 예외 처리 작업 필요
                continue;
            }
            if(invoice.getStatuses().size() < statusList.size()){  //배송상황 변경사항 존재하는 경우
                invoice.updateStatus(statusList.subList(0, statusList.size()- invoice.getStatuses().size()));

                if(parser.checkComplete(statusList.get(0)) && deleteMode){ //배송 완료된 경우, deleteMode 가 True 여야만 삭제
                    removeList.add(invoice);
                    System.out.println("배송완료 : " + invoice.toString());
                }
            }
        }

        for(UserInvoice invoice : removeList){
            invoiceMap.remove(invoice.getInvoiceNumber());
        }
    }


    public void clearList(){
        invoiceMap.clear();
    }

    public void setParserMap(Map<Carrier, Parser> parserMap) {
        this.parserMap = parserMap;
    }

    public List<UserInvoice> getInvoiceList(){
        return new ArrayList<UserInvoice>(invoiceMap.values());
    }

    public void setDeleteMode(Boolean deleteMode) {
        this.deleteMode = deleteMode;
    }

    public List<Parser> getParserList(){
        return parserMap.values().stream().toList();
    }

    public HashMap<String, UserInvoice> getInvoiceMap() {
        return invoiceMap;
    }
}
