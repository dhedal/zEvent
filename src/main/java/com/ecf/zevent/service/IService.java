package com.ecf.zevent.service;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.List;

public interface IService<T> {

    public T save(T entity);
    public T findById(Long id) throws Throwable;
    public void delete(Long id) throws Throwable;
    public List<T> listAll();
}
