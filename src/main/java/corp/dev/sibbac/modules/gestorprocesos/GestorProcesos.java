package corp.dev.sibbac.modules.gestorprocesos;

import java.util.HashMap;

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
		
//		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

		this.aguardarRenderizadoPaginaAngular(driver, 90, 100, 10000);
		
		LOG.info("Test - FIN Espera rendering");
		
		WebElement linkConfig = this.getWebElementUntilClickableByLocator(
				ConstantesGlobales.LOCATOR_BY_LINK_TEXT, "Configuración", 90);
		linkConfig.click();

		Thread.sleep(10000);
		
		WebElement linkGP = this.getWebElementUntilClickableByLocator(
				ConstantesGlobales.LOCATOR_BY_XPATH, "//a[@ng-href='#/configuracion/jobs                                                                                                                                                                                     ']", 90);
		
		while (!linkGP.isDisplayed() && !linkGP.isEnabled()) {
			LOG.info("LinkGP no esta visible ni habilitado");
			linkGP = this.getWebElementUntilClickableByLocator(
					ConstantesGlobales.LOCATOR_BY_XPATH, "//a[@ng-href='#/configuracion/jobs                                                                                                                                                                                     ']", 90);
		}
		
		if (linkGP.isDisplayed() && linkGP.isEnabled()) {
			LOG.info("LinkGP esta visible y habilitado");
			linkGP.click();
		}
		LOG.info("Test - FIN ejecutarProceso");
		return mapa;
	}

}

