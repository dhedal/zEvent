package com.ecf.zevent.service;

import com.ecf.zevent.model.IEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public abstract class AbstractService<R extends JpaRepository, T extends IEntity> implements IService<T> {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());

    protected R repository;

    public AbstractService(R repository) {
        this.repository = repository;
    }

    @Transactional
   public T save(T entity){
        this.LOG.info("save :: " + entity.toString());
        if(entity != null && entity.getId() != null) entity.setUpdatedAt(LocalDateTime.now());
        else entity.setCreatedAt(LocalDateTime.now());
        return (T) this.repository.save(entity);
   }

    @Transactional(readOnly = true)
    public T findById(Long id) throws Throwable {
        return (T) this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
   }

    @Transactional
    public void delete(Long id) throws Throwable {
        T entity = (T) this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        this.repository.delete(entity);
   }


    @Transactional(readOnly = true)
    public List<T> listAll() {
        return this.repository.findAll();
   }
}
