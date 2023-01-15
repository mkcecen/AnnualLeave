package com.annualLeave.framework.generic;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface GenericRest<E extends GenericEntity, D extends GenericDto> {

	ResponseEntity<D> get(@PathVariable Long id) throws Exception;
	ResponseEntity<D> actNew() throws Exception;
	ResponseEntity<D> save(@RequestBody D dto) throws Exception;
}
