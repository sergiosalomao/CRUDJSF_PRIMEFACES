package br.com.sistema.bean;



public class CrudBean {

	protected String state = "find"; // find, insert, edit
	 
	


	// METODOS VERIFICAR ESTADO TELA
	public boolean IsInsert() {
		return "insert".equals(state);
	}

	public boolean IsEdit() {
		return "edit".equals(state);
	}

	public boolean IsFind() {
		return "find".equals(state);
	}

	public void AlterInsert() {
		state = "insert";
	}

	public void AlterEdit() {
		state = "edit";
	}

	public void AlterFind() {
		state = "find";
	}

	
}
