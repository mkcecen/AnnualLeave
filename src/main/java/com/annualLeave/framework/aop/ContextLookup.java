package com.annualLeave.framework.aop;

import com.annualLeave.framework.generic.GenericDao;
import com.annualLeave.framework.generic.GenericEntity;
import com.annualLeave.framework.generic.GenericService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ContextLookup implements ApplicationContextAware {

    private static ApplicationContext sApplicationContext;

    @Override
    public void setApplicationContext(ApplicationContext aApplicationContext) throws BeansException {
        setContext(aApplicationContext);
    }

    public static void setContext(ApplicationContext aApplicationContext) {
        sApplicationContext = aApplicationContext;
    }

    protected static ApplicationContext getApplicationContext() {
        return sApplicationContext;
    }

    //TODO: Ex: (SessionContext)ContextLookup.getBean("sessionContext");  class adi kucuk harfle baslamali
    public static Object getBean(String aName) {
        if (sApplicationContext != null) {
            return sApplicationContext.getBean(aName);
        }
        return null;
    }

    //TODO: Ex:ContextLookup.getBean(ConstantService.class)
    public static <T> T getBean(Class<T> aClass) {
        if (sApplicationContext != null) {
            return sApplicationContext.getBean(aClass);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static GenericService<GenericEntity> getService(Class<?> entityClass) {
        String path = entityClass.getSimpleName() + "Service";
        path = Character.toLowerCase(path.charAt(0)) + path.substring(1);
        return (GenericService<GenericEntity>) ContextLookup.getBean(path);
    }

    @SuppressWarnings("unchecked")
    public static GenericDao<GenericEntity> getDAO(Class<?> entityClass) {
        String path = entityClass.getSimpleName() + "DAO";
        path = Character.toLowerCase(path.charAt(0)) + path.substring(1);
        return (GenericDao<GenericEntity>) ContextLookup.getBean(path);
    }

}