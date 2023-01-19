package es.vir2al.apuestas.app.services;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.vir2al.apuestas.app.domain.ApuestaVO;
import es.vir2al.apuestas.app.domain.request.ApuestaRequest;
import es.vir2al.apuestas.app.repositories.ApuestasDAO;
import es.vir2al.apuestas.fwk.domain.requests.NavigationInfoRequest;
import es.vir2al.apuestas.fwk.exceptions.BaseException;
import es.vir2al.apuestas.fwk.utils.constants.ResponseConstants;

@Service("apuestasService")
public class ApuestasServiceImpl implements ApuestasService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApuestasServiceImpl.class);

	@Autowired
	private ApuestasDAO apuestasDAO;

    @Override
    public ApuestaVO createApuesta(ApuestaVO data) throws BaseException {

        this.apuestasDAO.createApuesta(data); 

        return data;
    }

    @Override
    public ApuestaVO getApuestaById(Integer id) throws BaseException {

        if (id == null || id <= 0) {
            throw new BaseException(ResponseConstants.INPUT_DATA_ERROR, "El identificador {"+id+"} no es correcto");
        }
        
        return this.apuestasDAO.getApuestaById(id);
    }

    @Override
    public List<ApuestaVO> getApuestas(ApuestaRequest criteria, NavigationInfoRequest navigation, RowBounds rb)
            throws BaseException {

        return this.apuestasDAO.getApuestas(criteria, navigation, rb);
    
    }

    @Override
    public Integer getApuestasCount(ApuestaRequest criteria) throws BaseException {

        return this.apuestasDAO.getApuestasCount(criteria);
    
    }
    
}
