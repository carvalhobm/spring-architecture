package com.carvalhobm.springarchitecture.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String usrMessage = messageSource.getMessage("message.invalid", null, LocaleContextHolder.getLocale());
		String devMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		List<Error> errorList = Arrays.asList(new Error(usrMessage, devMessage));

		return handleExceptionInternal(ex, errorList, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<Error> errorList = createErrorList(ex.getBindingResult());

		return handleExceptionInternal(ex, errorList, headers, HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
			WebRequest request) {

		String usrMessage = messageSource.getMessage("message.resource.notfound", null,
				LocaleContextHolder.getLocale());
		String devMessage = ex.toString();
		List<Error> errorList = Arrays.asList(new Error(usrMessage, devMessage));

		return handleExceptionInternal(ex, errorList, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest request) {

		String usrMessage = messageSource.getMessage("message.resource.task-not-allowed", null,
				LocaleContextHolder.getLocale());
		String devMessage = ExceptionUtils.getRootCauseMessage(ex);
		List<Error> errorList = Arrays.asList(new Error(usrMessage, devMessage));

		return handleExceptionInternal(ex, errorList, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	private List<Error> createErrorList(BindingResult bindingResult) {
		List<Error> errorList = new ArrayList<>();

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			String usrMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String devMessage = fieldError.toString();

			errorList.add(new Error(usrMessage, devMessage));
		}

		return errorList;
	}

	public static class Error {
		private String usrMessage;
		private String devMessage;

		public Error(String usrMessage, String devMessage) {
			super();
			this.usrMessage = usrMessage;
			this.devMessage = devMessage;
		}

		public String getUsrMessage() {
			return usrMessage;
		}

		public String getDevMessage() {
			return devMessage;
		}

	}

}
