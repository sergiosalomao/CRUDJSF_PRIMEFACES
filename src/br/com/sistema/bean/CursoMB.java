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
import br.com.sistema.controller.CursoDAO;
import br.com.sistema.model.Curso;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

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
	
	

	public void imprimir() throws JRException, IOException {

		AlterFind();

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		
		String nomeArquivo = request.getServletContext().getRealPath("/relatorios/rel_curso.jasper");
		JRPdfExporter pdfExporter = new JRPdfExporter();
		JasperPrint jPrint = JasperFillManager.fillReport(nomeArquivo, null,  Conexao.getConnection());
		pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jPrint);
		byte[] output = JasperExportManager.exportReportToPdf(jPrint);
		response.setContentLength(output.length);		
		response.setContentType("application/pdf;");
		response.setHeader("Content-disposition", "inline; filename=\"curso.pdf\"");
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
