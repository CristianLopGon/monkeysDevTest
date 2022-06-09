package com.theagilemonkeys.devTest.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.theagilemonkeys.devTest.beans.Customer;
import com.theagilemonkeys.devTest.mappers.CustomerMapper;
import com.theagilemonkeys.devTest.services.CustomerService;
import com.theagilemonkeys.devTest.utils.CSVUtils;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerMapper mapper;

	public List<Long> wrongRowsId = new ArrayList<>();

	public List<Customer> getCustomers() {
		/*
		 * Customer cus =
		 * CustomerBuilder.customerBuilder().withName("Critian").withSurname("Lopez").
		 * withId(1L) .withPhoto("ponerfotoaqui").build(); Customer cus2 =
		 * CustomerBuilder.customerBuilder().withName("Persona").withSurname("Pájaro").
		 * withId(2L) .withPhoto("ponerfotoaqui").build();
		 * 
		 * List list = new ArrayList(); list.add(cus); list.add(cus2); return list;
		 */

		return mapper.getCustomers();
	}

	public Customer getCustomerInfo(Long id) {
		return mapper.getCustomerInfo(id);
	}

	@Override
	public Integer deleteCustomer(Long id) {
		return mapper.deleteCustomer(id);
	}

	@Override
	public Integer updateCustomer(Customer cus) {
		return mapper.updateCustomer(cus);
	}

	@Override
	public void createCustomers(MultipartFile file) {
		try {
			List<Customer> customers = CSVUtils.readCSV(file.getInputStream(), this);
			if (customers != null && customers.size() > 0) {
				List<Long> ids = mapper.getCustomersIds();
				List<Long> duplicatesId = new ArrayList<>();
				List<Customer> batchCustomers = customers.stream().filter(cus -> {

					if (ids.contains(cus.getId())) {
						duplicatesId.add(cus.getId());
						return false;
					}
					return true;
				}).collect(Collectors.toList());

				if (batchCustomers != null && batchCustomers.size() > 0) {
					batchCustomers.forEach(cus -> mapper.createCustomer(cus));

					// TODO do it with batch -> forEach in mybatis
					// String template = this.createTempBatch(batchCustomers);
					// mapper.createCustomers(template);
				}

				// TODO
				// Mail with duplicates - Wrong
				// duplicatesId
				// this.wrongRowsId
			}
		} catch (IOException ex) {

		}

	}

}
