package com.annualLeave.framework.generic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericDao<E extends GenericEntity> extends JpaRepository<E, Long> {
}
