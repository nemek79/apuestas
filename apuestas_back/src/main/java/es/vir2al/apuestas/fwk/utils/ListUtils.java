package es.vir2al.apuestas.fwk.utils;

import java.util.List;

import org.springframework.lang.Nullable;

public class ListUtils {

    /**
	 * Comprueba si la lista que se pasa por parámetro es nula o vacía
	 */
	public static boolean isEmpty(@Nullable List lst) {
		
		return (lst == null || lst.isEmpty() || lst.size() == 0);
	
	}

}
