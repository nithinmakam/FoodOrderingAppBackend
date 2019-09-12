package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.data.DateTimeProvider;
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
public class UpdateCustomerBusinessService {

    @Autowired
    private CustomerDao customerDao;


    @Autowired
    private AuthTokenValidityService authTokenValidityService;

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity updateCustomer(final String authorization) throws UpdateCustomerException,AuthorizationFailedException {

        final CustomerAuthEntity existingUser = customerDao.getCustomerAuthToken(authorization);
        CustomerEntity customerEntity = new CustomerEntity();

        if (existingUser == null) {
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in.");
        }else if (authTokenValidityService.isLoggedOut(existingUser)){
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
        }else if (authTokenValidityService.isExpired(existingUser)){
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access the endpoint");
        }else if (!StringUtils.isNotEmpty(updateCustomer(authorization).getFirstName())){
            throw new UpdateCustomerException("UCR-002", "First name field should not be empty");
        }
        else {
            customerEntity.setFirstName(updateCustomer(authorization).getFirstName());

            if (StringUtils.isNotEmpty(updateCustomer(authorization).getLastName())) {
                customerEntity.setLastName(updateCustomer(authorization).getLastName());
            }
            customerDao.updateCustomerEntity(existingUser);
            customerDao.updateCustomer(customerEntity);
        }

        return existingUser.getCustomer_id();
    }

}
