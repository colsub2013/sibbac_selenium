package corp.dev.sibbac.modules.carpetajudicial;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import corp.dev.sibbac.constantes.ConstantesGlobales;
import corp.dev.sibbac.modules.AbstractBaseModule;

/**
 *	Ejecuci�n de pruebas y relevamiento de resultados para
 *  la funcionalidad de b�squeda de carpeta judicial. 
 */
public class BusquedaCarpetaJudicial extends AbstractBaseModule {
	
	private static final Logger LOG = LoggerFactory.getLogger(BusquedaCarpetaJudicial.class);

	public BusquedaCarpetaJudicial(WebDriver driver, String host) {
		super(driver, host);
	}
	
	/**
	 *	En la funcionalidad de b�squeda de carpeta, realiza las 
	 *	acciones necesarias y almacena los resultados en un mapa.
	 *	@return HashMap<String, Object>: mapa 
	 * 	@throws Exception 
	 */
	public HashMap<String, Object> buscarCarpeta() throws Exception {
		LOG.info("Test - buscarCarpeta");
		
		HashMap<String, Object> mapa = new HashMap<String, Object>();
		
		// Voy a home
		WebElement linkHome = driver.findElements(By.xpath("//a[@class='col-sm-2 brand-text']")).get(0);
		linkHome.click();
		
		// Se aguarda 10 segs el procesamiento ajax.
		this.waitForAjax(ConstantesGlobales.DIEZ);
		
		// Voy a pantalla de b�squeda
		WebElement linkCJ = driver.findElements(
			By.xpath("//a[@href='/ogj/carpetaJudicial/busquedaCarpetaJudicial.xhtml']")).get(0);
		linkCJ.click();
		
//		// B�squeda sin filtros
//		LOG.info("B�squeda sin filtros");
//		this.ctrlClickByLocatorAndExpr(
//			ConstantesGlobales.LOCATOR_BY_ID, "formBusquedaCarpetaJudicial:buscar");
//		
//		LOG.info("Cantidad de registros: " + this.getResultadosDevueltosGrillaBusqueda());
//		mapa.put("bcjResultado1", this.getResultadosDevueltosGrillaBusqueda());
//		
//		// Se limpia la b�squeda
//		this.ctrlClickByLocatorAndExpr(
//			ConstantesGlobales.LOCATOR_BY_ID, "formBusquedaCarpetaJudicial:limpiar");
//		
//		// B�squeda con filtros
//		LOG.info("B�squeda c/ etapa de proceso: ejecuci�n y cuij 12");
//		ctrlSetText("formBusquedaCarpetaJudicial:cuij", "12");
//		ctrlSetCombo("formBusquedaCarpetaJudicial:etapasProceso", "EJECUCION");
//		this.ctrlCalendar("formBusquedaCarpetaJudicial:fechaDesde_input", "10", -ConstantesGlobales.CINCO);
//		this.ctrlClickByLocatorAndExpr(
//			ConstantesGlobales.LOCATOR_BY_ID, "formBusquedaCarpetaJudicial:buscar");
//		LOG.info("Cantidad de registros: " + this.getResultadosDevueltosGrillaBusqueda());
//		mapa.put("bcjResultado2", this.getResultadosDevueltosGrillaBusqueda());
		
		// Se aguarda 10 segs el procesamiento ajax.
		this.waitForAjax(ConstantesGlobales.DIEZ);
		
		return mapa;
	}

}
