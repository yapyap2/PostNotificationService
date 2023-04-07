package com.yapyap.postserviceproject.Service.Exception;

public class DuplicatedInvoiceException extends RuntimeException{
    public DuplicatedInvoiceException(String message){
        super(message);
    }
}
