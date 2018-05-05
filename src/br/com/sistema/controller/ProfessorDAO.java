package br.com.sistema.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import br.com.sistema.model.Professor;

public class ProfessorDAO implements CrudDAO<Professor> {

	EntityManager manager;
	EntityTransaction transaction;
	static EntityManagerFactory factory = Persistence.createEntityManagerFactory("crud-jsf");

	public ProfessorDAO() {
		manager = factory.createEntityManager();
	}

	public void save(Professor entity) throws Exception {
		transaction = manager.getTransaction();
		transaction.begin();
		manager.persist(entity);
		manager.flush();
		transaction.commit();

	}

	public void update(Professor entity) throws Exception {
		transaction = manager.getTransaction();
		transaction.begin();
		manager.merge(entity);
		manager.flush();
		transaction.commit();
	}

	public void delete(Professor entity) throws Exception {
		transaction = manager.getTransaction();
		transaction.begin();
		manager.remove(entity);
		transaction.commit();
	}

	public List<Professor> findAll() throws Exception {
		return (List<Professor>) manager.createQuery("select obj from Professor obj order by obj.id").getResultList();
		
	}


	public Professor find(Professor professor)  {
		return manager.find(Professor.class, professor.getId());
		
	}
	
}
