package com.df.core.rs;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.df.core.common.exception.BusinessException;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

	@Override
	public Response toResponse(BusinessException ex) {
		ErrorResponse error = new ErrorResponse(ex.getRealm(), ex.getErrorCode(), ex.getMessage());
		return Response.status(400).entity(error).build();
	}
}
