package com.theagilemonkeys.devTest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.okta.sdk.client.Client;
import com.okta.sdk.resource.group.GroupList;
import com.okta.sdk.resource.user.User;
import com.okta.sdk.resource.user.UserBuilder;
import com.okta.sdk.resource.user.UserList;
import com.okta.sdk.resource.user.UserProfile;
import com.theagilemonkeys.devTest.beans.UserBean;
import com.theagilemonkeys.devTest.beans.UserBean.Profile;
import com.theagilemonkeys.devTest.utils.UserUtils;

import io.swagger.annotations.ApiOperation;

@RestController("/users")
public class AdminController {

	@Autowired
	public Client client;

	@GetMapping("/users")
	@ApiOperation(value = "Gets the list of users from OKTA", response = ResponseEntity.class)
//	public UserList getUsers() {
//		return client.listUsers();
	public ResponseEntity<List<UserBean>> getUsers() {
		UserList usList = client.listUsers();
		List<UserBean> users = new ArrayList<>();
		usList.forEach(user -> {
			users.add(UserUtils.userToBean(user, new UserBean()));
		});
		return ResponseEntity.ok(users);
	}

	@GetMapping("/user/{mail}")
	@ApiOperation(value = "Gets a user from OKTA from given mail", response = UserBean.class)
	public UserBean searchUserByEmail(@PathVariable String mail) {
		// return client.listUsers(mail, null, null, null, null);
		User user = client.getUser(mail);
		GroupList grupos = user.listGroups();
		grupos.forEach(group -> group.getProfile().getName());

		return UserUtils.userToBean(user, new UserBean());
	}

//	@GetMapping("/createUser")
//	public ResponseEntity<?> createUser() {
	// UserBean userBean = new UserBean();
	// userBean.getCredentials().setPassword("P@ssW0rD");
	// userBean.getProfile().setEmail("test@gmail.com");
	// userBean.getProfile().setFirstName("Monkey");
	// userBean.getProfile().setLastName("Test");

	@PostMapping("/createUser")
	@ApiOperation(value = "Creates new user from UserBean, as normal user", response = ResponseEntity.class)
	public ResponseEntity<?> createUser(@RequestBody UserBean userBean) {

		ResponseEntity<String> response = UserUtils.checkData(userBean, true);
		if (response != null) {
			return response;
		}

		char[] tempPassword = userBean.getCredentials().getPassword().toCharArray();
		User user = UserBuilder.instance().setEmail(userBean.getProfile().getEmail())
				.setFirstName(userBean.getProfile().getFirstName()).setLastName(userBean.getProfile().getLastName())
				.setPassword(tempPassword).setGroups("Users").setActive(true).buildAndCreate(client);
		// return ResponseEntity.ok(user);
		return ResponseEntity.ok("User created!");
	}

	@PutMapping("/updateUser")
	@ApiOperation(value = "Updates a user from UserBean body", response = ResponseEntity.class)
	public ResponseEntity<?> updateUser(@RequestBody UserBean userBean) {

		ResponseEntity<String> response = UserUtils.checkData(userBean, false);
		if (response != null) {
			return response;
		}

		Profile urBProf = userBean.getProfile();

		User user = client.getUser(userBean.getProfile().getEmail());
		UserProfile prf = user.getProfile();

		if (urBProf != null) {
			if (urBProf.getEmail() != null && !urBProf.getEmail().contentEquals(prf.getEmail())) {
				prf.setEmail(urBProf.getEmail());
			}

			if (urBProf.getFirstName() != null && !urBProf.getFirstName().contentEquals(prf.getFirstName())) {
				prf.setFirstName(urBProf.getFirstName());
			}

			if (urBProf.getLastName() != null && !urBProf.getLastName().contentEquals(prf.getLastName())) {
				prf.setLastName(urBProf.getLastName());
			}
			user.setProfile(prf);
		}

		// TODO change password
		// if (userBean.getCredentials() != null &&
		// userBean.getCredentials().getPassword() != null) {
		// user.getCredentials().setPassword(userBean.getCredentials().getPassword().toCharArray());
		// }

		User resultUser = client.partialUpdateUser(user, userBean.getProfile().getEmail(), false);
		// return ResponseEntity.ok(resultUser);
		return ResponseEntity.ok("User updated!");
	}

	@DeleteMapping("/deleteUser")
	@ApiOperation(value = "Deletes a user from given mail", response = ResponseEntity.class)
	public ResponseEntity<?> updateUser(@RequestBody String email) {

		if (email != null) {
			return ResponseEntity.badRequest().body("Email must be NOT Null");
		}

		client.getUser(email).deactivate();
		client.getUser(email).delete();
		return ResponseEntity.ok("User deleted!");
	}
}
