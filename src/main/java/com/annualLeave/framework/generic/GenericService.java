package com.annualLeave.framework.generic;

public interface GenericService <E extends GenericEntity> {

	E actNew() throws Exception;
	E copy(Long id) throws Exception;
	E save(E entity) throws Exception;
	void delete(E entity) throws Exception;
	void deleteById(Long id) throws Exception;

	E getById(Long id) throws Exception;
}
