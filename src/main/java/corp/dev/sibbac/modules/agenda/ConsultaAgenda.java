package corp.dev.sibbac.modules.agenda;

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
 *  la funcionalidad de consulta de agenda. 
 */
public class ConsultaAgenda extends AbstractBaseModule {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConsultaAgenda.class);

	public ConsultaAgenda(WebDriver driver, String host) {
		super(driver, host);
	}
	
	/**
	 *	En la funcionalidad de b�squeda de agendas, realiza las 
	 *	acciones necesarias y almacena los resultados en un mapa.
	 *	@return HashMap<String, Object>: mapa 
	 * 	@throws Exception 
	 */
	public HashMap<String, Object> buscarAgendas() throws Exception {
		LOG.info("Test - buscarBandejaEntrada");
		
		HashMap<String, Object> mapa = new HashMap<String, Object>();
		
		// Voy a home
		WebElement linkHome = driver.findElements(By.xpath("//a[@class='col-sm-2 brand-text']")).get(0);
		linkHome.click();
		
		// Se aguarda 10 segs el procesamiento ajax.
		this.waitForAjax(ConstantesGlobales.DIEZ);
		
		// Voy a pantalla de b�squeda
		WebElement linkCJ = driver.findElements(
			By.xpath("//a[@href='/ogj/agenda/formularioAgendaFiltro.xhtml']")).get(0);
		linkCJ.click();
		
		// Se aguarda 10 segs el procesamiento ajax.
		this.waitForAjax(ConstantesGlobales.DIEZ);

		// B�squeda con filtros
		this.ctrlSetCombo("agendaForm:tipoActividadAgenda", "Licencia");
		this.ctrlSetCombo("agendaForm:juez", "ARRI, JULIO");
		this.ctrlClickByLocatorAndExpr(
			ConstantesGlobales.LOCATOR_BY_ID, "agendaForm:buscarFiltro");
		this.waitForAjax(ConstantesGlobales.DIEZ);
		
		// Se crea una licencia para el d�a de hoy
		this.ctrlClickByLocatorAndExpr(
			ConstantesGlobales.LOCATOR_BY_CSS_SELECTOR, ".fc-today");
		this.ctrlClickByLocatorAndExpr(
			ConstantesGlobales.LOCATOR_BY_ID, "agendaForm:guardar");

		// Se aguarda 10 segs el procesamiento ajax
		this.waitForAjax(ConstantesGlobales.DIEZ);

		// Confirmo
		this.ctrlClickByLocatorAndExpr(
			ConstantesGlobales.LOCATOR_BY_ID, "agendaForm:j_idt404");
		return mapa;
	}

}
