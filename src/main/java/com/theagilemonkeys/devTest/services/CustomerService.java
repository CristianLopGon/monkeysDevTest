package com.theagilemonkeys.devTest.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.theagilemonkeys.devTest.beans.Customer;

@Service
public interface CustomerService {

	public List<Customer> getCustomers();

	public Customer getCustomerInfo(Long id);

	public Integer updateCustomer(Customer cus);

	public Integer deleteCustomer(Long id);

	void createCustomers(MultipartFile file);
}
