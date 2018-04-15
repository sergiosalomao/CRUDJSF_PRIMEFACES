package br.com.sistema.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.sistema.controller.AlunoDAO;
import br.com.sistema.model.Aluno;

import java.util.List;

@ManagedBean(name = "alunoMB")
@ViewScoped
public class AlunoMB extends CrudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Aluno aluno;
	private AlunoDAO daoAluno;
	private List<Aluno> list_aluno;
	private Aluno selected_aluno;

	public AlunoMB() {
		aluno = new Aluno(); // cria
		daoAluno = new AlunoDAO();
		try {
			list_aluno = daoAluno.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void prepareInsert() {

		try {
			AlterInsert();
			aluno = new Aluno(); // cria
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void prepareEdit() {

		try {
			AlterEdit();
			aluno = daoAluno.find(selected_aluno);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() throws Exception {
		try {
			if (IsInsert()) {
				daoAluno.save(aluno);
				list_aluno = daoAluno.findAll();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso.", null));
			}
			if (IsEdit()) {

				daoAluno.update(aluno);
				list_aluno = daoAluno.findAll();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Alterado com sucesso.", null));

				if (IsFind()) {

					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Modo Find", null));
				}
			}

		} catch (

		Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			aluno = null;
			AlterFind();
			list_aluno = daoAluno.findAll();
			System.out.println(state + "<---------------------------------- saindo do save");
		}

	}

	public void cancel() {

		aluno = null;
		AlterFind();

	}

	public void delete() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			daoAluno.delete(selected_aluno);
			list_aluno = daoAluno.findAll();
			aluno = null;
			AlterFind();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Excluido com sucesso..", null));
		} catch (Exception ex) {
			fc.addMessage(null, new FacesMessage("Error :" + ex.getMessage()));
		}
	}

	/* <---- Getters and Setters---> */

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Aluno> getList_aluno() {
		return list_aluno;
	}

	public void setList_aluno(List<Aluno> list_aluno) {
		this.list_aluno = list_aluno;
	}

	public Aluno getSelected_aluno() {
		return selected_aluno;
	}

	public void setSelected_aluno(Aluno selected_aluno) {
		this.selected_aluno = selected_aluno;
	}

}
