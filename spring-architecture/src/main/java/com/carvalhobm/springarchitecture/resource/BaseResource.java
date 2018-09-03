package com.carvalhobm.springarchitecture.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.carvalhobm.springarchitecture.dto.BaseDTO;
import com.carvalhobm.springarchitecture.event.ResourceCreatedEvent;
import com.carvalhobm.springarchitecture.model.BaseEntity;
import com.carvalhobm.springarchitecture.service.BaseService;

public abstract class BaseResource<T extends BaseEntity<ID>, DTO extends BaseDTO<T, ID>, R extends JpaRepository<T, ID>, S extends BaseService<R, T, DTO, ID>, ID>
		implements IBaseResource<T, DTO, ID> {

	@Autowired
	protected S service;

	@Autowired
	protected ApplicationEventPublisher eventPublisher;

	@Override
	@GetMapping
	public List<T> findAll() {
		return service.findAll();
	}

	@Override
	@GetMapping(params = "paged")
	public Page<T> pagedSearch(Example<T> filter, Pageable pageable) {
		return service.pagedSearch(filter, pageable);
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable ID id) {
		T entity = service.findById(id);
		return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
	}

	@Override
	@PostMapping
	public ResponseEntity<T> create(@Valid @RequestBody T entity, HttpServletResponse response) {
		T entityCreated = service.create(entity);
		eventPublisher.publishEvent(new ResourceCreatedEvent((Object) this, response, (Long) entityCreated.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(entityCreated);
	}

	@Override
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody T entity, @PathVariable ID id) {
		try {
			return ResponseEntity.ok(service.update(entity, id));
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	@DeleteMapping("/{id}")
	public void delete(@PathVariable ID codigo) {
		service.delete(codigo);
	}
}
