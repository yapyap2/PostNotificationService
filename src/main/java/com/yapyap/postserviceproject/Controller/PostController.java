package com.yapyap.postserviceproject.Controller;

import com.yapyap.postserviceproject.Service.Exception.DuplicatedInvoiceException;
import com.yapyap.postserviceproject.Service.Exception.UnavailableInvoiceException;
import com.yapyap.postserviceproject.Service.SearchingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final SearchingService searchingService;

    @PostMapping("/test")
    public String test(@RequestBody KakaoRequest  request){

        String invoiceCode = request.getUserRequest().getUtterance();
        String userId = request.getUserRequest().getUser().getProperties().getAppUserId();

        try {
            searchingService.addInvoiceNumber(invoiceCode, userId,  1);
        } catch (UnavailableInvoiceException e){
            return "UnavailableInvoiceException";
        } catch (DuplicatedInvoiceException e){
            return "DuplicatedInvoiceException";
        }
        return "invoice add success";
    }

    @PostMapping("/echo")
    public String echo(@RequestBody String test){
        System.out.println(test);
        return test;
    }

}
