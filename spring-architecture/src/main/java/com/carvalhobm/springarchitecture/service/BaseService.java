package com.carvalhobm.springarchitecture.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.carvalhobm.springarchitecture.dto.BaseDTO;
import com.carvalhobm.springarchitecture.exception.ObjectNotFoundException;
import com.carvalhobm.springarchitecture.model.BaseEntity;

public abstract class BaseService<R extends JpaRepository<T, ID>, T extends BaseEntity<ID>, DTO extends BaseDTO<T, ID>, ID>
		implements IBaseService<T, DTO, ID> {

	private Class<T> entity;

	@Autowired
	private R repository;

	@Override
	public List<T> findAll() {
		return repository.findAll();
	}

	@Override
	public Page<T> pagedSearch(Example<T> example, Pageable pageable) {
		return repository.findAll(example, pageable);
	}

	@Override
	public T findById(ID id) throws ObjectNotFoundException {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id.toString(), entity.getClass()));
	}

	@Override
	public T create(T entity) {
		return repository.save(entity);
	}

	@Override
	public T update(T entity, ID id) throws ObjectNotFoundException {
		if (repository.existsById(id)) {
			return repository.save(entity);
		} else {
			throw new ObjectNotFoundException(id.toString(), entity.getClass());
		}
	}

	@Override
	public void delete(ID id) throws ObjectNotFoundException {
		if (repository.existsById(id)) {
			repository.deleteById(id);
		} else {
			throw new ObjectNotFoundException(id.toString(), entity.getClass());
		}

	}

}
