package com.annualLeave.framework.generic;
import java.util.Collection;
import java.util.List;

public interface GenericMapper<E extends GenericEntity, D extends GenericDto> {

	D map(E entity);
	E unMap(D dto);
	List<D> mapAll(Collection<E> entityList);
	List<E> unMapAll(Collection<D> dtoList);
}
