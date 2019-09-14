package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.exception.SignUpRestrictedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.regex.Pattern;

@Service
public class SignUpBusinessService {


    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }


    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private PasswordCryptographyProvider passwordCryptographyProvider = new PasswordCryptographyProvider();

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerEntity signup(CustomerEntity customerEntity) throws SignUpRestrictedException {

        String[] encryptedText = passwordCryptographyProvider.encrypt(customerEntity.getPassword());
        customerEntity.setSalt(encryptedText[0]);
        customerEntity.setPassword(encryptedText[1]);
        if (customerDao.getCustomerByContactNumber(customerEntity.getContactNumber()) != null) {
            throw new SignUpRestrictedException("SGR-001", "This contact number is already registered! Try another contact number.");
        } else if (isValid(customerEntity.getEmail()) == false)  {
            throw new SignUpRestrictedException("SGR-002", "Invalid email-id format!");
        } else if (customerEntity.getContactNumber() == "^$|[0-9]{10}") {
            throw new SignUpRestrictedException("SGR-003", "Invalid contact number");
        } else if (customerEntity.getPassword() == "(?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z]).{8,16})" ){
            throw new SignUpRestrictedException("SGR-004", "Weak password!");
        } else if (customerEntity.getFirstName()== null || customerEntity.getEmail()== null || customerEntity.getContactNumber()== null || customerEntity.getPassword() == null) {
            throw new SignUpRestrictedException("SGR-005", "Except last name all fields should be filled!");
        } else {
            return customerDao.createCustomer(customerEntity);
        }
    }


}
