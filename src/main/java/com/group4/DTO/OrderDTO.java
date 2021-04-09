package com.group4.DTO;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;


public class OrderDTO {
	
    private Integer orderId;
    
    private String customerName;
    
    private String phone;
    
    private String email;
    
    private String facebook;
    
    @Temporal(TemporalType.TIMESTAMP)
	private Date datetime;
    
    private String address;

    private String total;
    
    private String status;
    
    private Integer paymentId;
	
    
	public OrderDTO(Integer orderId, String customerName, String phone, String email, String facebook,
			Date datetime, String address, String total, String status, Integer paymentId) {
		super();
		this.orderId = orderId;
		this.customerName = customerName;
		this.phone = phone;
		this.email = email;
		this.facebook = facebook;
		this.datetime = datetime;
		this.address = address;
		this.total = total;
		this.status = status;
		this.paymentId = paymentId;
	}


	public Integer getOrderId() {
		return orderId;
	}


	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFacebook() {
		return facebook;
	}


	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}


	public Date getDatetime() {
		return datetime;
	}


	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getTotal() {
		return total;
	}


	public void setTotal(String total) {
		this.total = total;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Integer getPaymentId() {
		return paymentId;
	}


	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
}
