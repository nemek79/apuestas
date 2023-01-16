package es.vir2al.apuestas.app.services;

import es.vir2al.apuestas.app.domain.ApuestaVO;
import es.vir2al.apuestas.fwk.exceptions.BaseException;

public interface ApuestasService {
    
    public ApuestaVO createApuesta(ApuestaVO data) throws BaseException;

}
