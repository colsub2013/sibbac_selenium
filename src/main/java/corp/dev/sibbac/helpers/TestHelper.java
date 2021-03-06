package corp.dev.sibbac.helpers;

import org.apache.commons.lang3.StringUtils;

import corp.dev.sibbac.constantes.ConstantesGlobales;

/**
 * Test helper class.
 */
public final class TestHelper {

	/**
	 * Constructor por defecto.
	 */
	private TestHelper() { }
	
	/**
	 * 	Obtiene un array con los resultados de la grilla.
	 * 	@param resultado 
	 *  @return int[]
	 * 	array[0]: Primer registro del paginador
	 * 	array[1]: ultimo registro del paginador
	 * 	array[2]: Total de registros devueltos
	 */
	public static int[] getResultadosGrilla(String resultado) {
		if (resultado == null || (resultado != null && resultado.isEmpty())) { 
			return null; 
		}
		int[] arrayResultado = new int[ConstantesGlobales.TRES];
		String[] a = resultado.substring(1, resultado.length() - 1).split(" ");
		int i = 0;
		for (String p : a) {
			if (StringUtils.isNumeric(p.trim())) {
				arrayResultado[i] = new Integer(p);
				i++;
			}
		}
		return arrayResultado;
	}
	
	/**
	 * 	Switch que devuelve si un testeo esta habilitado.
	 * 	@param clave 
	 * 	@return Boolean
	 */
	public Boolean isTestHabilitado(String clave) {
		if (clave == null || (clave != null && clave.isEmpty())) {
			return Boolean.FALSE;
		}
		return ConfigHelper.getString(clave).equals(Boolean.TRUE);
	}
}
