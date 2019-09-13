package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.UpdateCustomerException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdatePasswordBusinessService {

//    @Autowired
//    private CustomerDao customerDao;
//
//    @Autowired
//    private PasswordCryptographyProvider passwordCryptographyProvider = new PasswordCryptographyProvider();
//
//    @Autowired
//    private AuthTokenValidityService authTokenValidityService;
//
//    @Transactional(propagation = Propagation.REQUIRED)
//    public CustomerEntity updatePassword(String authorization) throws UpdateCustomerException, AuthorizationFailedException {
//
//        final CustomerAuthEntity customerAuthEntity = customerDao.getCustomerAuthToken(authorization);
//
//        if (customerAuthEntity == null) {
//            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
//        }else if (authTokenValidityService.isLoggedOut(customerAuthEntity)){
//            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
//        }else if (authTokenValidityService.isExpired(customerAuthEntity)){
//            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access the endpoint");
//        }else if (!StringUtils.isNotEmpty(updatePassword(authorization).getPassword())|| !StringUtils.isNotEmpty(updatePassword(authorization).getNewPassword())) {
//            throw new UpdateCustomerException("UCR-003", "No field should be empty");
//        }else if (updatePassword(authorization).getNewPassword() != "(?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z]).{8,16})" ){
//                throw new AuthorizationFailedException("UCR-001", "Weak password!");
//        }else{
//            updatePassword(authorization).setPassword();
//        }
//
}
