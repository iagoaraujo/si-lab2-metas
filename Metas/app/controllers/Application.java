package controllers;

import java.util.ArrayList;
import java.util.GregorianCalendar;
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
	private List<Meta> metas = new ArrayList<Meta>();
	private static GenericDAO dao = new DAO();

	@Transactional
    public static Result index() {
    	List<Meta> metas = getDao().findAllByClassName("meta");
        return ok(index.render(metas));
    }
    
    @Transactional
    public static Result cadastrar(){
    	Form<Meta> filledForm = metaForm.bindFromRequest();
    	System.out.println(filledForm.get().getNome());
    	getDao().merge(filledForm.get());
    	getDao().flush();
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
}
