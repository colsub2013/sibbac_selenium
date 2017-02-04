package corp.dev.sibbac.run;

import java.util.HashMap;

import org.junit.Test;
import org.openqa.selenium.WebDriver;

import corp.dev.sibbac.helpers.TestHelper;
import corp.dev.sibbac.modules.carpetajudicial.BusquedaCarpetaJudicial;

/**
 *	Evaluación de assertions para la funcionalidad de búsqueda
 *	de carpeta judicial. 
 */
public class BusquedaCarpetaJudicialTest {
	
	private WebDriver driver = null;
	private String baseURL = null;
	
	public BusquedaCarpetaJudicialTest(
		WebDriver driver, String baseURL) {
		this.driver = driver;
		this.baseURL = baseURL;
	}

	/**
	 *	Se corren asserts para búsqueda de carpeta judicial. 
	 * 	@throws Exception 
	 */
	@Test
	public void test() throws Exception {
		
		// Búsqueda de Carpeta judicial
		BusquedaCarpetaJudicial carpetaJudicial 
			= new BusquedaCarpetaJudicial(this.driver, this.baseURL);
		HashMap<String, Object> mapa = carpetaJudicial.buscarCarpeta();

		int[] resultado1 = TestHelper.getResultadosGrilla(
			(String) mapa.get("bcjResultado1"));

		int[] resultado2 = TestHelper.getResultadosGrilla(
			(String) mapa.get("bcjResultado2"));

		org.junit.Assert.assertTrue(resultado1[2] > resultado2[2]);
	}
}
