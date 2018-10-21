package com.revolut.moneytransferapi.endpoint;

import com.revolut.moneytransferapi.entity.MoneyTransferDto;
import com.revolut.moneytransferapi.service.UserAccountService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("money-transfer")
public class MoneyTransferEndpoint {

	private UserAccountService userAccountService = UserAccountService.getInstance();

	/**
	 * Method handling HTTP money transfer requests. The returned object will be sent
	 * to the client as application/json media type.
	 *
	 * Takes json input eg. {
	 * "senderId":1,
	 * "receiverId":2,
	 * "bankCode":"A",
	 * "explanation":"Dummy Eplanation",
	 * "amount":240.0
	 * }
	 *
	 * @return {"success":"true"} or  {"success":"false"}
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response moneyTransfer(MoneyTransferDto moneyTransferDto) {
		boolean result = userAccountService.moneyTransfer(moneyTransferDto);
		return Response.ok("{\"success\":\"" + result + "\"}").build();
	}
}
