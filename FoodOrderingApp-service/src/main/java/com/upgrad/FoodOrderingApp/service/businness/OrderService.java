package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.OrderDao;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    private AuthTokenValidityService authTokenValidityService;

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity getCoupon(final String authorization, final String coupon_name)
            throws AuthorizationFailedException, CouponNotFoundException {
        CustomerAuthEntity customerAuthEntity = customerDao.getCustomerAuthToken(authorization);
        CouponEntity couponEntity  = (CouponEntity) orderDao.getCouponByName(coupon_name);

        if (customerAuthEntity == null) {
            throw new AuthorizationFailedException("ATHR-001", "Customer is not Logged in");
        } else if (authTokenValidityService.isLoggedOut(customerAuthEntity)) {
            throw new AuthorizationFailedException("ATHR-002", "Customer is logged out. Log in again to access this endpoint.");
        } else if (authTokenValidityService.isExpired(customerAuthEntity)) {
            throw new AuthorizationFailedException("ATHR-003", "Your session is expired. Log in again to access the endpoint");
        } else if (!couponEntity.equals(coupon_name)){
            throw new CouponNotFoundException("CPF-001", "No coupon by this name");
        }  else if (coupon_name == null){
            throw new CouponNotFoundException("CPF-002","Coupon name field should not be empty");
        }
        return customerAuthEntity;
    }

}
