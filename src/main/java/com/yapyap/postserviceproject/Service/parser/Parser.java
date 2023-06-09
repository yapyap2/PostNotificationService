package com.yapyap.postserviceproject.Service.parser;

import com.yapyap.postserviceproject.Service.documentGetter.DocumentGetter;
import com.yapyap.postserviceproject.Status;

import java.util.List;

public interface Parser {
    public List<Status> getStatus(String query);

    public void setDocumentGetter(DocumentGetter documentGetter);

    public boolean verifyInvoiceCode(String invoice);

    public boolean checkComplete(Status status);

    public String getCarrier();
}
