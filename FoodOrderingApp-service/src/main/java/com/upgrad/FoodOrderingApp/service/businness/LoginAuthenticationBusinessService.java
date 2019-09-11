package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthenticationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class LoginAuthenticationBusinessService {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PasswordCryptographyProvider cryptographyProvider;

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerAuthEntity authenticate(final String contactnumber, final String password) throws AuthenticationFailedException{

        CustomerEntity customerEntity = customerDao.getCustomerByContactNumber(contactnumber);
        CustomerEntity customerEntityPwd = customerDao.getCustomerByPassword(password);

        if (customerEntity == null){
            throw new AuthenticationFailedException("ATH-001", "This contact number has not been registered!");
        }

        else if (customerEntityPwd == null){
            throw new AuthenticationFailedException("ATH-002","Invalid Credentials");
        }

        final  String encryptedPassword = cryptographyProvider.encrypt(password, customerEntity.getSalt());
        if (encryptedPassword.equals(customerEntity.getPassword())){
            JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(encryptedPassword);
            CustomerAuthEntity customerAuthEntity = new CustomerAuthEntity();
            customerAuthEntity.setCustomer_id(customerEntity);

            final ZonedDateTime now = ZonedDateTime.now();
            final ZonedDateTime expiresAt = now.plusHours(8);
            customerAuthEntity.setAccess_token(jwtTokenProvider.generateToken(customerEntity.getUuid(), now, expiresAt));
            customerAuthEntity.setUuid(customerEntity.getUuid());
            customerAuthEntity.setLogin_at(now);
            customerAuthEntity.setExpires_at(expiresAt);
            customerDao.createAuthToken(customerAuthEntity);
            customerDao.updateUser(customerEntity);

            return customerAuthEntity;
        }

        else{
            throw new AuthenticationFailedException("ATH-003","Incorrect format of decoded customer name and password");
        }
    }
}
