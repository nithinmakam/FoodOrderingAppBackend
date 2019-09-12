package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.data.DateTimeProvider;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class LogOutBusinessService {

    @Autowired
    private CustomerDao customerDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity logout(final String authorizationToken) throws AuthorizationFailedException {

        CustomerAuthEntity customerAuthEntity = customerDao.getUserAuthToken(authorizationToken);

        if(customerAuthEntity == null){
            throw new AuthorizationFailedException("ATHR-001","Customer is not Logged in");
        }

        else if (isLoggedOut(customerAuthEntity)){
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
        }

        else if (isExpired(customerAuthEntity)){
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access the endpoint");
        }
        else{
            customerAuthEntity.setLogout_at(ZonedDateTime.now());
            customerDao.updateCustomerEntity(customerAuthEntity);
        }
        return customerAuthEntity.getCustomer_id();
    }

    private boolean isExpired(final CustomerAuthEntity customerAuthEntity) {
        final ZonedDateTime now = DateTimeProvider.currentProgramTime();
        return customerAuthEntity != null && (customerAuthEntity.getExpires_at().isBefore(now) || customerAuthEntity.getExpires_at().isEqual(now));
    }

    private boolean isLoggedOut(final CustomerAuthEntity customerAuthEntity) {
        return customerAuthEntity != null && customerAuthEntity.getLogout_at() != null;
    }
}
