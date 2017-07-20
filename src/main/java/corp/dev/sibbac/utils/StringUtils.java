package corp.dev.sibbac.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import corp.dev.sibbac.constantes.ConstantesGlobales;

/**
 * Utilidades basicas para strings.
 */
public final class StringUtils {

	/**
	 * Constructor por defecto.
	 */
	private  StringUtils() {
	}

	/**
	 * En caso de que el valor sea nulo, devuelve "".
	 * @param value Valor
	 * @return Retorno
	 */
	public static String safeNull(String value) {
		if (value == null) {
			return "";
		} else {
			return value.trim();
		}
	}
	
	/**
	 * Realiza un encodeado basico solo a nivel de caracteres especiales.
	 * 
	 * @param texto Texto a encodear.
	 * @return Texto encodeado.
	 */
	public static String htmlEncodeTilde(String texto) {

		String textoAux = texto;

		if (textoAux == null) {
			return "";
		} else {
			textoAux = textoAux.replace("�", "&aacute;");
			textoAux = textoAux.replace("�", "&eacute;");
			textoAux = textoAux.replace("�", "&iacute;");
			textoAux = textoAux.replace("�", "&oacute;");
			textoAux = textoAux.replace("�", "&uacute;");
			textoAux = textoAux.replace("�", "&Aacute;");
			textoAux = textoAux.replace("�", "&Eacute;");
			textoAux = textoAux.replace("�", "&Iacute;");
			textoAux = textoAux.replace("�", "&Oacute;");
			textoAux = textoAux.replace("�", "&Uacute;");
			textoAux = textoAux.replace("�", "&ntilde;");
			textoAux = textoAux.replace("�", "&Ntilde;");
			textoAux = textoAux.replace("�", "&deg;");
			return textoAux;
		}
	}
	
	/**
	 * Realiza un encodeado basico solo a nivel de caracteres especiales.
	 * 
	 * @param mapaOriginal Mapa original
	 * @return Mapa encodeado.
	 */
	public static HashMap<String, Object> htmlEncodeTilde(HashMap<String, Object> mapaOriginal) {
		HashMap<String, Object> mapaNuevo = new HashMap<String, Object>();
		Iterator<String> iter = mapaOriginal.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			Object obj = mapaOriginal.get(key);
			if (obj instanceof String) {
				mapaNuevo.put(key, htmlEncodeTilde((String) obj));
			} else {
				mapaNuevo.put(key,  obj);
			}
		}
		
		return mapaNuevo;
	}
	
	/**
	 * 	splitByLength.
	 * 	@param pValue 
	 * 	@param length 
	 * 	@param splitStr  
	 * 	@return String
	 */
	public static String splitByLength(String pValue, int length, String splitStr) {
		String value = pValue;
		if (value != null) {
			StringBuffer sb = new StringBuffer();
			do {
				if (value.length() > length) {
					String cut = value.substring(0, Math.min(value.length(), length));
					int pos = cut.lastIndexOf(" ");
					if (pos > 0) {
						cut = cut.substring(0, pos);
					}
					sb.append(cut);
					if (pos > 0) {
						value = value.substring(Math.min(cut.length() + 1, length));
					} else {
						value = value.substring(cut.length());
					}
					if (!"".equals(value)) {
						sb.append(splitStr);
					}
				} else {
					sb.append(value);
					value = "";
				}
			} while (!"".equals(value));
			
			return sb.toString();
		} else {
			return null;
		}
	}
	
	/**
	 * Normaliza un string para busquedas en base de datos
	 * con FN_NORMALIZAR_CADENA.
	 * @param value string original
	 * @return string normalizado
	 */
	public static String fnNormalizarCadena(String value) {
	    String original = "�������";
	    String ascii = "AEIOOUU";
	    String output = safeNull(value).toUpperCase();
	    for (int i = 0; i < original.length(); i++) {
	        output = output.replace(original.charAt(i), ascii.charAt(i));
	    }
	    return output;
	}
	
	/**
	 *	Determina si la cadena de caracteres pasada es una
	 *	expresion numerica.
	 *	@param str expresion a ser evaluada
	 *	@return boolean indica si es numorico o no 
	 */
	public static boolean esNumerico(String str) {
	    if (str == null) {
	        return false;
	    }    
	    try {  
	        Integer.parseInt(str);
	    } catch (NumberFormatException nfe) {  
	        return false;  
	    }
	    return true;
	}
	
	/**
	 *	Devuelve un String aleatorio con el tamano 
	 *	que se especifique.
	 *	@param cantidadCaracteres : tamano de la cadena de caracteres aleatoria
	 *	@return String aleatorio alfanumerico
	 */
	public static String getStringAleatorio(int cantidadCaracteres) {
		return UUID.randomUUID().toString().replaceAll("-", "").substring(
			0, cantidadCaracteres);
	}
	
	/**
	 *	Devuelve un numero aleatorio con el tamano que se especifique.
	 *	@param cantidadNumeros longitud expresion numerica a devolver
	 *	@return String expresion alfanumerica aleatoria
	 */
	public static String getNumericoAleatorioAsString(int cantidadNumeros) {
		if (cantidadNumeros <= 0 || cantidadNumeros > ConstantesGlobales.DIEZ) {
			return String.valueOf(Math.random() * ConstantesGlobales.MIL_MILLONES).substring(
				0, ConstantesGlobales.DIEZ);
		} else {
			StringBuffer a = new StringBuffer();
			a.append("1");
			for (int x = 0; x < cantidadNumeros; x++) {
				a.append("0");
			}
			return String.valueOf(
				Math.random() * Integer.parseInt(
					a.toString())).substring(0, cantidadNumeros);
		} 
	}
}
