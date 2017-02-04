package corp.dev.sibbac.run;

import java.util.HashMap;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import corp.dev.sibbac.modules.bandejaatencion.BusquedaBandejaAtencion;

/**
 *	Evaluaci�n de assertions para la funcionalidad de b�squeda
 *	de bandeja de atenci�n. 
 */
public class BusquedaBandejaAtencionTest {
	
	private WebDriver driver = null;
	private String baseURL = null;
	
	public BusquedaBandejaAtencionTest(
		WebDriver driver, String baseURL) {
		this.driver = driver;
		this.baseURL = baseURL;
	}

	/**
	 *	Se corren asserts para b�squeda de bandeja de atenci�n. 
	 * 	@throws Exception 
	 */
	@Test
	public void test() throws Exception {
		
		// B�squeda de Bandeja de atenci�n
		BusquedaBandejaAtencion bandejaAtencion 
			= new BusquedaBandejaAtencion(this.driver, this.baseURL);
		HashMap<String, Object> mapa = bandejaAtencion.buscarBandejaEntrada();
		
		// Se chequea consistencia de filtros para un registro devuelto.
		org.junit.Assert.assertEquals("MPA", mapa.get("resultado_bbap_ColumnaFiltro1"));
		
	}
}
