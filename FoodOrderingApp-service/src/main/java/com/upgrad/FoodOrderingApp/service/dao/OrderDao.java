package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<CouponEntity> getCouponByName(String coupon_name) {
        try {
            return (List<CouponEntity>) entityManager.createNamedQuery("getCouponByName", CustomerEntity.class).setParameter("coupon_name", coupon_name).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
