package corp.dev.sibbac.modules.solicitudes;

import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import corp.dev.sibbac.constantes.ConstantesGlobales;
import corp.dev.sibbac.entities.JQValidatorError;
import corp.dev.sibbac.modules.AbstractBaseModule;
import corp.dev.sibbac.modules.persona.Persona;
import corp.dev.sibbac.utils.StringUtils;

/**
 * 	Prueba del alta de una solicitud.
 */
public class AltaSolicitudAudiencia extends AbstractBaseModule {
	
	private static final Logger LOG = LoggerFactory.getLogger(AltaSolicitudAudiencia.class);

	public AltaSolicitudAudiencia(WebDriver driver, String host) {
		super(driver, host);
	}
	
	/**
	 *	Ejecuciones de prueba sobre el alta de solicitud.
	 *	@return HashMap<String, Object> : mapa con valores para los tests
	 *	@throws Exception  
	 */
	public HashMap<String, Object> altaSolicitud() throws Exception {
		LOG.info("Test - altaSolicitud");
		
		HashMap<String, Object> mapa = new HashMap<String, Object>();
		
		// Voy a pantalla de solicitud
		driver.get(host + "/ogj/solicitud/formularioNuevaSolicitud.xhtml?modo=new&origen=ENTRADA");
		
		// Guardo para validar formulario
		LOG.info("Test - guardo sin cargar campos opcionales");
		this.ctrlClickByLocatorAndExpr(
			ConstantesGlobales.LOCATOR_BY_ID, "nuevaSolicitudForm:guardar");
		List<JQValidatorError> errors = getJQErrors();
		for (JQValidatorError error : errors) {
			LOG.info("JQ-Error [" + error.getId() + "] : [" + error.getError() + "]");
		}
		mapa.put("asCamposRequeridos1", errors);
		
		this.ctrlSetText("nuevaSolicitudForm:detalleSolicitud", "Detalle de la solicitud " 
			+ StringUtils.getStringAleatorio(ConstantesGlobales.CUATRO));
		
		// Agrego opción picklist y reviso errores
		LOG.info("Test - cargo picklist y vuelvo a validar");
		ctrlPickOption("nuevaSolicitudForm:tipoAudienciaPickList", "ACUSACIÓN Y AUDIENCIA PRELIMINAR");
		errors = getJQErrors();
		for (JQValidatorError error : errors) {
			LOG.info("JQ-Error [" + error.getId() + "] : [" + error.getError() + "]");
		}
		mapa.put("asCamposRequeridos2", errors);
		this.ctrlClickByLocatorAndExpr(
			ConstantesGlobales.LOCATOR_BY_CSS_SELECTOR, ".btn.btn-primary.btn-xs.dropdown-toggle");
		Persona persona = new Persona(driver, host);
		mapa = persona.crearParteSujeto(mapa, true, "Asesor del menor");
		
		// this.ctrlClick("nuevaSolicitudForm:tieneCarpeta_input");
		this.waitForAjax(ConstantesGlobales.DIEZ);

		ctrlSetCombo("nuevaSolicitudForm:oficinaDestino", "OGJ-1ERA INSTANCIA-Santa Fe");
		this.waitForAjax();
		this.ctrlClickWildCardByHTMLTypePartialId("input", "nuevaSolicitudForm:sujetosSolicitud:0:");
		this.ctrlClickByLocatorAndExpr(
			ConstantesGlobales.LOCATOR_BY_ID, "nuevaSolicitudForm:guardar");
		this.waitForAjax(ConstantesGlobales.DIEZ);		
		return mapa;
	}
	
}