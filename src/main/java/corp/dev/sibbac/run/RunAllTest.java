package corp.dev.sibbac.run;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.MarionetteDriver;

import corp.dev.sibbac.constantes.ConstantesGlobales;
import corp.dev.sibbac.helpers.ConfigHelper;
import corp.dev.sibbac.modules.login.Login;

/**
 *	Corre todos los testeos. 
 */
public class RunAllTest {

	/**
	 *	Corre todos los testeos con switch enabled.
	 *	@throws Exception 	 
	 */
	@Test
	public void test() throws Exception {
		
		String baseURL = ConfigHelper.getString(ConstantesGlobales.BASE_URL);
		WebDriver driver = new MarionetteDriver();
		driver.manage().window().maximize();
		driver.get(baseURL + ConstantesGlobales.OGJ_USUARIO_LOGIN_XHTML);
		
		// Test sobre el login
		Login login = new Login(driver, baseURL);
		login.login(ConfigHelper.getString(ConstantesGlobales.USUARIO), 
			ConfigHelper.getString(ConstantesGlobales.PASSWORD));
		
		// Test sobre búsqueda de carpeta.
		if (ConfigHelper.getBoolean(ConstantesGlobales.SW_BUSQUEDA_CARPETA_ENABLED)) {
			BusquedaCarpetaJudicialTest bcjt = 
				new BusquedaCarpetaJudicialTest(driver, baseURL);
			bcjt.test();
		}
		
		// Test para alta de solicitud.
		if (ConfigHelper.getBoolean(ConstantesGlobales.SW_ALTA_SOLICITUD_AUDIENCIA_ENABLED)) {
			AltaSolicitudAudienciaTest ast = new AltaSolicitudAudienciaTest(driver, baseURL);
			ast.test();
		}
		
		// Test para búsqueda de bandeja de entrada.
		if (ConfigHelper.getBoolean(ConstantesGlobales.SW_BUSQUEDA_BANDEJA_ENTRADA_ENABLED)) {
			BusquedaBandejaAtencionTest bbat = new BusquedaBandejaAtencionTest(driver, baseURL);
			bbat.test();
		}
		
		// Test para búsqueda de agenda.
		if (ConfigHelper.getBoolean(ConstantesGlobales.SW_BUSQUEDA_CONSULTA_AGENDA_ENABLED)) {
			ConsultaAgendaTest cat = new ConsultaAgendaTest(driver, baseURL);
			cat.test();
		}
		
		driver.navigate().refresh();
		driver.quit();
	}

}
