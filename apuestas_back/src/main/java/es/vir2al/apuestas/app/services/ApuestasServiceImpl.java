package es.vir2al.apuestas.app.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.vir2al.apuestas.app.domain.ApuestaVO;
import es.vir2al.apuestas.app.repositories.ApuestasDAO;
import es.vir2al.apuestas.fwk.exceptions.BaseException;

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
    
}
