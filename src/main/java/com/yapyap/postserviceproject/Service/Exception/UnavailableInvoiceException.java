package com.yapyap.postserviceproject.Service.Exception;

public class UnavailableInvoiceException extends RuntimeException {
    public UnavailableInvoiceException(String message){
        super(message);
    }
}
