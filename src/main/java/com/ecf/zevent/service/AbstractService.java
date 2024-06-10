package com.ecf.zevent.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;

public abstract class AbstractService<R extends JpaRepository, T> implements IService<T> {

    protected R repository;

    public AbstractService(R repository) {
        this.repository = repository;
    }

   public T save(T entity){
        return (T) this.repository.save(entity);
   }

   public T findById(Long id) throws Throwable {
        return (T) this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
   }

   public void delete(Long id) throws Throwable {
        T entity = (T) this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        this.repository.delete(entity);
   }

   public List<T> listAll() {
        return this.repository.findAll();
   }
}
