package com.yapyap.postserviceproject;

import com.yapyap.postserviceproject.Service.parser.CjParser;
import com.yapyap.postserviceproject.Service.parser.LogenParser;
import com.yapyap.postserviceproject.Service.parser.PostParser;

public class InvoiceNumber {
    public static String getInvoiceCode(Class clazz){
        if(clazz == CjParser.class) return "363818621704";
        if(clazz == PostParser.class) return "6865257294432";
        if(clazz == LogenParser.class) return "34788507110";

        else throw new RuntimeException("unknown parser class. can't found invoice code");
    }

    public static final String CJ_INVOICE_NUM = "363818621704";

    public  final String POST_INVOICE_NUM = "6865257294432";

    public final String LOGEN_INVOICE_NUM  = "34788507110";

}
