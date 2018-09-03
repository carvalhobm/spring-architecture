package com.carvalhobm.springarchitecture.model;

import java.io.Serializable;

public abstract class BaseEntity<ID> implements Serializable {

	private static final long serialVersionUID = -323432704706047294L;

	public abstract ID getId();

	public abstract void setId(ID id);

	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract int hashCode();

}
