package com.revature.controller;

import java.security.InvalidParameterException;

import javax.security.auth.login.FailedLoginException;

import com.revature.dto.MessageDTO;
import com.revature.exception.PreviousTicketCompletedException;
import com.revature.exception.ReimTicketImageNotFoundException;
import com.revature.exception.ReimTicketNotFoundException;
import com.revature.exception.UnauthorizedException;

import io.javalin.Javalin;

public class ExceptionMapper {

	public void mapExceptions(Javalin app) {
		app.exception(FailedLoginException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new MessageDTO(e.getMessage()));
		});
		
		app.exception(UnauthorizedException.class, (e, ctx) -> {
			ctx.status(401);
			ctx.json(new MessageDTO(e.getMessage()));
		});
		
		app.exception(ReimTicketNotFoundException.class, (e, ctx) -> {
			ctx.status(404);
			ctx.json(new MessageDTO(e.getMessage()));
		});
		
		app.exception(InvalidParameterException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new MessageDTO(e.getMessage()));
		});
		
		app.exception(PreviousTicketCompletedException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new MessageDTO(e.getMessage()));
		});
		
		app.exception(ReimTicketImageNotFoundException.class, (e, ctx) -> {
			ctx.status(404);
			ctx.json(new MessageDTO(e.getMessage()));
		});
	}
	
}