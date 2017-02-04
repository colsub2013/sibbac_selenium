package corp.dev.sibbac.modules.login;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

		// Se aguarda 30 segs el procesamiento ajax.
		this.waitForAjax(ConstantesGlobales.TREINTA);
		
		this.ctrlSetTextByLocator(
				ConstantesGlobales.LOCATOR_BY_XPATH, "//*[@ng-model='user.username']", usuario);

		// Se aguarda 30 segs el procesamiento ajax.
		this.waitForAjax(ConstantesGlobales.TREINTA);
		
		WebElement passwordInputText = this.ctrlSetTextByLocator(
				ConstantesGlobales.LOCATOR_BY_XPATH, "//*[@ng-model='user.password']", pass);
		
		// Se pulsa RETURN para el submit de la app
		passwordInputText.sendKeys(Keys.RETURN);

		LOG.info("After click");
		LOG.info("url: " + driver.getCurrentUrl());
		
	}

}
