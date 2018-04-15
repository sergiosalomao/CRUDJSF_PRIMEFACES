package br.com.sistema.controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import br.com.sistema.model.Aluno;

public class AlunoDAO implements CrudDAO<Aluno> {

	EntityManager manager;
	EntityTransaction transaction;
	static EntityManagerFactory factory = Persistence.createEntityManagerFactory("crud-jsf");

	public AlunoDAO() {
		manager = factory.createEntityManager();
	}

	public void save(Aluno entity) throws Exception {
		transaction = manager.getTransaction();
		transaction.begin();
		manager.persist(entity);
		manager.flush();
		transaction.commit();

	}

	public void update(Aluno entity) throws Exception {
		transaction = manager.getTransaction();
		transaction.begin();
		manager.merge(entity);
		manager.flush();
		transaction.commit();
	}

	public void delete(Aluno entity) throws Exception {
		transaction = manager.getTransaction();
		transaction.begin();
		manager.remove(entity);
		transaction.commit();
	}

	public List<Aluno> findAll() throws Exception {
		return (List<Aluno>) manager.createQuery("select obj from Aluno obj order by obj.id").getResultList();
		
	}


	public Aluno find(Aluno aluno)  {
		return manager.find(Aluno.class, aluno.getId());
		
	}
	
}
