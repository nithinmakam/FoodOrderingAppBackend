package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.controller.provider.BearerAuthDecoder;
import com.upgrad.FoodOrderingApp.api.model.LoginResponse;
import com.upgrad.FoodOrderingApp.api.model.LogoutResponse;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerRequest;
import com.upgrad.FoodOrderingApp.api.model.SignupCustomerResponse;
import com.upgrad.FoodOrderingApp.service.businness.LoginAuthenticationBusinessService;
import com.upgrad.FoodOrderingApp.service.businness.LogOutBusinessService;
import com.upgrad.FoodOrderingApp.service.businness.SignUpBusinessService;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class CustomerController {


    @Autowired
    private SignUpBusinessService signupBusinessService;

    @Autowired
    private LoginAuthenticationBusinessService loginAuthenticationBusinessService;

    @Autowired
    private LogOutBusinessService logoutBusinessService;

    //1. signup endpoint
    @RequestMapping(method = RequestMethod.POST, path="/customer/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupCustomerResponse> signup(final SignupCustomerRequest signupCustomerRequest) throws SignUpRestrictedException
    {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setUuid(UUID.randomUUID().toString());
        customerEntity.setFirstName(signupCustomerRequest.getFirstName());
        customerEntity.setLastName(signupCustomerRequest.getLastName());
        customerEntity.setEmail(signupCustomerRequest.getEmailAddress());
        customerEntity.setContactNumber(signupCustomerRequest.getContactNumber());
        customerEntity.setPassword(signupCustomerRequest.getPassword());
        customerEntity.setSalt("abc1234");
        final CustomerEntity createdCustomerEntity;
        createdCustomerEntity=signupBusinessService.signup(customerEntity);
        SignupCustomerResponse userResponse = new SignupCustomerResponse().id(createdCustomerEntity.getUuid()).status("CUSTOMER SUCCESSFULLY REGISTERED");
        return new ResponseEntity<SignupCustomerResponse>(userResponse, HttpStatus.CREATED);
    }

    //2. login enpoint
    @RequestMapping(method = RequestMethod.POST, path = "/customer/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestHeader("authorization") final String authorization) throws AuthenticationFailedException{
        byte[] decode = Base64.getDecoder().decode(authorization.split("Basic")[1]);
        String decodeText = new String(decode);
        String[] decodeArray = decodeText.split(":");

        CustomerAuthEntity customerAuthEntity = loginAuthenticationBusinessService.authenticate(decodeArray[0], decodeArray[1]);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("LOGGED IN SUCCESSFULLY");
        loginResponse.setId(customerAuthEntity.getCustomer_id().getUuid());
        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token", customerAuthEntity.getAccess_token());

        return new ResponseEntity<LoginResponse>(loginResponse,headers,HttpStatus.OK);
    }

    //3. logout endpoint
    @RequestMapping(method = RequestMethod.POST, path = "/customer/logout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LogoutResponse> logout(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException{

        final BearerAuthDecoder authDecoder = new BearerAuthDecoder(authorization);
        logoutBusinessService.logout(authDecoder.getAccessToken());
        CustomerEntity loggedoutUserEntity = logoutBusinessService.logout(authorization);
        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.setMessage("LOGGED OUT SUCCESSFULLY");
        logoutResponse.setId(loggedoutUserEntity.getUuid());
        return new ResponseEntity<LogoutResponse>(logoutResponse,HttpStatus.OK);

    }

    //4. update endpoint




}
