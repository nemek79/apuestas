package es.vir2al.apuestas.fwk.utils;

import org.springframework.lang.Nullable;

public abstract class StringUtils {
  
	/**
	 * Comprueba si el string que se pasa por parámetro es nulo o vacío
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(@Nullable String str) {
		
		return (str == null || str.isEmpty() || "".equals(str));
	
	}

}
