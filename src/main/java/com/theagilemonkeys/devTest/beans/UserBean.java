package com.theagilemonkeys.devTest.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class UserBean {

	Profile profile;
	Credentials credentials;

	public UserBean() {
		this.profile = new Profile();
		this.credentials = new Credentials();
	}

	@Setter
	@Getter
	@NoArgsConstructor
	public class Profile {
		String email;
		String firstName;
		String lastName;
		String status;
		Boolean isAdmin;
	}

	@Setter
	@Getter
	@NoArgsConstructor
	public class Credentials {
		String password;
	}

}
