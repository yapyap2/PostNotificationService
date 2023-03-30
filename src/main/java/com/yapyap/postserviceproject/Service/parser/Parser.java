package com.yapyap.postserviceproject.Service.parser;

import com.yapyap.postserviceproject.Service.parser.documentGetter.DocumentGetter;
import com.yapyap.postserviceproject.Status;

import java.util.List;

public interface Parser {
    public List<Status> parsing(String query);

    public void setDocumentGetter(DocumentGetter documentGetter);
}
