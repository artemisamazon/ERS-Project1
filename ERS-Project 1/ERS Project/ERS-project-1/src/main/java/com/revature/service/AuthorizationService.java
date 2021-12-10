package com.revature.service;

import com.revature.exception.UnauthorizedException;
import com.revature.model.User;

// Authentication is different than Authorization
// Authentication is about providing credentials to identify who you are
// Authorization is about checking whether you have the permissions to access a particular thing
public class AuthorizationService {

	public void authorizeEmployeeAndFinmanager(User user) throws UnauthorizedException {
		// If the user is not either a regular role or admin role
		if (user == null || !(user.getUserRole().equals("employee") || user.getUserRole().equals("finmanager"))) {
			throw new UnauthorizedException("This resource is only intended for authorized employees and finance managers. You are not authorized to access this resource");
		}
	}
	
	public void authorizeFinmanager(User user) throws UnauthorizedException {
		if (user == null || !user.getUserRole().equals("finmanager")) {
			throw new UnauthorizedException("Authorized Finance Manager must be logged in for access");
		}
	}

	public void authorizeEmployee(User user) throws UnauthorizedException {
		// if we are not logged in, or do not have a role of employee
		if (user == null || !user.getUserRole().equals("employee")) {
			throw new UnauthorizedException("Authorized empolyees must be logged in for access");
		}
	}
	
}