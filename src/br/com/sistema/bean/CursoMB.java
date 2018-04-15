package br.com.sistema.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.sistema.controller.CursoDAO;
import br.com.sistema.model.Curso;

import java.util.List;

@ManagedBean(name = "cursoMB")
@ViewScoped
public class CursoMB extends CrudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Curso curso;
	private CursoDAO daoCurso;
	private List<Curso> list_curso;
	private Curso selected_curso;

	public CursoMB() {
		curso = new Curso(); // cria
		daoCurso = new CursoDAO();
		try {
			list_curso = daoCurso.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void prepareInsert() {

		try {
			AlterInsert();
			curso = new Curso(); // cria
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void prepareEdit() {

		try {
			AlterEdit();
			curso = daoCurso.find(selected_curso);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() throws Exception {
		try {
			if (IsInsert()) {
				daoCurso.save(curso);
				list_curso = daoCurso.findAll();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso.", null));
			}
			if (IsEdit()) {

				daoCurso.update(curso);
				list_curso = daoCurso.findAll();
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
			curso = null;
			AlterFind();
			list_curso = daoCurso.findAll();

		}

	}

	public void cancel() {

		curso = null;
		AlterFind();

	}

	public void delete() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			daoCurso.delete(selected_curso);
			list_curso = daoCurso.findAll();
			curso = null;
			AlterFind();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Excluido com sucesso..", null));
		} catch (Exception ex) {
			fc.addMessage(null, new FacesMessage("Error :" + ex.getMessage()));
		}
	}

	/* <---- Getters and Setters---> */

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Curso> getList_curso() {
		return list_curso;
	}

	public void setList_curso(List<Curso> list_curso) {
		this.list_curso = list_curso;
	}

	public Curso getSelected_curso() {
		return selected_curso;
	}

	public void setSelected_curso(Curso selected_curso) {
		this.selected_curso = selected_curso;
	}

}
