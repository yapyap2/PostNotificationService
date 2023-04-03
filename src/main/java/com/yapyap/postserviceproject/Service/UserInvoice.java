package com.yapyap.postserviceproject.Service;

import com.yapyap.postserviceproject.Carrier;

public class UserInvoice {

    String email;

    String invoiceNumber;

    Carrier carrier;

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
}
