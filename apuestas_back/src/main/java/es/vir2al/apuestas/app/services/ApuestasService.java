package es.vir2al.apuestas.app.services;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import es.vir2al.apuestas.app.domain.ApuestaVO;
import es.vir2al.apuestas.app.domain.request.ApuestaRequest;
import es.vir2al.apuestas.fwk.domain.requests.NavigationInfoRequest;
import es.vir2al.apuestas.fwk.exceptions.BaseException;

public interface ApuestasService {
    
    public ApuestaVO createApuesta(ApuestaVO data) throws BaseException;

    /**
     * Devuelve una apuesta a partir de su identificador
     * @param id
     * @return una apuesta. Devuelve nulo en caso de no existir.
     * @throws BaseException
     */
    public ApuestaVO getApuestaById(Integer id) throws BaseException;

    /**
     * Devuelve las apuestas que coincidand con los criterios de búsqueda
     * @param criteria
     * @param navigation
     * @param rb
     * @return Lista de apuestas. Si ninguna apuesta coincide con los criterios devuelve una lista vacía.
     * @throws BaseException
     */
    public List<ApuestaVO> getApuestas(ApuestaRequest criteria,  NavigationInfoRequest navigation, RowBounds rb) 
        throws BaseException;

    /**
     * Devuelve el número de apuestas total que coinciden con los criterios de búsqueda
     * @param criteria
     * @return Número entero
     * @throws BaseException
     */
    public Integer getApuestasCount(ApuestaRequest criteria) throws BaseException;

    /**
     * Actualiza la apuesta 
     * @param id
     * @param data
     * @throws BaseException
     */
    public void updateApuesta(Integer id, ApuestaVO data) throws BaseException;

    /**
     * Actualiza el estado de una apuesta
     */
    public void updateEstadoApuesta(Integer id, Integer estadoId) throws BaseException;

    /**
     * Actualiza el estado a push con la cantidad indicada
     */
    public void updateEstadoApuestaPush(Integer id, Float cantidad) throws BaseException;

}
