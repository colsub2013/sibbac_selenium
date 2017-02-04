package corp.dev.sibbac.run;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import corp.dev.sibbac.constantes.ConstantesGlobales;
import corp.dev.sibbac.helpers.ConfigHelper;
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
		
		System.setProperty("webdriver.gecko.driver", "geckodriver-v0.14.0-win64/geckodriver.exe");
		
		// Firefox Driver
		LOG.info("Inicializando firefox...");
		
		DesiredCapabilities cap = DesiredCapabilities.firefox();
        cap.setCapability("marionette", true);
        WebDriver driver = new MarionetteDriver(cap);
		
		driver.manage().window().maximize();
		LOG.info("Firefox inicializado.");

		// Home Page
		String baseURL = ConfigHelper.getString(ConstantesGlobales.BASE_URL);
		driver.get(baseURL + "/sibbac20/#/login");
		
		// ======================== Comienzo

		// Test login
		Login login = new Login(driver, baseURL);
		login.login(ConfigHelper.getString(ConstantesGlobales.USUARIO), 
				ConfigHelper.getString(ConstantesGlobales.PASSWORD));
		
		// Test Búsqueda de legajo
//		BusquedaCarpetaJudicial busquedaLegajo = new BusquedaCarpetaJudicial(driver, host);
//		busquedaLegajo.buscarCarpeta();
		
		// Alta de Solicitud
//		AltaSolicitudAudiencia altaSolicitud = new AltaSolicitudAudiencia(driver, host);
//		altaSolicitud.altaSolicitud();

		// ======================== Final

//		driver.quit();
		turnOffGeckoDriver();
		
	}
	
	/**
	 * Setup de la aplicación.
	 * 
	 */
	private static void setup() {
		File configFile = new File(Run.class.getClassLoader().getResource("log4j.properties").getFile());
		PropertyConfigurator.configure(configFile.getAbsolutePath());
	}
	
	private static void turnOffGeckoDriver() {
		boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
		try {
		    if (isDebug)
		        Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	
	
}