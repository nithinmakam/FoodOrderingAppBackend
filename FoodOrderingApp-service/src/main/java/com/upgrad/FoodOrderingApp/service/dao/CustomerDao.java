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

    public CustomerEntity createCustomer(CustomerEntity customerEntity) {
        entityManager.persist(customerEntity);
        return customerEntity;
    }


    public CustomerEntity getCustomerByContactNumber(final String contactnumber) {
        try {
            return entityManager.createNamedQuery("customerByContactNumber", CustomerEntity.class).setParameter("contactnumber", contactnumber).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }


    public CustomerEntity getCustomerByPassword(final String password) {
        try {
            return entityManager.createNamedQuery("customerByPassword", CustomerEntity.class).setParameter("password", password).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public CustomerAuthEntity createAuthToken(final CustomerAuthEntity customerAuthEntity) {
        entityManager.persist(customerAuthEntity);
        return customerAuthEntity;
    }

    public void updateCustomer(final CustomerEntity customerEntity) {
        entityManager.merge(customerEntity);
    }

    public CustomerAuthEntity getUserAuthToken(final String access_token) {
        try {
            return entityManager.createNamedQuery("customerAuthTokenByAccessToken", CustomerAuthEntity.class).setParameter("access_token", access_token).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public void updateCustomerEntity(final CustomerAuthEntity customerAuthEntity) {
        entityManager.merge(customerAuthEntity);
    }

    public CustomerAuthEntity getCustomerLogoutTime(final String logout_at) {
        try {
            return entityManager.createNamedQuery("customerByLogoutAt", CustomerAuthEntity.class).setParameter("logout_at", logout_at).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public CustomerAuthEntity getCustomerSessionExpiresAt(final String expires_at) {
        try {
            return entityManager.createNamedQuery("customerByExpiresAt", CustomerAuthEntity.class).setParameter("expires_at", expires_at).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}