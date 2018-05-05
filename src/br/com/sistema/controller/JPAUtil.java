package br.com.sistema.controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	 
	    private static EntityManagerFactory factory;
	 


	    /**
	     * Cria uma entity manager factory única e o retorna em todas as demais chamadas
	     */
	    public static EntityManagerFactory getFactory() {

	        if (factory == null || !factory.isOpen()) {

	            factory = Persistence.createEntityManagerFactory("crud-jsf");
	        }
	        return factory;

	    }

}
