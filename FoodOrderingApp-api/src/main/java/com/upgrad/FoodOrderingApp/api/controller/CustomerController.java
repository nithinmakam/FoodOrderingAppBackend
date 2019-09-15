package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.*;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@CrossOrigin
@RestController
@RequestMapping("/")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    //1. signup endpoint
    @RequestMapping(method = RequestMethod.POST, path = "/customer/signup", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SignupCustomerResponse> signup(final SignupCustomerRequest signupCustomerRequest) throws SignUpRestrictedException {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setUuid(UUID.randomUUID().toString());
        customerEntity.setFirstName(signupCustomerRequest.getFirstName());
        customerEntity.setLastName(signupCustomerRequest.getLastName());
        customerEntity.setEmail(signupCustomerRequest.getEmailAddress());
        customerEntity.setContactNumber(signupCustomerRequest.getContactNumber());
        customerEntity.setPassword(signupCustomerRequest.getPassword());
        customerEntity.setSalt("abc1234");
        final CustomerEntity createdCustomerEntity;
        createdCustomerEntity = customerService.saveCustomer(customerEntity);
        SignupCustomerResponse userResponse = new SignupCustomerResponse().id(createdCustomerEntity.getUuid()).status("CUSTOMER SUCCESSFULLY REGISTERED");
        return new ResponseEntity<SignupCustomerResponse>(userResponse, HttpStatus.CREATED);
    }

    //2. login enpoint
    @RequestMapping(method = RequestMethod.POST, path = "/customer/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginResponse> login(@RequestHeader("authorization") final String authorization) throws AuthenticationFailedException {

//        byte[] decode = Base64.getDecoder().decode(authorization.split("Basic ")[1]);
//        String decodeText = new String(decode);
//        String[] decodeArray = decodeText.split(":");
        String[] decodeArray = customerService.validateAuthenticationFormat(authorization);
        CustomerAuthEntity customerAuthEntity = customerService.authenticate(decodeArray[0], decodeArray[1]);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("LOGGED IN SUCCESSFULLY");
        loginResponse.setId(customerAuthEntity.getCustomer().getUuid());
        loginResponse.setFirstName(customerAuthEntity.getCustomer().getFirstName());
        loginResponse.setLastName(customerAuthEntity.getCustomer().getLastName());
        loginResponse.setContactNumber(customerAuthEntity.getCustomer().getContactNumber());
        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token", customerAuthEntity.getAccessToken());

        return new ResponseEntity<LoginResponse>(loginResponse, headers, HttpStatus.OK);
    }

    //3. logout endpoint
    @RequestMapping(method = RequestMethod.POST, path = "/customer/logout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LogoutResponse> logout(@RequestHeader("authorization") final String authorization) throws AuthorizationFailedException {

        String[] bearerToken = authorization.split("Bearer");
        CustomerAuthEntity loggedoutUserEntity = customerService.logout(bearerToken[0]);
        LogoutResponse logoutResponse = new LogoutResponse();
        logoutResponse.setMessage("LOGGED OUT SUCCESSFULLY");
        logoutResponse.setId(loggedoutUserEntity.getUuid());
        return new ResponseEntity<LogoutResponse>(logoutResponse, HttpStatus.OK);
    }

    //4. update endpoint
    @RequestMapping(method = PUT, path = "/customer", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdateCustomerRequest> updateCustomer(@RequestHeader("authorization") String authorization, final UpdateCustomerRequest updateCustomer)
            throws UpdateCustomerException, AuthorizationFailedException {
        String[] bearerToken = authorization.split("Bearer ");
        CustomerEntity customer = customerService.getCustomer(bearerToken[1]);
        customer.setFirstName(updateCustomer.getFirstName());
        customer.setLastName(updateCustomer.getLastName());
        CustomerEntity updatedCustomerEntity = customerService.updateCustomer(customer);
        UpdateCustomerResponse updateCustomerResponse = new UpdateCustomerResponse();
        updateCustomerResponse.setId(updatedCustomerEntity.getUuid());
        updateCustomerResponse.setFirstName(updatedCustomerEntity.getFirstName());
        updateCustomerResponse.setLastName(updatedCustomerEntity.getLastName());
        updateCustomerResponse.setStatus("CUSTOMER DETAILS UPDATED SUCCESSFULLY");
        return new ResponseEntity(updateCustomerResponse, HttpStatus.OK);
    }

    //5.change password endpoint
    @RequestMapping(method = PUT, path = "/customer/password", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UpdatePasswordRequest> updateCustomerPassword(@RequestHeader("authorization") String authorization, String old_Password, String new_Password)
            throws UpdateCustomerException, AuthorizationFailedException {

        if (old_Password == null || new_Password == null)
            throw new UpdateCustomerException("UCR-003", "No field should be empty");
        String[] bearerToken = authorization.split("Bearer ");
        CustomerEntity customer = customerService.getCustomer(bearerToken[1]);
        CustomerEntity updatePasswordEntity = customerService.updateCustomerPassword(old_Password, new_Password, customer);
        UpdatePasswordResponse updatePasswordResponse = new UpdatePasswordResponse();
        updatePasswordResponse.setId(updatePasswordEntity.getUuid());
        updatePasswordResponse.setStatus("CUSTOMER DETAILS UPDATED SUCCESSFULLY");
        return new ResponseEntity(updatePasswordResponse, HttpStatus.OK);
    }
}
