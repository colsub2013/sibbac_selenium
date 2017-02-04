package corp.dev.sibbac.modules.login;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import corp.dev.sibbac.constantes.ConstantesGlobales;
import corp.dev.sibbac.modules.AbstractBaseModule;

/**
 *	Contiene la ejecución necesaria para login de usuario. 
 */
public class Login extends AbstractBaseModule {
	
	private static final Logger LOG = LoggerFactory.getLogger(Login.class);

	public Login(WebDriver driver, String host) {
		super(driver, host);
	}
	
	/**
	 *	Logueo de usuario.
	 *	@param usuario  
	 *	@param pass 
	 *	@throws Exception   
	 */
	public void login(String usuario, String pass) throws Exception {
		LOG.info("Test - login");

		Thread.sleep(10000);
		
		// Se aguarda 10 segs el procesamiento ajax.
		this.waitForAjax(ConstantesGlobales.TREINTA);
		
		this.ctrlSetTextByXPath("//*[@ng-model='user.username']", usuario);

		// Se aguarda 10 segs el procesamiento ajax.
		this.waitForAjax(ConstantesGlobales.TREINTA);

		this.ctrlSetTextByXPath("//*[@ng-model='user.password']", pass);

		LOG.info("Before click");
		
		// Se aguarda 10 segs el procesamiento ajax.
		this.waitForAjax(ConstantesGlobales.TREINTA);
		
		this.ctrlClickByLocatorAndExpr(
			ConstantesGlobales.LOCATOR_BY_CSS_SELECTOR, ".btn.buscador.mybutton");
		LOG.info("After click");
		
//		if (!driver.getCurrentUrl().toLowerCase().endsWith("index.xhtml")) {
//			throw new Exception("Fallo de test - No acepta login enviado");
//		}
		LOG.info("url: " + driver.getCurrentUrl());
		
		//assert => estoy logueado
	}

}
