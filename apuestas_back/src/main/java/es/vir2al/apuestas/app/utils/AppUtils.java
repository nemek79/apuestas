package es.vir2al.apuestas.app.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AppUtils {
   
    /**
    * Round to certain number of decimals
    * 
    * @param d
    * @param decimalPlace
    * @return
    */
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, RoundingMode.HALF_UP);
        return bd.floatValue();
    }

}
