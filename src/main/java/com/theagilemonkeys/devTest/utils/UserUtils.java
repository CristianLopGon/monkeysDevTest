package com.theagilemonkeys.devTest.utils;

import org.springframework.http.ResponseEntity;

import com.okta.sdk.resource.group.GroupList;
import com.okta.sdk.resource.user.User;
import com.theagilemonkeys.devTest.beans.UserBean;

public class UserUtils {

	public static ResponseEntity<String> checkData(UserBean userBean, Boolean create) {
		if (userBean == null) {
			return ResponseEntity.badRequest().body("User must be NOT Null");
		}

		if (userBean.getProfile() == null) {
			return ResponseEntity.badRequest().body("Profile must be NOT Null");
		}

		if (userBean.getProfile().getEmail() == null) {
			return ResponseEntity.badRequest().body("Email must be NOT Null");
		}

		if (userBean.getCredentials() == null && create) {
			return ResponseEntity.badRequest().body("Credentials must be NOT Null");
		}
		return null;
	}

	public static UserBean userToBean(User user, UserBean userBean) {
		if (user == null) {
			return null;
		}
		userBean.getProfile().setEmail(user.getProfile().getEmail());
		userBean.getProfile().setFirstName(user.getProfile().getFirstName());
		userBean.getProfile().setLastName(user.getProfile().getLastName());
		userBean.getProfile().setStatus(user.getProfile().getState());

		GroupList grupos = user.listGroups();
		userBean.getProfile().setIsAdmin(grupos.stream()
				.filter(group -> group.getProfile().getName().contentEquals("Admins")).findAny().isPresent());

		return userBean;
	}
}
