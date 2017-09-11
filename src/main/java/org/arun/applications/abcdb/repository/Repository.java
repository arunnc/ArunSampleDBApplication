package org.arun.applications.abcdb.repository;

public interface Repository<T, ID> {
	
	T save(T entity);
	
	long count();
	
	boolean delete(T entity);
	
	T findByID(ID id);
	
	Iterable<T> findAll();
	
}
