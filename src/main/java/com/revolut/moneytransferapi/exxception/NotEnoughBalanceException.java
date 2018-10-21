package com.revolut.moneytransferapi.exxception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NotEnoughBalanceException
		extends RuntimeException implements ExceptionMapper<NotEnoughBalanceException> {

	public NotEnoughBalanceException(String s) {
		super(s);
	}

	@Override
	public Response toResponse(NotEnoughBalanceException exception) {
		return Response.status(404).entity(exception.getMessage())
				.type("text/plain").build();
	}
}
