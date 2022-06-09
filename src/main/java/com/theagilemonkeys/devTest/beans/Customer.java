package com.theagilemonkeys.devTest.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Customer {

	private Long Id; // Change Id, not autoincremental in DB, maybe email as Id
	private String Name;
	private String Surname;
	private String Url;
	private String Photo;
	private Integer CreatedBy;
	private Integer ModifiedBy;
}