package com.yapyap.postserviceproject;

import com.yapyap.postserviceproject.Service.parser.*;

public class InvoiceNumber {
    public static String getInvoiceCode(Class clazz){
        if(clazz == CjParser.class) return "363818621704";
        if(clazz == PostParser.class) return "6865257294432";
        if(clazz == LogenParser.class) return "34788507110";
        if(clazz == HanjinParser.class) return "452205032834";
        if(clazz == LotteParser.class) return "404969447013";

        else throw new RuntimeException("unknown parser class. can't found invoice code");
    }

    public static final String CJ_INVOICE_NUM = "363818621704";

    public  final String POST_INVOICE_NUM = "6865257294432";

    public final String LOGEN_INVOICE_NUM  = "34788507110";

    public final String LOTTE_INVOICE_NUM = "404969447013";

}
