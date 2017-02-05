package corp.dev.sibbac.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

import corp.dev.sibbac.constantes.ConstantesGlobales;
import corp.dev.sibbac.entities.JQValidatorError;
import corp.dev.sibbac.helpers.ConfigHelper;

/**
 *	Contiene métodos para fijar el driver más
 *	los controles necesarios de los componentes
 *	de JSF/Primefaces.  
 */
public abstract class AbstractBaseModule {
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractBaseModule.class);
	
	protected WebDriver driver;
	protected String host;
	
	/**
	 *	Constructor con argumentos. 
	 */
	protected AbstractBaseModule(WebDriver driver, String host) {
		this.driver = driver;
		this.host = host;
	}

	/**
	 *	Se para la ejecución por 5 segundos. 
	 * 	@throws Exception 
	 */
	protected static void waitIdle() throws Exception {
		waitIdle(ConstantesGlobales.CINCO);
	}

	/**
	 *	Se para la ejecución por la cantidad de segundos especificada. 
	 * 	@param secs : Segundos a detener la ejecución
	 * 	@throws Exception 
	 */
	protected static void waitIdle(double secs) throws Exception {
		LOG.debug("BaseModule - waitIdle :: start");
		Thread.sleep((long) (ConstantesGlobales.MIL * secs));
		LOG.debug("BaseModule - waitIdle :: end");
	}

	/**
	 *	Ejecución del procesamiento Ajax por 30 segundos.
	 *	@throws Exception 
	 */
	protected void waitForAjax() throws Exception {
		this.waitForAjax(ConstantesGlobales.TREINTA);
	}

	/**
	 *	Ejecución del procesamiento Ajax por la 
	 *	cantidad de segundos especificada.
	 *	@param secs : segundos a detener el procesamiento ajax
	 *	@throws Exception 
	 */
	protected void waitForAjax(double secs) throws Exception {
		LOG.debug("BaseModule - waitForAjax :: start");
		waitIdle(1);
		MarionetteDriver ffDriver = null;
		ChromeDriver chromeDriver = null;
		switch (ConfigHelper.getString(ConstantesGlobales.BROWSER)) {
			case ConstantesGlobales.BROWSER_MFF:
				// Firefox Driver
				ffDriver = (MarionetteDriver) this.driver;
				break;
			case ConstantesGlobales.BROWSER_CHROME:
				// Chrome Driver
				chromeDriver = new ChromeDriver();
				break;
			default:
				break;
		}
		
		
		/**
		 * 	Deprecado - actualizar.
		 */
        @SuppressWarnings("deprecation")
		Stopwatch sw = Stopwatch.createStarted();
//        sw.start();
        while (true) {
            if (sw.elapsed(TimeUnit.SECONDS) > secs) {
        		LOG.error("BaseModule - waitForAjax :: timeout");
            	throw new Exception("Timeout");
            }
            boolean ajaxIsComplete = false;
            switch (ConfigHelper.getString(ConstantesGlobales.BROWSER)) {
            	case ConstantesGlobales.BROWSER_MFF:
                    ajaxIsComplete = new Boolean(ffDriver.executeScript("return jQuery.active == 0").toString());
            		break;
            	case ConstantesGlobales.BROWSER_CHROME:
                    ajaxIsComplete = new Boolean(chromeDriver.executeScript("return jQuery.active == 0").toString());
            		break;	
            }
            

            if (ajaxIsComplete) {
                break;
            }
            Thread.sleep(ConstantesGlobales.CIEN);
        }            
		LOG.debug("BaseModule - waitForAjax :: end");
    }

	/**
	 *	Ejecuta un click en un elemento por el tipo de elemento HTML
	 *	e id parcial que se le pase.
	 *	Sirve tanto para botones como para checkboxes
	 *	
	 *	Pasando "*" como elementTypeHTML simboliza cualquier elemento HTML
	 *	
	 *	@param elementTypeHTML 
	 * 	@param partialId  
	 * 	@throws Exception 
	 */
	@Deprecated
	protected void ctrlClickWildCardByHTMLTypePartialId(
		String elementTypeHTML, String partialId) throws Exception {
		WebElement element = this.driver.findElement(
			By.xpath("//" + elementTypeHTML + "[contains(@id, '" + partialId + "')]"));
		this.ensureWebElementVisible(element);
		element.click();
		this.waitForAjax();
	}
	
	/**
	 * 	Controla el accionamiento del click por el locator y expresión que se le pase.
	 * 	@param locator valores posibles:
	 * 	> LOCATOR_BY_CSS_SELECTOR
	 * 	> LOCATOR_BY_ID
	 * 	> LOCATOR_BY_XPATH
	 * 
	 * 	@param expression la expresión según el tipo de locator
	 * 	@return element
	 * 	@throws Exception 
	 */
	protected WebElement ctrlClickByLocatorAndExpr(
		String locator, String expression) throws Exception {
		WebElement element = this.getWebElementByLocator(locator, expression);
		if (element != null) {
			this.ensureWebElementVisible(element);
			element.click();
			Thread.sleep(3000);
//			this.waitForAjax();
		} 
		return element;
	}
	
	
	/**
	 *	Ejecuta un click en el número de registro de la grilla de búsqueda que se le pasa.
	 * 	@param nroRegistro  
	 * 	@throws Exception 
	 */
	@Deprecated
	protected void ctrlClickFilaGrilla(String nroRegistro) throws Exception {
		this.ensureVisibleFilaGrilla(nroRegistro);
		WebElement opcion = this.driver.findElement(By.xpath("//tbody/tr[@data-ri='" 
				+ nroRegistro + "']/td[8]"));
		opcion.click();
		this.waitForAjax();
	}
	
	/**
	 * 
	 * 
	 */
	@Deprecated
	protected String getTextoColumnaGrilla(
		String nroRegistro, int columna) throws Exception {
		this.ensureVisibleFilaGrilla(nroRegistro);
		WebElement opcion = this.driver.findElement(By.xpath("//tbody/tr[@data-ri='" 
				+ nroRegistro + "']/td[" + columna + "]"));
		if (opcion != null) {
			return opcion.getText();
		} else {
			return null;		
		}
	}
		
