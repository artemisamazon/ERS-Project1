package com.revature.controller;

import java.io.InputStream;
import java.util.List;

import org.apache.tika.Tika;

import com.revature.dto.ChangeReimStatusDTO;
import com.revature.dto.MessageDTO;
import com.revature.model.ReimTicket;
import com.revature.model.User;
import com.revature.service.ReimTicketService;
import com.revature.service.AuthorizationService;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UploadedFile;

public class ReimTicketController implements Controller {

	private AuthorizationService authService;
	private ReimTicketService reimticketService;

	public ReimTicketController() {
		this.authService = new AuthorizationService();
		this.reimticketService = new ReimTicketService();
	}

	private Handler getReimTickets = (ctx) -> {
		// guard this endpoint
		// roles permitted: finmanager and employee
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeEmployeeAndFinmanager(currentlyLoggedInUser);

		// If the above this.authService.authorizeEmployeeAndFinmanager(...) method did
		// not throw an exception, that means
		// our program will continue to proceed to the below functionality
		List<ReimTicket> reimtickets = this.reimticketService.getReimTickets(currentlyLoggedInUser);

		ctx.json(reimtickets);
	};

	// Who should be able to access this endpoint?
	// only finance managers should have access.
	private Handler changeReimStatus = (ctx) -> {
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeFinmanager(currentlyLoggedInUser);
		
		String reimticketId = ctx.pathParam("reimticket_id");
		
		String reimInfo = ctx.formParam("reiminfo"); // may need to be deleted	

		ChangeReimStatusDTO dto = ctx.bodyAsClass(ChangeReimStatusDTO.class); // Taking the request body -> putting the
																				// data into a new object
		
		//this.reimticketDao.changeReimStatus(reimticketId, reimStatus, reimInfo, currentlyLoggedInUser.getId(), reimamount);

		ReimTicket changedReimTicket = this.reimticketService.changeReimStatus(reimticketId, dto.getReimStatus(),reimInfo, currentlyLoggedInUser); 
				
		
		
		ctx.json(changedReimTicket);
		//System.out.println(this.reimticketService.reimAmount);
		
		
	};

	private Handler addReimTicket = (ctx) -> {
		// Protect endpoint
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeEmployee(currentlyLoggedInUser);

		String reimticketType = ctx.formParam("reimticket_type");
		
		String reimAmount = ctx.formParam("reimamount");
		/*
		 * Extracting file from HTTP Request
		 */
		UploadedFile file = ctx.uploadedFile("reimticket_image");

		if (file == null) {
			ctx.status(400);
			ctx.json(new MessageDTO("Must have an image to upload"));
			return;
		}

		InputStream content = file.getContent(); // This is the most important. It is the actual content of the file

		Tika tika = new Tika();
		
		String reiminfo = ctx.formParam("reiminfo");

		// We want to disallow users from uploading files that are not jpeg, gif, or png
		// So, in the controller layer, figure out the MIME type of the file
		// jpeg = image/jpeg
		// gif = image/gif
		// png = image/png
		String mimeType = tika.detect(content);

		// Service layer invocation
		ReimTicket addedReimTicket = this.reimticketService.addReimTicket(currentlyLoggedInUser, mimeType,
				reimticketType, reiminfo, reimAmount, content);
		ctx.json(addedReimTicket);
		ctx.status(201);
	};

	private Handler getImageFromReimTicketById = (ctx) -> {
		// protect endpoint
		User currentlyLoggedInUser = (User) ctx.req.getSession().getAttribute("currentuser");
		this.authService.authorizeEmployeeAndFinmanager(currentlyLoggedInUser);

		String reimticketId = ctx.pathParam("reimticket_id");

		InputStream image = this.reimticketService.getImageFromReimTicketById(currentlyLoggedInUser, reimticketId);

		Tika tika = new Tika();
		String mimeType = tika.detect(image);

		ctx.contentType(mimeType); // specifying to the client what the type of the content actually is
		ctx.result(image); // Sending the image back to the client
	};

	// new handler

	/*
	 * private Handler getAllReimTicketsByEmployee = (ctx) -> { // protect endpoint
	 * User currentlyLoggedInUser = (User)
	 * ctx.req.getSession().getAttribute("currentuser");
	 * this.authService.authorizeFinmanager(currentlyLoggedInUser);
	 * 
	 * String reimauthorId = ctx.pathParam("reimauthor_id");
	 * 
	 * List<ReimTicket> reimtickets =
	 * this.reimticketService.getAllReimTicketsByEmployee(currentlyLoggedInUser,
	 * reimauthorId);
	 * 
	 * ctx.json(reimtickets);
	 * 
	 * };
	 */

	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/reimtickets", getReimTickets);
		app.patch("/reimtickets/{reimticket_id}/reimstatus", changeReimStatus);
		app.post("/reimtickets", addReimTicket);
		app.get("/reimtickets/{reimticket_id}/image", getImageFromReimTicketById);
		// app.get("/reimtickets/{reimauthor_id}", getAllReimTicketsByEmployee);
	}

}