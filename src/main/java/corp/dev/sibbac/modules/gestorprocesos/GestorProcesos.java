package corp.dev.sibbac.modules.gestorprocesos;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import corp.dev.sibbac.constantes.ConstantesGlobales;
import corp.dev.sibbac.modules.AbstractBaseModule;

/**
 *	Ejecución de pruebas y relevamiento de resultados para
 *  la funcionalidad de búsqueda de carpeta judicial. 
 */
public class GestorProcesos extends AbstractBaseModule {
	
	private static final Logger LOG = LoggerFactory.getLogger(GestorProcesos.class);

	public GestorProcesos(WebDriver driver, String host) {
		super(driver, host);
	}
	
	/**
	 *	Ejecucion de tareas para el gestor de procesos.
	 *	@return HashMap<String, Object>: mapa 
	 * 	@throws Exception 
	 */
	public HashMap<String, Object> ejecutarProceso() throws Exception {
		LOG.info("Test - INICIO ejecutarProceso");
		
		HashMap<String, Object> mapa = new HashMap<String, Object>();
		
		// Voy a home
//		WebElement linkHome = this.ctrlClickByLocatorAndExpr(
//				ConstantesGlobales.LOCATOR_BY_CSS_SELECTOR, ".img-responsive");
//		linkHome.click();
		
		// Se aguarda 10 segs el procesamiento ajax.
//		this.waitForAjax(ConstantesGlobales.DIEZ);
		
		Thread.sleep(10000);

		WebElement linkConfig = (new WebDriverWait(driver, 20))
		        .until(ExpectedConditions.elementToBeClickable(By.linkText("Configuración")));
		linkConfig.click();

		WebElement linkGP = this.getWebElementByLocator(
		ConstantesGlobales.LOCATOR_BY_LINK_TEXT, "Gestor de Procesos");

		while (!linkGP.isDisplayed()) {
			linkConfig.click();
		}
		linkGP.click();

		
//		WebElement linkGestorProcesos = (new WebDriverWait(driver, 20))
//		        .until(ExpectedConditions.elementToBeClickable(By.linkText("Gestor de Procesos")));
//		linkGestorProcesos.click();

		
		// Voy a pantalla de búsqueda
//		WebElement linkGP = driver.findElements(
//			By.xpath("//a[@ng-href='#/configuracion/jobs']")).get(0);

//		WebElement linkConfig = this.ctrlClickByLocatorAndExpr(
//				ConstantesGlobales.LOCATOR_BY_LINK_TEXT, "Configuración");
//		linkConfig.click();
//		
//		Thread.sleep(1000);
//		
//		WebElement linkGP = this.ctrlClickByLocatorAndExpr(
//				ConstantesGlobales.LOCATOR_BY_LINK_TEXT, "Gestor de Procesos");
//		linkGP.click();
		
//		// Búsqueda sin filtros
//		LOG.info("Búsqueda sin filtros");
//		this.ctrlClickByLocatorAndExpr(
//			ConstantesGlobales.LOCATOR_BY_ID, "formBusquedaCarpetaJudicial:buscar");
//		
//		LOG.info("Cantidad de registros: " + this.getResultadosDevueltosGrillaBusqueda());
//		mapa.put("bcjResultado1", this.getResultadosDevueltosGrillaBusqueda());
//		
//		// Se limpia la búsqueda
//		this.ctrlClickByLocatorAndExpr(
//			ConstantesGlobales.LOCATOR_BY_ID, "formBusquedaCarpetaJudicial:limpiar");
//		
//		// Búsqueda con filtros
//		LOG.info("Búsqueda c/ etapa de proceso: ejecución y cuij 12");
//		ctrlSetText("formBusquedaCarpetaJudicial:cuij", "12");
//		ctrlSetCombo("formBusquedaCarpetaJudicial:etapasProceso", "EJECUCION");
//		this.ctrlCalendar("formBusquedaCarpetaJudicial:fechaDesde_input", "10", -ConstantesGlobales.CINCO);
//		this.ctrlClickByLocatorAndExpr(
//			ConstantesGlobales.LOCATOR_BY_ID, "formBusquedaCarpetaJudicial:buscar");
//		LOG.info("Cantidad de registros: " + this.getResultadosDevueltosGrillaBusqueda());
//		mapa.put("bcjResultado2", this.getResultadosDevueltosGrillaBusqueda());
		
		// Se aguarda 10 segs el procesamiento ajax.
		this.waitForAjax(ConstantesGlobales.DIEZ);
		
		LOG.info("Test - FIN ejecutarProceso");
		
		return mapa;
	}

}

