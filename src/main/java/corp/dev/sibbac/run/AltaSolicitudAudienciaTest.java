package corp.dev.sibbac.run;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import corp.dev.sibbac.entities.JQValidatorError;
import corp.dev.sibbac.modules.solicitudes.AltaSolicitudAudiencia;

/**
 *	Testeos para el alta de solicitud. 
 */
public class AltaSolicitudAudienciaTest {

	private WebDriver driver = null;
	private String baseURL = null;
	
	public AltaSolicitudAudienciaTest(
		WebDriver driver, String baseURL) {
		this.driver = driver;
		this.baseURL = baseURL;
	}
	
	/**
	 * 	Se corren asserts para alta de solicitud. 
	 * 	@throws Exception 
	 */
	@Test
	public void test() throws Exception {
		
		// Búsqueda de Carpeta judicial
		AltaSolicitudAudiencia altaSolicitud 
			= new AltaSolicitudAudiencia(this.driver, this.baseURL);
		HashMap<String, Object> mapa = altaSolicitud.altaSolicitud();
		
		List<JQValidatorError> errores1 = (List<JQValidatorError>) mapa.get("asCamposRequeridos1");
		for (JQValidatorError jqve : errores1) {
			org.junit.Assert.assertEquals("Campo requerido", jqve.getError());
		}
		
		List<JQValidatorError> errores2 = (List<JQValidatorError>) mapa.get("asCamposRequeridos2");
		for (JQValidatorError jqve : errores1) {
			org.junit.Assert.assertEquals("Campo requerido", jqve.getError());
		}
	}
}
