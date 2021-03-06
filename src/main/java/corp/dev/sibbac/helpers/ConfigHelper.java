package corp.dev.sibbac.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 	Contiene metodos de utilidad para la configuracion
 * 	de archivos .properties. 
 */
public final class ConfigHelper {

	private static final Logger LOG = LoggerFactory.getLogger(ConfigHelper.class);

	private static final String CONST_ENV_DEFAULT = "prod";

	private final static String CONFIG_FILE_PREFIX = "config";
	private static String environment = System.getProperty("gsf.ogj.env");
	private static String propsDir = System.getProperty("gsf.ogj.props.dir");
	
	private static Properties properties = null;
	private static boolean initialized = false;
	
	/**
	 * Constructor por defecto.
	 */
	private ConfigHelper() { }

	// Public methods

	/**
	 * Inicializaci�n est�tica de ConfigHelper.
	 */
	public static void init() {
		if (!initialized) {
			synchronized (ConfigHelper.class) {
				if (!initialized) {
					internalInit();
					LOG.info("ConfigHelper inicializado.");
				}
			}
		}
	}

	/**
	 * Retorna el valor de una propiedad como un objeto del tipo String.
	 * @param property
	 *            Nombre de la propiedad a retornar
	 * @return Valor de la propiedad
	 */
	public static String getString(String property) {
		init();
		return properties.getProperty(property);
	}

	/**
	 * Retorna el valor de una propiedad como un objeto del tipo int.
	 * @param property
	 *            Nombre de la propiedad a retornar
	 * @return Valor de la propiedad
	 */
	public static int getInt(String property) {
		init();
		return Integer.parseInt(properties.getProperty(property));
	}

	/**
	 * Retorna el valor de una propiedad como un objeto del tipo int. En caso de
	 * error, devuelve el valor por defecto
	 * 
	 * @param property
	 *            Nombre de la propiedad a retornar
	 * @param defaultValue
	 *            Valor por defecto
	 * @return Valor de la propiedad
	 */
	public static int getInt(String property, int defaultValue) {
		init();
		int ret = defaultValue;
		try {
			ret = getInt(property);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * Retorna el valor de una propiedad como un objeto del tipo long.
	 * 
	 * @param property
	 *            Nombre de la propiedad a retornar
	 * @return Valor de la propiedad
	 */
	public static long getLong(String property) {
		init();
		return Long.parseLong(properties.getProperty(property));
	}

	/**
	 * Retorna el valor de una propiedad como un objeto del tipo long. En caso
	 * de error, devuelve el valor por defecto
	 * 
	 * @param property
	 *            Nombre de la propiedad a retornar
	 * @param defaultValue
	 *            Valor por defecto
	 * @return Valor de la propiedad
	 */
	public static long getLong(String property, int defaultValue) {
		init();
		long ret = defaultValue;
		try {
			ret = getLong(property);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * Retorna el valor de una propiedad como un objeto del tipo boolean.
	 * 
	 * @param property
	 *            Nombre de la propiedad a retornar
	 * @return Valor de la propiedad
	 */
	public static boolean getBoolean(String property) {
		init();
		return Boolean.parseBoolean(properties.getProperty(property));
	}

	// Private methods

	/**
	 * Procedimiento interno de inicializaci�n.
	 */
	private static void internalInit() {
		properties = new Properties();
		try {
			String env = "";
			if (environment == null || "".equals(environment)) {
				env = CONST_ENV_DEFAULT;
			} else {
				env = environment.toLowerCase();
			}
			if (propsDir != null && (!propsDir.endsWith("/") || !propsDir.endsWith("\\"))) {
				propsDir += "/";
			}
			
			LOG.info("Ambiente inicializado: --===[" + env + "]===--");
			LOG.info("Directorio de propiedades : --===[" + propsDir + "]===--");

			// Archivo de configuraci�n local
			try {
				addPropertiesFile(ConfigHelper.class.getClassLoader().getResourceAsStream(
					CONFIG_FILE_PREFIX + ".properties"));
				LOG.info("Archivo de configuraci�n en EAR [" 
						+ CONFIG_FILE_PREFIX + ".properties] le�do en forma exitosa");
			} catch (Exception e) { 
				e.printStackTrace();
			}
			
			// Archivo de configuraci�n local por entorno
			try {
				if (null != env) {
					// Archivo de configuraci�n local
					addPropertiesFile(ConfigHelper.class.getClassLoader().getResourceAsStream(
						CONFIG_FILE_PREFIX + "_" + env + ".properties"));
					LOG.info("Archivo de configuraci�n en EAR [" + CONFIG_FILE_PREFIX 
						+ "_" + env + ".properties] le�do en forma exitosa");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// Archivo de configuraci�n local por path
			try {
				if (propsDir != null) {
					addPropertiesFile(propsDir + CONFIG_FILE_PREFIX + ".properties");
					LOG.info("Archivo de configuraci�n en VM prop [" 
					+ CONFIG_FILE_PREFIX + ".properties] le�do en forma exitosa");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Archivo de configuraci�n local por path + env
			LOG.info("Listados de propiedades finales");
			try {
				if (propsDir != null && env != null) {
					addPropertiesFile(propsDir + CONFIG_FILE_PREFIX + "_" + env + ".properties");
					LOG.info("Archivo de configuración en VN prop [" + CONFIG_FILE_PREFIX + "_" 
					+ env + ".properties] leído en forma exitosa");
				}
			} catch (Exception e) { 
				e.printStackTrace();
			}
			
			initialized = true;
			
			// Escribe propiedades finales en LOG
			Iterator<Object> iter = properties.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				String val = properties.getProperty(key);
				LOG.info("  > prop [" + key + "] = [" + val + "]");
			}

		} catch (Exception e) {
			LOG.error("Error al leer archivos de configuración", e);
		}
	}

	/**
	 * Agrega properties a partir de un path.
	 * @param filePath Path
	 * @throws Exception Exception
	 */
	private static void addPropertiesFile(String filePath) throws Exception {
		addPropertiesFile(new File(filePath));
	}
	
	/**
	 * Agrega properties a partir de un File.
	 * @param file File
	 * @throws Exception Exception
	 */
	private static void addPropertiesFile(File file) throws Exception {
		if (file.exists()) {
			addPropertiesFile(new FileInputStream(file));
		}
	}
	
	/**
	 * Agrega properties a partir de un InputStream.
	 * @param is InputStream
	 * @throws Exception Exception
	 */
	private static void addPropertiesFile(InputStream is) throws Exception {
		try {
			properties.load(is);
		} catch (Exception e) {
			throw new Exception("Error al cargar archivo por input stream", e);
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}
	
}
