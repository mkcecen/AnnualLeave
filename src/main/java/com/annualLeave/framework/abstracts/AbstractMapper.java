package com.annualLeave.framework.abstracts;

import com.annualLeave.framework.generic.GenericDto;
import com.annualLeave.framework.generic.GenericEntity;
import com.annualLeave.framework.generic.GenericMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public abstract class AbstractMapper<E extends GenericEntity, D extends GenericDto> implements GenericMapper<E, D> {


	protected PropertyMap<E, D> map;
	protected PropertyMap<D, E> unMap;

	@SuppressWarnings("unchecked")
	private Class<E> getEntityType() {
		Class<?>[] typeArgs = GenericTypeResolver.resolveTypeArguments(getClass(), GenericMapper.class);
		return (Class<E>) typeArgs[0];
	}

	@SuppressWarnings("unchecked")
	private Class<D> getDtoType() {
		Class<?>[] typeArgs = GenericTypeResolver.resolveTypeArguments(getClass(), GenericMapper.class);
		return (Class<D>) typeArgs[1];
	}

	@Override
	public D map(E entity) {
		ModelMapper modelMapper = new ModelMapper();
		if (map != null) {
			return modelMapper.addMappings(map).map(entity);
		}
		return modelMapper.map(entity, getDtoType());
	}

	@Override
	public E unMap(D dto) {
		ModelMapper modelMapper = new ModelMapper();
		if (unMap != null) {
			return modelMapper.addMappings(unMap).map(dto);
		}
		return modelMapper.map(dto, getEntityType());
	}

	@Override
	public List<D> mapAll(final Collection<E> entityList) {
        return entityList.stream().map(entity -> map(entity)).collect(Collectors.toList());
    }

	@Override
	public List<E> unMapAll(final Collection<D> dtoList) {
        return dtoList.stream().map(dto -> unMap(dto)).collect(Collectors.toList());
    }

	/*
	private void logMap(ModelMapper modelMapper) {
	    TypeMap<E, D> typeMap = modelMapper.getTypeMap(getEntityType(), getDtoType());
	    List<Mapping> list = typeMap.getMappings();
	    for (Mapping mapping : list) {
	        System.out.println(mapping);
	    }
	}
	
	private void logUnMap(ModelMapper modelMapper) {
	    TypeMap<D, E> typeMap = modelMapper.getTypeMap(getDtoType(), getEntityType());
	    List<Mapping> list = typeMap.getMappings();
	    for (Mapping mapping : list) {
	        System.out.println(mapping);
	    }
	}*/
}
