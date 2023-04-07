package com.yapyap.postserviceproject.Service;

import com.yapyap.postserviceproject.Carrier;
import com.yapyap.postserviceproject.Status;

import java.util.ArrayList;
import java.util.List;

public class UserInvoice {

    String email;

    String invoiceNumber;

    Carrier carrier;

    List<Status> statuses = new ArrayList<>();

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(int value) {
        this.carrier = Carrier.valueOf(value);
    }

    public List<Status> getStatuses() {
        return statuses;
    }


    public void updateStatus(List<Status> list){
        statuses.addAll(0, list);
    }

    @Override
    public String toString() {
       return "{ invoiceCode : " + invoiceNumber + " userId : " + email +" }";
    }
}
