package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerAuthEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CustomerEntity createCustomer(CustomerEntity customerEntity){
        entityManager.persist(customerEntity);
        return customerEntity;
    }


    public CustomerEntity getCustomerByContactNumber(final String contactnumber){
        try{
            return entityManager.createNamedQuery("customerByContactNumber",CustomerEntity.class).setParameter("contactnumber",contactnumber).getSingleResult();
        } catch (NoResultException nre){
            return null;
        }
    }
}
