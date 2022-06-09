package com.theagilemonkeys.devTest.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.theagilemonkeys.devTest.beans.Customer;
import com.theagilemonkeys.devTest.services.impl.CustomerServiceImpl;

public class CSVUtils {

	static String[] HEADERs = { "Id", "Name", "Surname", "Url" };
	public static String TYPE = "text/csv";

	public static boolean hasCSVFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	public static List<Customer> readCSV(InputStream iS, CustomerServiceImpl customerServiceImpl) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(iS, "UTF-8"));
//				CSVParser csvParser = new CSVParser(fileReader,
//						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
				CSVParser csvParser = new CSVParser(fileReader, CSVFormat.EXCEL.withFirstRecordAsHeader());) {
			List<Customer> customers = new ArrayList<Customer>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			Boolean isWrong = false;
			for (CSVRecord csvRecord : csvRecords) {
				String[] columns = csvRecord.get(0).split(";");
				Customer cus = new Customer();
				String value = columns[0];
				if (value.isBlank()) {
					customerServiceImpl.wrongRowsId.add(csvRecord.getRecordNumber());
					continue;
				}
				cus.setId(Long.parseLong(value));
				cus.setName(columns[1]);
				cus.setSurname(columns[2]);
				cus.setUrl(columns[3]);
				if (cus.getName().isBlank() || cus.getName().isBlank() || cus.getName().isBlank()) {
					customerServiceImpl.wrongRowsId.add(csvRecord.getRecordNumber());
					continue;
				}
				customers.add(cus);
			}
			return customers;
		} catch (IOException ex) {
			throw new RuntimeException("fail to parse CSV file: " + ex.getMessage());
		}
	}
}
