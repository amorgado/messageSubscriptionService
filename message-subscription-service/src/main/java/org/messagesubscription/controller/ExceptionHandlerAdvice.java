package org.messagesubscription.controller;

import java.util.List;

import org.messagesubscription.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<Response> handleInputValidation(MethodArgumentNotValidException e) {
		List<ObjectError> errors = e.getBindingResult().getAllErrors();
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for (ObjectError error : errors) {
			if (count > 0) {
				sb.append(",");
			}
			if (error instanceof FieldError) {
				final FieldError fieldError = (FieldError) error;
				if (!fieldError.getField().contains("Valid")) {
					sb.append(fieldError.getField()).append(" ").append(error.getDefaultMessage());
				} else {
					sb.append(error.getDefaultMessage());
				}
			} else {
				sb.append(error.getDefaultMessage());
			}
			count++;
		}
		Response response = new Response();
		response.setMessage(sb.toString());
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

}
