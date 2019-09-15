package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.controller.ext.ResponseBuilder;
import com.upgrad.FoodOrderingApp.api.model.CouponDetailsResponse;
import com.upgrad.FoodOrderingApp.service.businness.OrderService;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET, path = "/order/coupon/{coupon_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CouponDetailsResponse> getCoupon(@RequestHeader final String authorization, @PathVariable final String couponName)
            throws AuthorizationFailedException, CouponNotFoundException {

        String[] bearerToken = authorization.split("Bearer ");
        final ResponseEntity<CouponDetailsResponse> couponDetailsResponseResponseEntity = new ResponseEntity<CouponDetailsResponse>
                ((MultiValueMap<String, String>) orderService.getCoupon(bearerToken[1], couponName), HttpStatus.OK);
        return couponDetailsResponseResponseEntity;

    }

    @RequestMapping(method = RequestMethod.GET, path = "/order/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CouponDetailsResponse> getPastOrders(@RequestHeader final String authorization)
            throws AuthorizationFailedException {
        return null;

        //to be completed

    }

}
