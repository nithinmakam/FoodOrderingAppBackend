package com.upgrad.FoodOrderingApp.service.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.ZonedDateTime;


@Entity
@Table(name = "customer_auth")
@NamedQueries({
        @NamedQuery(name = "customerAuthTokenByAccessToken", query = "select ut from CustomerAuthEntity ut where ut.access_token = :access_token "),
        @NamedQuery(name="customerAuthTokenByUuid",query="select ut from CustomerAuthEntity ut where ut.uuid = :uuid")
})
public class CustomerAuthEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(name = "uuid" ,unique = true)
    @NotNull
    @Size(max = 200)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CustomerEntity customer_id;

    @Column(name = "access_token")
    @NotNull
    @Size(max = 500)
    private String access_token;

    @Column(name = "login_at")
    @NotNull
    private ZonedDateTime login_at;

    @Column(name = "logout_at")
    private ZonedDateTime logout_at;

    @Column(name = "expires_at")
    @NotNull
    private ZonedDateTime expires_at;

    public BigInteger getId() {
        return id;
    }
    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public CustomerEntity getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(CustomerEntity customer_id) {
        this.customer_id = customer_id;
    }

    public String getAccess_token() {
        return access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public ZonedDateTime getLogin_at() {
        return login_at;
    }
    public void setLogin_at(ZonedDateTime login_at) {
        this.login_at = login_at;
    }

    public ZonedDateTime getLogout_at() {
        return logout_at;
    }
    public void setLogout_at(ZonedDateTime logout_at) {
        this.logout_at = logout_at;
    }

    public ZonedDateTime getExpires_at() {
        return expires_at;
    }
    public void setExpires_at(ZonedDateTime expires_at) {
        this.expires_at = expires_at;
    }



}
