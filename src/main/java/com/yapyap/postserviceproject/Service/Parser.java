package com.yapyap.postserviceproject.Service;

import com.yapyap.postserviceproject.Service.documentGetter.DocumentGetter;
import com.yapyap.postserviceproject.Status;

import java.util.List;

public interface Parser {
    public List<Status> getStatus(String query);

    public void setDocumentGetter(DocumentGetter documentGetter);
}
