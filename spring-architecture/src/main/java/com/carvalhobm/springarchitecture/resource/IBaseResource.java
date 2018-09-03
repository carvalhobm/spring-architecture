package com.carvalhobm.springarchitecture.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.carvalhobm.springarchitecture.dto.BaseDTO;
import com.carvalhobm.springarchitecture.model.BaseEntity;

public interface IBaseResource<T extends BaseEntity<ID>, DTO extends BaseDTO<T, ID>, ID> {

	public List<T> findAll();

	public Page<T> pagedSearch(Example<T> filter, Pageable pageable);

	public ResponseEntity<?> findById(@PathVariable ID id);

	public ResponseEntity<T> create(@Valid @RequestBody T entity, HttpServletResponse response);

	public ResponseEntity<?> update(@Valid @RequestBody T entity, @PathVariable ID id);

	public void delete(@PathVariable ID codigo);
}
