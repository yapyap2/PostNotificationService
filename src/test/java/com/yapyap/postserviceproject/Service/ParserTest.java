package com.yapyap.postserviceproject.Service;

import com.yapyap.postserviceproject.InvoiceNumber;
import com.yapyap.postserviceproject.Service.documentGetter.ApiDocumentGetter;
import com.yapyap.postserviceproject.Service.documentGetter.DocumentGetter;
import com.yapyap.postserviceproject.Service.documentGetter.LocalDocumentGetter;
import com.yapyap.postserviceproject.Service.documentGetter.PostDocumentGetter;
import com.yapyap.postserviceproject.Service.parser.CjParser;
import com.yapyap.postserviceproject.Service.parser.LogenParser;
import com.yapyap.postserviceproject.Service.parser.Parser;
import com.yapyap.postserviceproject.Service.parser.PostParser;
import com.yapyap.postserviceproject.Status;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ParserTest {

    List<Parser> parserList = new ArrayList<>(Arrays.asList(new CjParser(), new PostParser(), new LogenParser()));

    DocumentGetter apiDocumentGetter = new ApiDocumentGetter();

    @Before
    public void beforeTest(){ //localDocumentGetter의 생성자로 로컬 html파일 경로를 제공함. 단, CJ대한 통운의 경우 'CJ'만 전달하기에 Service Test 에서는 'Service' 를 붙여줘야 함.
        parserList.forEach(parser -> parser.setDocumentGetter(new LocalDocumentGetter(parser.getCarrier())));
    }

    private void prepareOnline(){
        for (Parser parser : parserList) {
            if(parser.getClass() == PostParser.class){
                parser.setDocumentGetter(new PostDocumentGetter());
            }
            else {
                parser.setDocumentGetter(apiDocumentGetter);
            }
        }
    }

    @Test
    public void simpleParsingUsingApiTest(){
        prepareOnline();

        for(Parser parser : parserList){
            List<Status> list = parser.getStatus(InvoiceNumber.getInvoiceCode(parser.getClass()));

            list.forEach(item -> System.out.println(item));
            System.out.println("\n");
        }

    }

    @Test
    public void statusUpdateTest(){

        for(Parser parser : parserList){
            List<Status> list1 = parser.getStatus("fake Invoice Number");
            List<Status> list2 = parser.getStatus("fake Invoice Number");

            assertThat(list2.size(), is(list1.size() + 1 ));
            assertThat(list2.get(1).toString(), is(list1.get(0).toString()));
        }

    }

    @Test()
    public void verifyInvoiceCodeTest(){
        prepareOnline();

        for(Parser parser : parserList){
            assertTrue(parser.verifyInvoiceCode(InvoiceNumber.getInvoiceCode(parser.getClass())));
            assertFalse(parser.verifyInvoiceCode("fake invoice code"));
        }

    }

    @Test
    public void checkCompleteTest(){

        for(Parser parser : parserList){

            List<Status> list = parser.getStatus("fake invoice code");
            assertFalse(parser.checkComplete(list.get(0)));

            list = parser.getStatus("fake invoice code");
            assertTrue(parser.checkComplete(list.get(0)));
        }

    }


}