//	/**
//	 *	Permite seleccionar una fecha del componente 
//	 *	calendar en base a los parámetros.
//	 *	id: id del elemento
//	 *	String: número de día a seleccionar
//	 *	int desplazMes: si es:
//	 *	. 0: mes actual
//	 *	. n > 0: n meses al futuro
//	 *	. n < 0: n meses al pasado
//	 * 		
//	 * 	@param id 
//	 * 	@param dia 
//	 * 	@param desplazMes 
//	 * 	@throws Exception 
//	 */
//	@Deprecated
//	protected void ctrlCalendar(String id, String dia, 
//		int desplazMes) throws Exception {
//		this.ensureVisibleById(id);
//		this.driver.findElement(By.id(id)).click(); 
//		if (desplazMes > 0) {
//			for (int x = 0; x < desplazMes; x++) {
//				this.driver.findElement(By.cssSelector(".ui-datepicker-next.ui-corner-all")).click(); 
//			}
//		}
//		if (desplazMes < 0) {
//			for (int x = 0; x < -desplazMes; x++) {
//				this.driver.findElement(By.cssSelector(".ui-datepicker-prev.ui-corner-all")).click(); 
//			}
//		}
//		this.driver.findElement(By.linkText(dia)).click();
//		this.waitForAjax();
//	}
//	
//	/**
//	 * 	Selecciona un valor para el combo.
//	 * 	@param id : id del combo (elemento select HTML)
//	 * 	@param val : valor de la etiqueta con la que seleccionará el elemento
//	 * 	@throws Exception	
//	 */
//	protected void ctrlSetCombo(String id, String val) throws Exception {
//		this.ensureVisibleById(id);
//		WebElement element = this.driver.findElement(By.id(id + "_label"));
//		element.click();
//		WebElement opcion = element.findElement(By.xpath("//div[@id='" 
//				+ id + "_panel']/div/ul/li[@data-label='" + val + "']"));
//		opcion.click();
//		this.waitForAjax();
//	}
//
//	/**
//	 *	Se selecciona un valor del picklist.
//	 *	@param id : id del elemento picklist
//	 *	@param val : valor a pasar como target del elemento
//	 *	@throws Exception 
//	 */
//	protected void ctrlPickOption(String id, String val) throws Exception {
//		this.ensureVisibleById(id);
//		WebElement tblElement = this.driver.findElement(By.id(id));
//		WebElement lstElement = tblElement.findElement(By.xpath("//ul[contains(@class,'ui-picklist-source')]"));
//		WebElement element = lstElement.findElement(By.xpath("//li[@data-item-label='" + val + "']"));
//		element.click();
//		this.waitForAjax();
//		
//		WebElement btnElement = tblElement.findElement(By.xpath("//button[contains(@class,'ui-picklist-button-add')]"));
//		btnElement.click();
//		this.waitForAjax();
//	}
//
//	/**
//	 *	Permite fijar ingresar un texto a un elemento HTML input text ó textarea.
//	 * 	@param id : id del elemento input
//	 * 	@param text : texto a ingresar en el elemento input text ó textarea.
//	 * 	@throws Exception 
//	 */
//	protected void ctrlSetText(String id, String text) throws Exception {
//		this.ensureVisibleById(id);
//		WebElement element = this.driver.findElement(By.id(id));
//		element.sendKeys(text);
//	}

	/**
	 *	Se escribe en campo de texto.
	 *	@param locator
	 *	@param expression
	 *	@param text
	 *	@throws Exception 
	 */
	protected WebElement ctrlSetTextByLocator(String locator, String expression, String text) throws Exception {
		this.ensureElementVisibleByLocator(locator, expression);
		WebElement element = this.getWebElementByLocator(locator, expression);
		if (element != null) {
			element.sendKeys(text);
		} else {
			throw new Exception("No se ha podido localizar al elemento.");
		}
		return element;
	}
	
	/**
	 *	Se obtiene elemento Web por Locator y expression.
	 * 	@param locator
	 * 	@param expression
	 * 	@return WebElement
	 */
	protected WebElement getWebElementByLocator(String locator, String expression) {
		WebElement element = null;
		switch (locator) {
			case ConstantesGlobales.LOCATOR_BY_CSS_SELECTOR:
				element = this.driver.findElement(By.cssSelector(expression));
				break;
			case ConstantesGlobales.LOCATOR_BY_ID:
				element = this.driver.findElement(By.id(expression));
				break;
			case ConstantesGlobales.LOCATOR_BY_LINK_TEXT:
				element = this.driver.findElement(By.linkText(expression));
				break;	
			case ConstantesGlobales.LOCATOR_BY_XPATH:
				element = this.driver.findElement(By.xpath(expression));
				break;
			default:
				break;
		}
		return element;
	}
	
	/**
	 *	Se verifica que el elemento sea visible segun locator.
	 *	@param locator
	 *	@param expression
	 */	
	protected void ensureElementVisibleByLocator(String locator, String expression) {
		WebElement element = this.getWebElementByLocator(locator, expression);
		this.ensureWebElementVisible(element);
	}
	
	/**
	 *	Se ejecuta un scroll para que el elemento sea visible.
	 *	@param element : elemento en cuestión. 
	 */
	protected void ensureWebElementVisible(WebElement element) {
		switch (ConfigHelper.getString(ConstantesGlobales.BROWSER)) {
			case ConstantesGlobales.BROWSER_MFF:
				((MarionetteDriver) this.driver).executeScript(
						"window.scrollTo(0, " 
					+ (element.getLocation().y - ConstantesGlobales.CIENTO_CINCUENTA) + ");");
				break;
			case ConstantesGlobales.BROWSER_CHROME:
				((ChromeDriver) this.driver).executeScript(
						"window.scrollTo(0, " 
					+ (element.getLocation().y - ConstantesGlobales.CIENTO_CINCUENTA) + ");");
				break;
		}
		

	}

	/**
	 *	Se ejecuta un scroll para que el elemento sea visible.
	 *	@param nroRegistro : número de registro a seleccionar. 
	 */
	@Deprecated
	protected void ensureVisibleFilaGrilla(String nroRegistro) {
		WebElement element = this.driver.findElement(By.xpath("//tbody/tr[@data-ri='" 
				+ nroRegistro + "']"));
		this.ensureWebElementVisible(element);
	}
	
	/**
	 *	Obtiene la cadena de texto con los resultados devueltos
	 *	de una grilla de búsqueda.
	 *	@return String
	 */
	@Deprecated
	protected String getResultadosDevueltosGrillaBusqueda() {
		return this.driver.findElements(
			By.xpath("//span[@class='ui-paginator-current']")).get(0).getText();
	}
	
	/**
	 * 	Determina si la celda en la agenda está ocupada por selector.
	 * 	@param cssSelector 
	 * 	@return Boolean 
	 */
	@Deprecated
	protected Boolean isCeldaAgendaOcupada(String cssSelector) {
		List<WebElement> elementos = this.driver.findElements(
			By.cssSelector(cssSelector));
		if (elementos != null && !elementos.isEmpty()) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	
	/**
	 * 	Determina el número de celda de la agenda según el selector.
	 * 	@param cssSelector 
	 * 	@return String 
	 */
	@Deprecated
	protected String getNumeroCeldaAgendaByCssSelector(String cssSelector) {
		List<WebElement> elementos = this.driver.findElements(
			By.cssSelector(cssSelector));
		if (elementos != null && !elementos.isEmpty()) {
			return elementos.get(0).getText();
		} else {
			return null;
		}
	}
	
	/**
	 *	Se devuelve lista con los errores de jQuery.
	 *	@return List<JQValidatorError> 
	 */
	@Deprecated
	protected List<JQValidatorError> getJQErrors() {
		ArrayList<JQValidatorError> errors = new ArrayList<JQValidatorError>();
		List<WebElement> elements = this.driver.findElements(By.xpath("//label[@class='error']"));
		for (WebElement element : elements) {
			JQValidatorError error = new JQValidatorError(
					element.getAttribute("for")
						.replaceAll("_input", "")
						.replaceAll("_target", ""),
					element.getText());
			errors.add(error);
		}
		return errors;
	}
	
}
