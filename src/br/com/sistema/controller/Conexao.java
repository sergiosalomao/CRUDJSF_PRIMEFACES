package br.com.sistema.controller;



import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	
	private static final String user = "postgres";
	private static final String password = "postgres";
	private static final String url = "jdbc:postgresql://localhost/sistema";
	
	private static Connection connection = null;
	
	private Conexao() {}
 
	public static Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				//Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(url, user, password);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Erro ao pegar Conex�o com o Banco de Dados!");
		}
		
		return connection;
	}
	
}
