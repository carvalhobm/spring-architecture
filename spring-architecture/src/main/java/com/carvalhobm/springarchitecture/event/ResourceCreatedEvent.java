package com.carvalhobm.springarchitecture.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class ResourceCreatedEvent extends ApplicationEvent {

	private static final long serialVersionUID = -2130098054944502748L;

	private HttpServletResponse response;
	private Long codigo;

	public ResourceCreatedEvent(Object source, HttpServletResponse response, Long codigo) {
		super(source);

		this.response = response;
		this.codigo = codigo;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Long getCodigo() {
		return codigo;
	}

}
