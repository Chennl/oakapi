package com.oak.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Chennl on 6/23/2017.
 */
@Entity
@Table(name="cm_customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="outlet_no")
    private String customerId;
    @Column(name="company_code")
    private String companyCode;
    @Column(name="name_e")
    private String customerName;
    @Column(name="address_e1")
    private String address;

    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCompanyCode() {
        return companyCode;
    }
    public void setCompanyCode(String address) {
        this.companyCode = companyCode;
    }
}
