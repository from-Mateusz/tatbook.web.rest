package me.m92.tatbook_web;

import java.util.Collection;
import java.util.Optional;

public interface DomainRepository<T extends DomainEntity> {
    T save(T entity);
    T update(T entity);
    void remove(T entity);
    Optional<T> findById(Long id);
    Collection<T> findAll();
}
