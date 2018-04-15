package br.com.sistema.controller;

import java.util.List;



public interface CrudDAO<E> { // E representa a entidade

	public void save(E entity)  throws Exception;

	public void update(E entity)  throws Exception;

	public void delete(E entity) throws Exception;

	public List<E> findAll() throws Exception;
	
}
