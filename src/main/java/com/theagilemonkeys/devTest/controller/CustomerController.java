package com.theagilemonkeys.devTest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.theagilemonkeys.devTest.beans.Customer;
import com.theagilemonkeys.devTest.services.impl.CustomerServiceImpl;
import com.theagilemonkeys.devTest.utils.CSVUtils;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {
	@Autowired
	CustomerServiceImpl service;

	@RequestMapping(value = "/listCustomers", method = RequestMethod.GET)
	@ApiOperation(value = "Gets the list of customers in DB", response = List.class)
	public List<Customer> getCustomers() {
		return service.getCustomers();
	}

	@RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Gets a user from DB for a specific Id", response = Customer.class)
	public Customer getCustomer(@PathVariable Long id) {
		return service.getCustomerInfo(id);
	}

	@RequestMapping(value = "/createCustomers", method = RequestMethod.POST)
	@ApiOperation(value = "Creates customers (Id, Name, Surname, Url) in DB from CSV file", response = ResponseEntity.class)
	public ResponseEntity<?> createCustomers(@RequestBody MultipartFile file) {

		if (CSVUtils.hasCSVFormat(file)) {
			service.createCustomers(file);
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a CSV File");
	}

	@RequestMapping(value = "/customer/{id}", method = RequestMethod.PUT)
	@ApiOperation(value = "Updates a user info in DB", response = ResponseEntity.class)
	public ResponseEntity<?> updateCustomer(@PathVariable Long id, @RequestBody Customer cus) {
		if (id == null) {
			return ResponseEntity.badRequest().body("Id must be NOT Null");
		}

		if (cus.getId() != null && cus.getId() != id) {
			return ResponseEntity.badRequest().body("Id param must be equals to body Id");
		}
		cus.setId(id); // Same id from request

		service.updateCustomer(cus);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Deletes a user in DB", response = ResponseEntity.class)
	public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
		service.deleteCustomer(id);
		return ResponseEntity.ok().build();
	}
}
