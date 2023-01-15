package com.annualLeave.framework.abstracts;

import com.annualLeave.framework.generic.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Benim Pet API dökumantasyonum")

public abstract class AbstractRest<E extends GenericEntity, D extends GenericDto> implements GenericRest<E, D> {

	private GenericService<E> service;
	private GenericMapper<E, D> map;

	@Autowired
	public AbstractRest(GenericService service, GenericMapper map) {
		this.service = service;
		this.map = map;
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Get By Id")
	public ResponseEntity<D> get(@PathVariable Long id) throws Exception {
		E entity = service.getById(id);
		return new ResponseEntity<D>(map.map(entity), HttpStatus.OK);
	}

	@GetMapping("/new")
	@ApiOperation(value = "New Model Method")
	public ResponseEntity<D> actNew() throws Exception {
		E entity = service.actNew();
		return new ResponseEntity<>(map.map(entity), HttpStatus.OK);
	}

	@PostMapping
	@ApiOperation(value = "Save Model Method")
	public ResponseEntity<D> save(@RequestBody D dto) throws Exception {
		E entity = service.save(map.unMap(dto));
		return new ResponseEntity<D>(map.map(entity), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete Model Method", notes = "You should use this method carefully")
	public ResponseEntity<D> deleteById(@PathVariable Long id) throws Exception {
		E entity = service.getById(id);
		if (entity == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		service.delete(entity);
		entity = service.actNew();
		return new ResponseEntity<>(map.map(entity), HttpStatus.OK);
	}
}
