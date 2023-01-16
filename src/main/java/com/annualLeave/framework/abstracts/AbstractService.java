package com.annualLeave.framework.abstracts;

import com.annualLeave.framework.generic.GenericDao;
import com.annualLeave.framework.generic.GenericEntity;
import com.annualLeave.framework.generic.GenericService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = Throwable.class)
public abstract class AbstractService<E extends GenericEntity> implements GenericService<E> {


    @PersistenceContext protected EntityManager em;
    @Autowired protected GenericDao<E> dao;
    protected Gson gson = new Gson();

    @Autowired protected ApplicationContext appContext;

    private Class<E> entityClass;

    public AbstractService() {
        entityClass = getEntityType();
    }

    @SuppressWarnings("unchecked")
    private Class<E> getEntityType() {
        Class<E> entityClassType = (Class<E>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractService.class);
        return entityClassType;
    }

    @Override
    public E actNew() throws Exception {
        E entity = entityClass.newInstance();
        return entity;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E copy(Long id) throws Exception {
        E entity = (E) dao.getOne(id).clone();
        entity.setId(null);
        return entity;
    }

    @Override
    public E save(E entity) throws Exception {
        return dao.saveAndFlush(entity);
    }

    @Override
    public void delete(E entity) throws Exception {
        dao.delete(entity);
    }

    @Override
    public void deleteById(Long id) throws Exception {
        dao.deleteById(id);
        em.flush();
    }

    @Override
    public E getById(Long id) throws Exception {
        return (E) dao.getOne(id);
    }

    public static boolean empty(final String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean isTrue(final Boolean b) {
        return b != null && b == true;
    }

    public static boolean isFalse(final Boolean b) {
        return b == null || b == false;
    }


    public void createRuntimeException(E entity, String errorCode, String errorText, String... customErrors) throws Exception {
        List<String> customErrorList = new ArrayList<>();
        if (customErrors != null) {
            for (int i = 1; i <= customErrors.length; i++) {
                customErrorList.add(customErrors[i - 1]);
            }
        }
        throw new Exception("");
//        throw new NakilixException(entity, errorCode, errorText, customErrorList);
    }

}
