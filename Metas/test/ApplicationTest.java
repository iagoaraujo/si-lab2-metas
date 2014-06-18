
import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.callAction;
import static play.test.Helpers.charset;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.redirectLocation;
import static play.test.Helpers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Meta;
import models.dao.DAO;
import models.dao.GenericDAO;

import org.junit.Before;
import org.junit.Test;

import play.db.jpa.JPA;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest extends WithApplication{

	Result result;
	
	@Before
	public void setUp() throws Exception{
		start(fakeApplication(inMemoryDatabase()));
	}
	
	@Test
	public void callIndex(){
		result = callAction(controllers.routes.ref.Application.index(), 
        		fakeRequest());
		
		assertThat(status(result)).isEqualTo(Http.Status.OK);
    	assertThat(charset(result)).isEqualTo("utf-8");
    	assertThat(contentAsString(result)).contains("Prazo: 1 semana");
    	assertThat(contentAsString(result)).contains("Prazo: 2 semanas");
    	assertThat(contentAsString(result)).contains("Prazo: 3 semanas");
    	assertThat(contentAsString(result)).contains("Prazo: 4 semanas");
    	assertThat(contentAsString(result)).contains("Prazo: 5 semanas");
    	assertThat(contentAsString(result)).contains("Prazo: 6 semanas");
    	assertThat(contentAsString(result)).contains("Início");
    	assertThat(contentAsString(result)).contains("Nova Meta");
	}

    @Test
    public void callCadastrar() {
    	Helpers.running(Helpers.fakeApplication(Helpers.inMemoryDatabase()), new Runnable() {
    	    public void run() {
    	        JPA.withTransaction(new play.libs.F.Callback0() {
    	            @Override
    	            public void invoke() throws Throwable {
    	            	GenericDAO dao = new DAO();
    	            	Map<String, String> formData = new HashMap<String, String>();
    	            	formData.put("nome", "metaTeste");
    	            	formData.put("descricao", "Testando o sistema de controle de Metas");
    	            	formData.put("prazo", "1");
    	            	formData.put("prioridade", "baixa");
    	            	
    	                result = callAction(controllers.routes.ref.Application.cadastrar(), 
    	                		fakeRequest().withFormUrlEncodedBody(formData));
    	                
    	                
    	                //Ao cadastrar, retorna ao index
    	                assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
    	                assertThat(redirectLocation(result)).isEqualTo("/");
    	                
    	                //A meta foi cadastrada no sistema
    	                List<Meta> metas = dao.findAllByClassName("meta");
    	                assertThat(metas.size()).isEqualTo(1);
    	            }
    	        });
    	    }
    	});
    }
    
    @Test
    public void callRemover(){
    	Helpers.running(Helpers.fakeApplication(Helpers.inMemoryDatabase()), new Runnable() {
    	    public void run() {
    	        JPA.withTransaction(new play.libs.F.Callback0() {
    	            @Override
    	            public void invoke() throws Throwable {
    	            	GenericDAO dao = new DAO();
    	            	Map<String, String> formData = new HashMap<String, String>();
    	            	formData.put("nome", "metaTeste");
    	            	formData.put("descricao", "Testando o sistema de controle de Metas");
    	            	formData.put("prazo", "1");
    	            	formData.put("prioridade", "baixa");
    	            	
    	                result = callAction(controllers.routes.ref.Application.cadastrar(), 
    	                		fakeRequest().withFormUrlEncodedBody(formData));
    	                
    	                
    	                //Ao cadastrar, retorna ao index
    	                assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
    	                assertThat(redirectLocation(result)).isEqualTo("/");
    	                
    	                //A meta foi cadastrada no sistema
    	                List<Meta> metas = dao.findAllByClassName("meta");
    	                assertThat(metas.size()).isEqualTo(1);
    	                Long id = metas.get(0).getId();
    	                
    	                //Remove a meta cadastrada anteriormente
    	                result = callAction(controllers.routes.ref.Application.remover(id), 
    	                		fakeRequest());
    	                
    	                assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
    	                assertThat(redirectLocation(result)).isEqualTo("/");
    	                
    	                metas = dao.findAllByClassName("meta");
    	                assertThat(metas.size()).isEqualTo(0);
    	            }
    	        });
    	    }
    	});
    }

    @Test
    public void callCadastro() {
    	result = callAction(controllers.routes.ref.Application.cadastro(), 
        		fakeRequest());
    	
    	//Checa os campos existentes da pagina de cadastro
    	assertThat(status(result)).isEqualTo(Http.Status.OK);
    	assertThat(charset(result)).isEqualTo("utf-8");
    	assertThat(contentAsString(result)).contains("Nome:");
    	assertThat(contentAsString(result)).contains("Descrição:");
    	assertThat(contentAsString(result)).contains("Prazo:");
    	assertThat(contentAsString(result)).contains("Prioridade:");
    	assertThat(contentAsString(result)).contains("Início");
    	assertThat(contentAsString(result)).contains("Nova Meta");
    }
    
    @Test
    public void marcarCumpridaTest(){
    	Helpers.running(Helpers.fakeApplication(Helpers.inMemoryDatabase()), new Runnable() {
    	    public void run() {
    	        JPA.withTransaction(new play.libs.F.Callback0() {
    	            @Override
    	            public void invoke() throws Throwable {
    	            	GenericDAO dao = new DAO();
    	            	Map<String, String> formData = new HashMap<String, String>();
    	            	formData.put("nome", "metaTeste");
    	            	formData.put("descricao", "Testando o sistema de controle de Metas");
    	            	formData.put("prazo", "1");
    	            	formData.put("prioridade", "baixa");
    	            	
    	                result = callAction(controllers.routes.ref.Application.cadastrar(), 
    	                		fakeRequest().withFormUrlEncodedBody(formData));
    	                
    	                //Ao cadastrar, retorna ao index
    	                assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
    	                assertThat(redirectLocation(result)).isEqualTo("/");
    	                
    	                //A meta foi cadastrada no sistema
    	                List<Meta> metas = dao.findAllByClassName("meta");
    	                assertThat(metas.size()).isEqualTo(1);
    	                Long id = metas.get(0).getId();
    	                assertThat(metas.get(0).isCumprida()).isEqualTo(false);
    	                
    	                result = callAction(controllers.routes.ref.Application.cumprida(id), 
    	                		fakeRequest());

    	                assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
    	                assertThat(redirectLocation(result)).isEqualTo("/");
    	            }
    	        });
    	    }
    	});
    }
}
