package com.revature.service;

import java.io.InputStream;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.dao.ReimTicketDAO;
import com.revature.exception.PreviousTicketCompletedException;
import com.revature.exception.ReimTicketImageNotFoundException;
import com.revature.exception.ReimTicketNotFoundException;
import com.revature.exception.UnauthorizedException;
import com.revature.model.ReimTicket;
import com.revature.model.User;

public class ReimTicketService {

	private ReimTicketDAO reimticketDao;

	public ReimTicketService() {
		this.reimticketDao = new ReimTicketDAO();
	}

	public ReimTicketService(ReimTicketDAO reimticketDao) {
		this.reimticketDao = reimticketDao;
	}

	// If the currently logged in User is an employee, we should only grab the
	// reimtickets that belong to that employee
	// If the currently logged in User is a finmanager, we should grab ALL
	// reimtickets
	public List<ReimTicket> getReimTickets(User currentlyLoggedInUser) throws SQLException {
		List<ReimTicket> reimtickets = null;

		if (currentlyLoggedInUser.getUserRole().equals("finmanager")) {
			reimtickets = this.reimticketDao.getAllReimTickets();
		} else if (currentlyLoggedInUser.getUserRole().equals("employee")) {
			reimtickets = this.reimticketDao.getAllReimTicketsByEmployee(currentlyLoggedInUser.getId());
		}

		return reimtickets;
	}

	// We do not want duplicate reimbursements for the same ticket.
	// Once we have already completed a reimticket (amount added), it is permanent,
	// you can't change it from there

	// 0. Check if the reimticket exists or not
	// 1. Check if the reimticket already has a reimbursement amount (reimamount)
	// - if it does, throw an PreviousTicketCompletedException
	// 2. If it doesn't already have a reimamount, reimamount can be modified.
	public ReimTicket changeReimStatus(String reimticketId, String reimStatus, String reimInfo, User currentlyLoggedInUser)
			throws SQLException, ReimTicketNotFoundException, PreviousTicketCompletedException {
		try {
			int id = Integer.parseInt(reimticketId);

			ReimTicket reimticket = this.reimticketDao.getReimTicketById(id);

			// 0
			if (reimticket == null) {
				throw new ReimTicketNotFoundException("ReimTicket with id " + reimticketId + " was not found");
			}

			// 1
			if (reimticket.getResolverId() == 0) { // if it's 0, it means no fiance manager has reviewed ticket yet.
				
				//changeReimStatus(String reimticketId, String reimStatus, String reimInfo, int resolverId, double reimamout) in DAO
													
				this.reimticketDao.changeReimStatus(id, reimStatus, reimInfo, currentlyLoggedInUser.getId());
				
				
			
			} else { // if it has already been reimbursed, and we're trying to change
						// the reimamount here, that shouldn't be allowed
				throw new PreviousTicketCompletedException(
						" Previous ReimTicket Status was accepted. Modification of ReimTicket Status is not authorized.");

			}

			return this.reimticketDao.getReimTicketById(id);
		} catch (NumberFormatException e) {
			throw new InvalidParameterException("ReimTicket id supplied must be a numeric value");
		}

	}

	// Business logic
	// check if the mimetype is either image/jpeg, image/png, image/gif
	// and if not, throw a InvalidParameterException
	public ReimTicket addReimTicket(User currentlyLoggedInUser, String mimeType, String reimticketType, String reimInfo, String reimAmount,
			InputStream content) throws SQLException {
		Set<String> allowedFileTypes = new HashSet<>();
		allowedFileTypes.add("image/jpeg");
		allowedFileTypes.add("image/png");
		allowedFileTypes.add("image/gif");

		if (!allowedFileTypes.contains(mimeType)) {
			throw new InvalidParameterException(
					"When adding an reimticket receipt or document, only PNG, JPEG, or GIF are allowed");
		}

		// Author, reimticket type, file content (bytes, 0s and 1s)
		int id = currentlyLoggedInUser.getId(); // whoever is logged in, will be the actual author of the
															// reimticket

		double amount = Double.parseDouble(reimAmount);
		
		//addReimTicket(String reimticketType, int reimauthorId, String reimInfo, InputStream content, double reimamount) in DAO
			
		ReimTicket addedReimTicket = this.reimticketDao.addReimTicket(reimticketType, id, reimInfo, content, amount);

		return addedReimTicket;
	}

	// Business logic
	// Finmanagers will be able to view any images that belong to anybody
	// Employees can only view images for assignments that belong to them
	public InputStream getImageFromReimTicketById(User currentlyLoggedInUser, String reimticketId)
			throws SQLException, UnauthorizedException, ReimTicketImageNotFoundException {

		try {
			int id = Integer.parseInt(reimticketId);

			// Check if they are an employee
			if (currentlyLoggedInUser.getUserRole().equals("employee")) {
				// Grab all of the reimtickets that belong to the employee
				int reimauthorId = currentlyLoggedInUser.getId();
				List<ReimTicket> reimticketsThatBelongToEmployee = this.reimticketDao
						.getAllReimTicketsByEmployee(reimauthorId);

				Set<Integer> reimticketIdsEncountered = new HashSet<>();
				for (ReimTicket a : reimticketsThatBelongToEmployee) {
					reimticketIdsEncountered.add(a.getReimticketId());
				}

				// Check to see if the image they are trying to grab for a particular reimticket
				// is actually their own reimticket
				if (!reimticketIdsEncountered.contains(id)) {
					throw new UnauthorizedException(
							"You cannot access the receipts of reimtickets that do not belong to yourself");
				}
			}

			// Grab the image from the DAO
			InputStream image = this.reimticketDao.getImageFromReimTicketById(id);

			if (image == null) {
				throw new ReimTicketImageNotFoundException("Receipt image was not found for reimticket id " + id);
			}

			return image;

		} catch (NumberFormatException e) {
			throw new InvalidParameterException("ReimTicket id supplied must be an a numeric value");
		}

		// }

		// new method in service layer--Get ReimTickets by Employee--
		/*
		 * public List<ReimTicket> getAllReimTicketsByEmployee(User
		 * currentlyLoggedInUser, String reimauthorId) { List<ReimTicket> reimtickets =
		 * null;
		 * 
		 * if (currentlyLoggedInUser.getUserRole().equals("finmanager")) { reimtickets =
		 * this.reimticketDao.getAllReimTicketsByEmployee(reimauthorId); }
		 * 
		 * // return reimtickets;
		 * 
		 * }
		 */
	}
}
