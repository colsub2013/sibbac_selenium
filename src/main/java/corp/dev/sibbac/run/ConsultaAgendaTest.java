package corp.dev.sibbac.run;

import java.util.HashMap;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import corp.dev.sibbac.modules.agenda.ConsultaAgenda;

/**
 *	Evaluaci�n de assertions para la funcionalidad de b�squeda
 *	de agendas. 
 */
public class ConsultaAgendaTest {
	
	private WebDriver driver = null;
	private String baseURL = null;
	
	public ConsultaAgendaTest(
		WebDriver driver, String baseURL) {
		this.driver = driver;
		this.baseURL = baseURL;
	}

	/**
	 *	Se corren asserts para b�squeda de agendas. 
	 * 	@throws Exception 
	 */
	@Test
	public void test() throws Exception {
		
		// B�squeda de agendas
		ConsultaAgenda consultaAgenda 
			= new ConsultaAgenda(this.driver, this.baseURL);
		HashMap<String, Object> mapa = consultaAgenda.buscarAgendas();
	}
}