package corp.dev.sibbac.modules.persona;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import corp.dev.sibbac.constantes.ConstantesGlobales;
import corp.dev.sibbac.helpers.ConfigHelper;
import corp.dev.sibbac.modules.AbstractBaseModule;
import corp.dev.sibbac.utils.StringUtils;

/**
 *	Ejecución de pruebas y relevamiento de resultados para
 *  la funcionalidad de creación de persona. 
 */
public class Persona extends AbstractBaseModule {
	
	private static final Logger LOG = LoggerFactory.getLogger(Persona.class);

	public Persona(WebDriver driver, String host) {
		super(driver, host);
	}
	
	/**
	 *	Crea una parte. 
	 *	@param mapa 
	 *	@param isParte : si es true es una parte, si es false es un sujeto 
	 *	@param tipoDeParte : valor en el combo de parte, por ejemplo: "Asesor Del menor"
	 *  (Defensor por defecto si se deja en null)
	 *	@return mapa: mapa
	 * 	@throws Exception 
	 */
	public HashMap<String, Object> crearParteSujeto(
		HashMap<String, Object> mapa, boolean isParte, String tipoDeParte) throws Exception {
		this.ctrlClickByLocatorAndExpr(
			ConstantesGlobales.LOCATOR_BY_ID, "nuevaSolicitudForm:sujetosSolicitud:j_idt227");

		// Se aguarda 10 segs el procesamiento ajax.
		this.waitForAjax(ConstantesGlobales.DIEZ);

//		driver.get(host 
//			+ "/ogj/carpetaJudicial/formularioPersona.xhtml?idTipoRelacion=1&modo=NEW&origen=Solicitud");
		this.ctrlSetText("personaForm:nroDocumento", 
			StringUtils.getNumericoAleatorioAsString(ConstantesGlobales.CINCO));
		if (tipoDeParte != null && !tipoDeParte.isEmpty()) {
			ctrlSetCombo("personaForm:idTipoPersona", tipoDeParte);
		}
		this.ctrlSetText("personaForm:apellido", "ape-" + StringUtils.getStringAleatorio(ConstantesGlobales.CINCO));
		this.ctrlSetText("personaForm:nombre", "nom-" + StringUtils.getStringAleatorio(ConstantesGlobales.CINCO));
		this.ctrlSetText("personaForm:apellidoMaterno", 
			"ape-mat-" + StringUtils.getStringAleatorio(ConstantesGlobales.CINCO));
		ctrlSetCombo("personaForm:idGenero", "FEMENINO");
		this.ctrlSetText("personaForm:domicilio", 
			"domic " + StringUtils.getNumericoAleatorioAsString(ConstantesGlobales.CINCO));
		ctrlSetCombo("personaForm:localidadPersona", "ROSARIO");
		this.ctrlSetText("personaForm:caracteristicaTel", 
			StringUtils.getNumericoAleatorioAsString(ConstantesGlobales.TRES));
		this.ctrlSetText("personaForm:telefono", StringUtils.getNumericoAleatorioAsString(ConstantesGlobales.CINCO));
		this.ctrlSetText("personaForm:mail", ConfigHelper.getString(ConstantesGlobales.MAIL_VALIDO));
		this.ctrlSetText("personaForm:otrosDatos", 
			"otros datos -" + StringUtils.getStringAleatorio(ConstantesGlobales.CINCO));
		this.ctrlClickByLocatorAndExpr(
			ConstantesGlobales.LOCATOR_BY_ID, "personaForm:guardar");
		return mapa;
	}


}
