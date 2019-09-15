package com.upgrad.FoodOrderingApp.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "COUPON")
@NamedQueries(
        {
                @NamedQuery(name = "getCouponUuid", query = "select u from CouponEntity u where u.uuid = :uuid"),
                @NamedQuery(name = "getCouponByName", query = "select u from CouponEntity u where u.coupon_name = :coupon_name"),
                @NamedQuery(name = "getPercent", query = "select u from CouponEntity u where u.percent = :percent")
        }
)
public class CouponEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer coupon_id;

    @Column(name = "uuid", unique = true)
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "coupon_name")
    @Size(max = 30)
    private String coupon_name;

    @Column(name = "percent")
    @NotNull
    @Size(max = 30)
    private Integer percent;


    public Integer getCoupon_id() {return coupon_id; }
    public void setCoupon_id(Integer coupon_id){this.coupon_id = coupon_id;}

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCoupon_name() {
        return coupon_name;
    }
    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public Integer getPercent() {return percent; }
    public void setPercent(Integer percent){this.percent = percent;}

    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(this, obj).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this).hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
