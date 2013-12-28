package com.df.core.rs;

import java.util.ArrayList;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.ObjectMapper;

import com.df.core.common.utils.json.JsonException;
import com.df.core.rs.ErrorResponse.ErrorFormat;
import com.df.core.validation.exception.ValidationException;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

	private ObjectMapper objectMapper;

	public ValidationExceptionMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public Response toResponse(ValidationException ex) {
		ConstraintViolation<?>[] violations = ex.getViolations();
		ArrayList<ValidationError> errors = new ArrayList<ValidationError>();
		for (ConstraintViolation<?> violation : violations) {
			errors.add(new ValidationError(violation.getMessageTemplate(), violation.getMessage()));
		}
		try {
			String jsonErrors = objectMapper.writeValueAsString(errors);
			ErrorResponse errorResponse = new ErrorResponse(ex.getRealm(), ex.getErrorCode(), jsonErrors);
			errorResponse.setErrorFormat(ErrorFormat.JSON);
			return Response.status(400).entity(errorResponse).build();
		} catch (Throwable error) {
			throw new JsonException(error);
		}
	}

	static class ValidationError {
		private String code;

		private String message;

		public ValidationError(String code, String message) {
			this.code = code;
			this.message = message;
		}

		public String getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

	}
}
