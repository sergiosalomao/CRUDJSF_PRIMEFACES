package br.com.sistema.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import br.com.sistema.model.Curso;

public class CursoDAO implements CrudDAO<Curso> {

	EntityManager manager;
	EntityTransaction transaction;
	static EntityManagerFactory factory = Persistence.createEntityManagerFactory("crud-jsf");

	public CursoDAO() {
		manager = factory.createEntityManager();
	}

	public void save(Curso entity) throws Exception {
		transaction = manager.getTransaction();
		transaction.begin();
		manager.persist(entity);
		manager.flush();
		transaction.commit();

	}

	public void update(Curso entity) throws Exception {
		transaction = manager.getTransaction();
		transaction.begin();
		manager.merge(entity);
		manager.flush();
		transaction.commit();
	}

	public void delete(Curso entity) throws Exception {
		transaction = manager.getTransaction();
		transaction.begin();
		manager.remove(entity);
		transaction.commit();
	}

	public List<Curso> findAll() throws Exception {
		return (List<Curso>) manager.createQuery("select obj from Curso obj order by obj.id").getResultList();
		
	}


	public Curso find(Curso curso)  {
		return manager.find(Curso.class, curso.getId());
		
	}
	
}
