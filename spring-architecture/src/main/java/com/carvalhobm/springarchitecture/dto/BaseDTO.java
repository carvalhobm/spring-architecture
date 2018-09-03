package com.carvalhobm.springarchitecture.dto;

import java.io.Serializable;

import com.carvalhobm.springarchitecture.model.BaseEntity;

public abstract class BaseDTO<T extends BaseEntity<ID>, ID> implements Serializable {

	private static final long serialVersionUID = 1991958898223782080L;

	private ID id;

	public abstract T toNewEntity();

	public T toEntity() {
		T obj = toNewEntity();
		obj.setId(id);
		return obj;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

}
