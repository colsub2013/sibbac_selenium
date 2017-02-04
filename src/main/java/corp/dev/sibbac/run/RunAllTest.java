package corp.dev.sibbac.run;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.MarionetteDriver;

import corp.dev.sibbac.constantes.ConstantesGlobales;
import corp.dev.sibbac.helpers.ConfigHelper;
import corp.dev.sibbac.modules.login.Login;

/**
 *	Corre todos los testeos. 
 */
public class RunAllTest {

	/**
	 *	Corre todos los testeos con switch enabled.
	 *	@throws Exception 	 
	 */
	@Test
	public void test() throws Exception {
		
		String baseURL = ConfigHelper.getString(ConstantesGlobales.BASE_URL);
		WebDriver driver = new MarionetteDriver();
		driver.manage().window().maximize();
		driver.get(baseURL + ConstantesGlobales.OGJ_USUARIO_LOGIN_XHTML);
		
		// Test sobre el login
		Login login = new Login(driver, baseURL);
		login.login(ConfigHelper.getString(ConstantesGlobales.USUARIO), 
			ConfigHelper.getString(ConstantesGlobales.PASSWORD));
		
		driver.navigate().refresh();
		driver.quit();
	}

}
