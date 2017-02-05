package corp.dev.sibbac.run;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import corp.dev.sibbac.constantes.ConstantesGlobales;
import corp.dev.sibbac.helpers.ConfigHelper;
import corp.dev.sibbac.modules.gestorprocesos.GestorProcesos;
import corp.dev.sibbac.modules.login.Login;

/**
 *	Ejecutable de prueba - por el momento no utilizado. 
 */
public class Run {

	private static final Logger LOG = LoggerFactory.getLogger(Run.class);

	/**
	 * Procedimiento principal.
	 * 
	 * @param args 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		// Setup de la aplicación
		setup();
		LOG.info("Aplicación inicializada.");

		WebDriver driver = null;
		switch (ConfigHelper.getString(ConstantesGlobales.BROWSER)) {
			case ConstantesGlobales.BROWSER_MFF:
				// Firefox Driver
				LOG.info("Inicializando firefox...");
				System.setProperty("webdriver.gecko.driver", "geckodriver-v0.14.0-win64/geckodriver.exe");
				
				DesiredCapabilities cap = DesiredCapabilities.firefox();
		        cap.setCapability("marionette", true);
		        driver = new MarionetteDriver(cap);
				
				LOG.info("Firefox inicializado.");
				break;
			case ConstantesGlobales.BROWSER_CHROME:
				// Chrome Driver
				LOG.info("Inicializando Chrome...");
				System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver.exe");
				
				driver = new ChromeDriver();
				LOG.info("Inicializando Chrome...");
				break;
			
			default:
				break;
			
		}

		// Se maximiza ventana
		driver.manage().window().maximize();

		// Home Page
		String baseURL = ConfigHelper.getString(ConstantesGlobales.BASE_URL);
		driver.get(baseURL + "/sibbac20/#/login");
		
		// ======================== Comienzo

		// Test login
		Login login = new Login(driver, baseURL);
		login.login(ConfigHelper.getString(ConstantesGlobales.USUARIO), 
				ConfigHelper.getString(ConstantesGlobales.PASSWORD));
		
		// Test Gestor Procesos
		GestorProcesos gps = new GestorProcesos(driver, baseURL);
		gps.ejecutarProceso();
		

		// ======================== Final

//		driver.quit();
		turnOffDriver();
		
	}
	
	/**
	 * Setup de la aplicación.
	 * 
	 */
	private static void setup() {
		File configFile = new File(Run.class.getClassLoader().getResource("log4j.properties").getFile());
		PropertyConfigurator.configure(configFile.getAbsolutePath());
	}
	
	private static void turnOffDriver() {
		boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
		
		switch (ConfigHelper.getString(ConstantesGlobales.BROWSER)) {
			case ConstantesGlobales.BROWSER_MFF:
				try {
				    if (isDebug)
				        Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
				} catch (IOException e) {
				    e.printStackTrace();
				}
				break;
			case ConstantesGlobales.BROWSER_CHROME:
				try {
				    if (isDebug)
				        Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
				} catch (IOException e) {
				    e.printStackTrace();
				}
				break;
		}
	}
	
}