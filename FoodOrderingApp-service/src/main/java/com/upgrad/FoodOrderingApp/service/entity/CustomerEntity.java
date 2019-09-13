package com.upgrad.FoodOrderingApp.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "customer")
@NamedQueries(
        {
                @NamedQuery(name = "customerByUuid", query = "select u from CustomerEntity u where u.uuid = :uuid"),
                @NamedQuery(name = "customerByContactNumber", query = "select u from CustomerEntity u where u.contactnumber = :contactnumber"),
                @NamedQuery(name = "customerByPassword", query = "select u from CustomerEntity u where u.password = :password")
        }
)
public class CustomerEntity implements Serializable{

    @Id
    @Column(name = "id")
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customer_id;

    @Column(name = "uuid", unique = true)
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "firstname")
    @NotNull
    @Size(max = 30)
    private String firstName;

    @Column(name = "lastname")
    @Size(max = 30)
    private String lastName;

    @Column(name = "email")
    @Size(max = 50)
    private String email;

    @Column(name = "contactnumber" ,unique = true)
    @NotNull
    @Size(max = 30)
    private String contactnumber;

    @Column(name = "password")
    @NotNull
    @Size(max = 255)
    private String password;

//    @NotNull
//    @Size(max = 255)
//    private String newPassword;

    @Column(name = "salt")
    @NotNull
    @Size(max = 200)
    private String salt;

    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(this, obj).isEquals();
    }

    public Integer getCustomer_id() {return customer_id; }
    public void setCustomer_id(Integer customer_id){this.customer_id = customer_id;}

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactnumber;
    }
    public void setContactNumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }

//    public String getNewPassword(){return newPassword;}
//    public void setNewPassword(String newPassword) {
//        this.newPassword = newPassword;
//    }
}
