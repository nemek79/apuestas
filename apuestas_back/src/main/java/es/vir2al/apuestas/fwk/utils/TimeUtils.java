package es.vir2al.apuestas.fwk.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.vir2al.apuestas.fwk.exceptions.BaseException;

public class TimeUtils {

    public static String getNowString(String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		return sdf.format(timestamp);
	}
	
	public static Date getLastDateOfMonth(Integer month, Integer year) throws BaseException {
		
		Calendar calendario = Calendar.getInstance();
		calendario.set(year,month, 1);
		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);
		calendario.set(Calendar.DAY_OF_MONTH, calendario.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		return calendario.getTime();
	}
	
	public static Date getFirstDateOfMonth(Integer month, Integer year) throws BaseException {
		
		Calendar calendario = Calendar.getInstance();
		calendario.set(year,month, 1);
		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);
		return calendario.getTime();
	}
	
	public static Integer getCurretnYear() {

		return Calendar.getInstance().get(Calendar.YEAR);
		
	}

}
