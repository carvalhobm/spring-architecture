package com.carvalhobm.springarchitecture.event.listener;

import java.net.URI;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carvalhobm.springarchitecture.event.ResourceCreatedEvent;

@Component
public class ResourceCreatedListener implements ApplicationListener<ResourceCreatedEvent> {

	// TODO: Check why this isn't working
	@Override
	public void onApplicationEvent(ResourceCreatedEvent event) {
		adicionarHeaderLocation(event);
	}

	private void adicionarHeaderLocation(ResourceCreatedEvent event) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(event.getCodigo()).toUri();

		event.getResponse().setHeader("Location", uri.toASCIIString());
	}

}
