package controllers;

import java.util.Collections;
import java.util.List;

import models.Meta;
import models.dao.DAO;
import models.dao.GenericDAO;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.cadastro;


public class Application extends Controller {
	static Form<Meta> metaForm = Form.form(Meta.class);
	private static GenericDAO dao = new DAO();
	private static boolean isToPutExamples = true;

	@Transactional
    public static Result index() {
		if(isToPutExamples){
	    	addExemplosDeMetas();
			isToPutExamples = false;
		}
    	List<Meta> metas = getDao().findAllByClassName("meta");
    	Collections.sort(metas);
        return ok(index.render(metas));
    }
    
    @Transactional
    public static Result cadastrar(){
    	Form<Meta> filledForm = metaForm.bindFromRequest();
    	cadastraMeta(filledForm.get());
    	return redirect(routes.Application.index());
    }
    
    @Transactional
    public static Result remover(Long id){
    	getDao().removeById(Meta.class, id);
    	getDao().flush();
    	return redirect(routes.Application.index());
    }
    
    @Transactional
    public static Result cumprida(Long id){
    	Meta meta = (Meta) getDao().findByEntityId(Meta.class, id);
    	meta.setCumprida(true);
    	getDao().merge(meta);
    	getDao().flush();
    	return redirect(routes.Application.index());
    }

    public static Result cadastro(){
    	return ok(cadastro.render(metaForm));
    }
    
    public static GenericDAO getDao() {
		return dao;
	}
    
    public static void setDao(GenericDAO dao) {
		Application.dao = dao;
	}
    
    private static void cadastraMeta(Meta meta){
    	getDao().merge(meta);
    	getDao().flush();
    }
    
    private static void addExemplosDeMetas(){
    	cadastraMeta(new Meta("Aprender JSF", "Aprender a utilizar o JSF", "1", "alta"));
    	cadastraMeta(new Meta("Aprender Play", "Aprender a utilizar o framework play para SI", "1", "baixa"));
    	cadastraMeta(new Meta("Atualizar Lattes", "Atualizar dados do currículo Lattes com informações mais recentes", "2", "media"));
    	cadastraMeta(new Meta("Comprar PS4", "Comprar um PS4", "2", "alta"));
    	cadastraMeta(new Meta("Pegar carteira de estudante", "Ir no Estudante10 buscar a carteira de estudante", "3", "media"));
    	cadastraMeta(new Meta("Limpar o carro", "Lavar o carro antes de viajar.", "3", "baixa"));
    	cadastraMeta(new Meta("Comprar passagens", "Para comprar passagens para viajar para Brasília em setembro", "1", "media"));
    	cadastraMeta(new Meta("Certificado do inglês", "Buscar o certificado de conclusão do curso de inglês", "2", "baixa"));
    	cadastraMeta(new Meta("Pagar pelo livro", "Pagar o valor do livro de Graciliano Ramos", "3", "alta"));
    	cadastraMeta(new Meta("Show de Zé Ramalho", "Ir para o show de Zé Ramalho no Parque do Povo", "1", "alta"));
    }
}
