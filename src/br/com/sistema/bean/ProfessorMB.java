package br.com.sistema.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;

import br.com.sistema.controller.Conexao;
import br.com.sistema.controller.ProfessorDAO;
import br.com.sistema.model.Professor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import java.util.List;

@ManagedBean(name = "professorMB")
@ViewScoped
public class ProfessorMB extends CrudBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Professor professor;
	private ProfessorDAO daoProfessor;
	private List<Professor> list_professor;
	private Professor selected_professor;

	public ProfessorMB() {
		professor = new Professor(); // cria
		daoProfessor = new ProfessorDAO();
		try {
			list_professor = daoProfessor.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void prepareInsert() {

		try {
			AlterInsert();
			professor = new Professor(); // cria
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void prepareEdit() {

		try {
			AlterEdit();
			professor = daoProfessor.find(selected_professor);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void save() throws Exception {
		try {
			if (IsInsert()) {
				daoProfessor.save(professor);
				list_professor = daoProfessor.findAll();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso.", null));
			}
			if (IsEdit()) {

				daoProfessor.update(professor);
				list_professor = daoProfessor.findAll();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Alterado com sucesso.", null));
				
			}

		} catch (

		Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			professor = null;
			AlterFind();
			list_professor = daoProfessor.findAll();
			
		}

	}

	public void cancel() {

		professor = null;
		AlterFind();

	}
	
	public void imprimir() throws JRException, IOException {

		
		AlterFind();

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		
		String nomeArquivo = request.getServletContext().getRealPath("/relatorios/rel_professor.jasper");
		JRPdfExporter pdfExporter = new JRPdfExporter();
		JasperPrint jPrint = JasperFillManager.fillReport(nomeArquivo, null,  Conexao.getConnection());
		pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jPrint);
		byte[] output = JasperExportManager.exportReportToPdf(jPrint);
		response.setContentLength(output.length);		
		response.setContentType("application/pdf;");
		response.setHeader("Content-disposition", "inline; filename=\"professor.pdf\"");
		ServletOutputStream ouputStream = response.getOutputStream();
		ouputStream.write(output);

		ouputStream.flush();
		ouputStream.close();
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("CRUD::JSF|PRIMEFACES",  "Relatório gerado com sucesso!") );
		

	}

	public void delete() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			daoProfessor.delete(selected_professor);
			list_professor = daoProfessor.findAll();
			professor = null;
			AlterFind();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Excluido com sucesso..", null));
		} catch (Exception ex) {
			fc.addMessage(null, new FacesMessage("Error :" + ex.getMessage()));
		}
	}

	/* <---- Getters and Setters---> */

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public List<Professor> getList_professor() {
		return list_professor;
	}

	public void setList_professor(List<Professor> list_professor) {
		this.list_professor = list_professor;
	}

	public Professor getSelected_professor() {
		return selected_professor;
	}

	public void setSelected_professor(Professor selected_professor) {
		this.selected_professor = selected_professor;
	}

}
