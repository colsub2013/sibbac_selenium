package corp.dev.sibbac.modules.bandejaatencion;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import corp.dev.sibbac.constantes.ConstantesGlobales;
import corp.dev.sibbac.helpers.TestHelper;
import corp.dev.sibbac.modules.AbstractBaseModule;

/**
 *	Ejecuci�n de pruebas y relevamiento de resultados para
 *  la funcionalidad de b�squeda de bandeja de atenci�n. 
 */
public class BusquedaBandejaAtencion extends AbstractBaseModule {
	
	private static final Logger LOG = LoggerFactory.getLogger(BusquedaBandejaAtencion.class);

	public BusquedaBandejaAtencion(WebDriver driver, String host) {
		super(driver, host);
	}
	
	/**
	 *	En la funcionalidad de b�squeda de bandeja de entrada, realiza las 
	 *	acciones necesarias y almacena los resultados en un mapa.
	 *	@return HashMap<String, Object>: mapa 
	 * 	@throws Exception 
	 */
	public HashMap<String, Object> buscarBandejaEntrada() throws Exception {
		LOG.info("Test - buscarBandejaEntrada");
		
		HashMap<String, Object> mapa = new HashMap<String, Object>();
		
		// Voy a home
		WebElement linkHome = driver.findElements(By.xpath("//a[@class='col-sm-2 brand-text']")).get(0);
		linkHome.click();
		
		// Se aguarda 10 segs el procesamiento ajax.
		this.waitForAjax(ConstantesGlobales.DIEZ);
		
		// Voy a pantalla de b�squeda
		WebElement linkCJ = driver.findElements(
			By.xpath("//a[@href='/ogj/bandejaDeEntrada/formularioAtencionPublico.xhtml']")).get(0);
		linkCJ.click();
		
		// Se aguarda 10 segs el procesamiento ajax.
		this.waitForAjax(ConstantesGlobales.DIEZ);
		
		// B�squeda con filtros
		LOG.info("B�squeda de solicitudes provenientes de MPA");
		this.ctrlSetCombo("bandejaForm:ofis", "Todos");
		this.ctrlSetCombo("bandejaForm:area", "MPA");
		// this.ctrlCalendar("bandejaForm:fechaAgendaFiltro_input", "10", 1);
		this.ctrlClickByLocatorAndExpr(
			ConstantesGlobales.LOCATOR_BY_ID, "bandejaForm:buscarFiltro");
		
		this.waitForAjax(ConstantesGlobales.DIEZ);

		// Se chequea consistencia del filtro de origen para el 1er registro.
		mapa.put("resultado_bbap_ColumnaFiltro1", 
			this.getTextoColumnaGrilla("0", ConstantesGlobales.CINCO));
		
		int[] resultado = TestHelper.getResultadosGrilla(
			this.getResultadosDevueltosGrillaBusqueda());
		
		// Si hay resultado de b�squeda mayor � igual a 10, 
		// asignar el �ltimo registro a carpeta. 
		if (resultado[2] >= ConstantesGlobales.DIEZ) {
			this.ctrlClickFilaGrilla("9");
			this.ctrlClickByLocatorAndExpr(
				ConstantesGlobales.LOCATOR_BY_ID, "bandejaForm:asignar");
		}
		return mapa;
	}

}
