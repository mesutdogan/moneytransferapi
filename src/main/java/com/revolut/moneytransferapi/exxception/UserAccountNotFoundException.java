package com.revolut.moneytransferapi.exxception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class UserAccountNotFoundException
		extends RuntimeException implements ExceptionMapper<UserAccountNotFoundException> {

	public UserAccountNotFoundException(String s) {
		super(s);
	}

	@Override
	public Response toResponse(UserAccountNotFoundException exception) {
		return Response.status(404).entity(exception.getMessage())
				.type("text/plain").build();
	}
}
