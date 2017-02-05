package corp.dev.sibbac.modules.gestorprocesos;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
		
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

		WebElement linkConfig = this.getWebElementUntilClickableByLocator(
				ConstantesGlobales.LOCATOR_BY_LINK_TEXT, "Configuración", 20);
		linkConfig.click();

		WebElement linkGP = this.getWebElementUntilClickableByLocator(
				ConstantesGlobales.LOCATOR_BY_XPATH, "//a[@ng-href='#/configuracion/jobs                                                                                                                                                                                     ']", 20);
		
		while (!linkGP.isDisplayed() && linkGP.isEnabled()) {
			LOG.info("LinkGP no esta visible ni habilitado");
			linkGP = this.getWebElementUntilClickableByLocator(
					ConstantesGlobales.LOCATOR_BY_XPATH, "//a[@ng-href='#/configuracion/jobs                                                                                                                                                                                     ']", 20);
		} 
		linkGP.click();
		LOG.info("Test - FIN ejecutarProceso");
		return mapa;
	}

}

