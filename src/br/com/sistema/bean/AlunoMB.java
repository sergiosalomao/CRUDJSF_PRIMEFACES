package br.com.sistema.bean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

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

import org.apache.tomcat.dbcp.dbcp2.ConnectionFactory;
import org.primefaces.context.RequestContext;

import br.com.sistema.controller.AlunoDAO;
import br.com.sistema.controller.Conexao;
import br.com.sistema.controller.JPAUtil;
import br.com.sistema.model.Aluno;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

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
				
			}

		} catch (

		Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			aluno = null;
			AlterFind();
			list_aluno = daoAluno.findAll();
			
		}

	}

	public void cancel() {

		aluno = null;
		AlterFind();

	}
	
	public void imprimir() throws JRException, IOException {
			
		AlterFind();

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		
		String nomeArquivo = request.getServletContext().getRealPath("/relatorios/rel_aluno.jasper");
		JRPdfExporter pdfExporter = new JRPdfExporter();
		JasperPrint jPrint = JasperFillManager.fillReport(nomeArquivo, null,  Conexao.getConnection());
		pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jPrint);
		byte[] output = JasperExportManager.exportReportToPdf(jPrint);
		response.setContentLength(output.length);		
		response.setContentType("application/pdf;");
		response.setHeader("Content-disposition", "inline; filename=\"aluno.pdf\"");
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
