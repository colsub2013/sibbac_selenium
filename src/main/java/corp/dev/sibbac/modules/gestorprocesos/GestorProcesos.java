package corp.dev.sibbac.modules.gestorprocesos;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		
		Thread.sleep(10000);

		WebElement linkConfig = (new WebDriverWait(driver, 20))
		        .until(ExpectedConditions.elementToBeClickable(By.linkText("Configuración")));
		linkConfig.click();

		WebElement linkGP = (new WebDriverWait(driver, 20))
		        .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@ng-href='#/configuracion/jobs                                                                                                                                                                                     ']")));

		if (!linkGP.isDisplayed()) {
			linkGP = (new WebDriverWait(driver, 20))
			        .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@ng-href='#/configuracion/jobs                                                                                                                                                                                     ']")));
		} else {
			linkGP.click();
		}	
		
		LOG.info("Test - FIN ejecutarProceso");
		return mapa;
	}

}

