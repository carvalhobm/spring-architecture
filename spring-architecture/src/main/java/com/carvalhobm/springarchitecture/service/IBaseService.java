package com.carvalhobm.springarchitecture.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.carvalhobm.springarchitecture.dto.BaseDTO;
import com.carvalhobm.springarchitecture.exception.ObjectNotFoundException;
import com.carvalhobm.springarchitecture.model.BaseEntity;

public interface IBaseService<T extends BaseEntity<ID>, DTO extends BaseDTO<T, ID>, ID> {

	/**
	 * Método responsável por listar todos os objetos.
	 * 
	 * @return Lista de {@link BaseEntity}
	 */
	List<T> findAll();

	/**
	 * Método responsável por realizar uma consulta paginada.
	 * 
	 * @param page
	 * @param linesPerPage
	 * @param orderBy
	 * @param direction
	 * @return Lista paginada de {@link Object}
	 */
	Page<T> pagedSearch(Example<T> filter, Pageable pageable);

	/**
	 * Método responsável por buscar objeto por id.
	 * 
	 * @param id
	 * @return {@link Object}
	 */
	T findById(ID id) throws ObjectNotFoundException;

	/**
	 * Método responsável por realizar criação de um novo objeto.
	 * 
	 * @param entidade
	 * @return {@link Usuario}
	 */
	T create(T entity);

	/**
	 * Método responsável por atualizar o bjeto passado.
	 * 
	 * @return {@link Object}
	 * @throws Exception
	 */
	T update(T entity, ID id) throws ObjectNotFoundException;

	/**
	 * Método responsável por excluir objeto.
	 * 
	 * @param id
	 * @throws ObjectNotFoundException
	 */
	void delete(ID id) throws ObjectNotFoundException;

}
