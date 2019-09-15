package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.PaymentListResponse;
import com.upgrad.FoodOrderingApp.api.model.PaymentResponse;
import com.upgrad.FoodOrderingApp.service.businness.PaymentService;
import com.upgrad.FoodOrderingApp.service.entity.PaymentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
@CrossOrigin
public class PaymentController {
    @Autowired
    PaymentService paymentBusinessService;

    @RequestMapping(method = RequestMethod.GET, path = "/payment", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PaymentListResponse> payment(){
        PaymentListResponse paymentListResponse = new PaymentListResponse();
        List<PaymentEntity> payments = paymentBusinessService.getAllPaymentMethods();
        for(PaymentEntity p : payments){
          PaymentResponse payment= new PaymentResponse().id(UUID.fromString(p.getUuid())).paymentName(p.getPaymentName());
          paymentListResponse.addPaymentMethodsItem(payment);
        }
        return new ResponseEntity<PaymentListResponse>(paymentListResponse,HttpStatus.OK);

    }
}