package com.globant.jakarta_starter.exceptions;

import java.util.HashMap;
import java.util.Map;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();

        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String field = null;

            for (Path.Node node : violation.getPropertyPath())
                field = node.getName();

            String messasge = violation.getMessage();
            errors.put(field, messasge);
        }

        Map<String, Object> responseObject = new HashMap<>();
        responseObject.put("success", false);
        responseObject.put("status", 400);
        responseObject.put("errors", errors);

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(responseObject)
                .build();
    }

}
