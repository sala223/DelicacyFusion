package com.df.core.rs;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.df.core.common.exception.BusinessException;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

	@Override
	public Response toResponse(BusinessException ex) {
		ErrorEntity error = new ErrorEntity(ex.getRealm(), ex.getErrorCode(), ex.getMessage());
		return Response.status(400).entity(error).build();
	}

	static class ErrorEntity {
		private String realm;

		private int errorCode;

		private String error;

		public ErrorEntity(String realm, int errorCode, String error) {
			this.realm = realm;
			this.errorCode = errorCode;
			this.error = error;
		}

		public String getRealm() {
			return this.realm;
		}

		public void setRealm(String realm) {
			this.realm = realm;
		}

		public int getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(int errorCode) {
			this.errorCode = errorCode;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}
	}

}
