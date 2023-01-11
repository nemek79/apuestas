package es.vir2al.apuestas.fwk.utils;

import java.util.concurrent.TimeUnit;

public class FwkUtils {
   
    private static final long MILISECONDS_X_DEFECTO = 500;
	
	public static void sleepForAWhile(Long miliseconds) {
		
		long finalMiliseconds = MILISECONDS_X_DEFECTO;
		
		try {
			
			if (miliseconds != null && miliseconds > 100) {
				
				finalMiliseconds = miliseconds;
				
			}
			
			TimeUnit.MILLISECONDS.sleep(finalMiliseconds);
		
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
	}

}
